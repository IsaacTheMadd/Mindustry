package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.resource.Weapon;
import io.anuke.mindustry.resource.CrafterRecipes;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.PowerAcceptor;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.function.Listenable;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.Tooltip;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static io.anuke.mindustry.Vars.*;

public class PowerCrafter extends Block implements PowerAcceptor{
	protected final int timerDump = timers++;
	protected final int timerCraft = timers++;
	
	protected float craftTime = 20f; //time to craft one item, so max 3 items per second by default
	protected Effect craftEffect = Fx.smelt;

	protected int capacity = 180;
	public float powerCapacity = 20f;
	public float powerUsed = 0.2f;

    public PowerCrafter(String name){
        super(name);
        solid = true;
        destructible = true;
        
		bars.add( new BlockBar(Color.YELLOW, true, tile -> tile.<PowerCrafterEntity>entity().power / powerCapacity));
    }

	@Override
	public void draw(Tile tile){
		super.draw(tile);
		
		PowerCrafterEntity entity = tile.entity();

		TextureRegion region = entity.craftItem.region;
		Tmp.tr1.setRegion(region, 2, 2, 2, 2);
		
		Draw.rect(Tmp.tr1, tile.worldx(), tile.worldy(), 2f, 2f);
	}

/*	@Override
	public void init(){
		PowerCrafterEntity entity = tile.entity();
		for(ItemStack item : entity.inputitems){
			bars.add(new BlockBar(Color.GREEN, true, tile -> (float)tile.entity.getItem(item.item)/capacity));
		}
	}
*/	
	@Override
	public void update(Tile tile){
		PowerCrafterEntity entity = tile.entity();
		Item result = entity.craftItem;
		ItemStack[] inputitems = CrafterRecipes.get(entity.craftItem);
		
		if(entity.timer.get(timerDump, 5) && entity.hasItem(result)){
			tryDump(tile, -1, result);
		}

		//make sure it has all the items
		for(ItemStack item : inputitems){
			if(!entity.hasItem(item.item, item.amount)){
				return;
			}
		}

		if(entity.getItem(result) >= capacity //output full
				|| entity.power <= powerUsed //not enough power
				|| !entity.timer.get(timerCraft, craftTime)){ //not yet time
			return;
		}

		if(entity.power >= powerUsed){
			entity.power -= powerUsed;
		}

		for(ItemStack item : inputitems){
			entity.removeItem(item.item, item.amount);
		}
		
		offloadNear(tile, result);
		Effects.effect(craftEffect, entity);
	}

	@Override
	public boolean acceptItem(Item item, Tile tile, Tile source){
		PowerCrafterEntity entity = tile.entity();
		ItemStack[] inputitems = CrafterRecipes.get(entity.craftItem);
		boolean isInput = false;

		for(ItemStack req : inputitems){
			if(req.item == item){
				isInput = true;
				break;
			}
		}

		return (isInput && tile.entity.getItem(item) < capacity);
	}

	@Override
	public void configure(Tile tile, byte data) {
		PowerCrafterEntity entity = tile.entity();
		if(entity != null){
			entity.craftItem = Item.getByID(data);
		}
	}

	@Override
	public boolean isConfigurable(Tile tile){
		return true;
	}

    @Override
    public void buildTable(Tile tile, Table table) {
        int i = 0;
        
		PowerCrafterEntity entity = tile.entity();

        Table content = new Table();
        
		ButtonGroup<ImageButton> group = new ButtonGroup<>();

        for(Item item : Item.getAllItems()){
            if(!(item.crafted == true)) continue;
            Item crafteditem = item;
        	        	
            ItemStack[] requirements = CrafterRecipes.get(crafteditem);

            Table tiptable = new Table();

            Listenable run = ()->{
                tiptable.clearChildren();

                String description = crafteditem.getDescription();
                
                tiptable.background("pane");
                tiptable.add("[orange]" + crafteditem.localizedName(), 0.5f).left().padBottom(2f);

                Table reqtable = new Table();

                tiptable.row();
                tiptable.add(reqtable).left();

                for(ItemStack s : requirements){

                     reqtable.addImage(s.item.region).padRight(3).size(8*2);
                     reqtable.add((capacity <= s.amount ? "" : "") +s.amount, 0.5f).left();
                     reqtable.row();
                }

                tiptable.row();
                tiptable.add().size(4);
                tiptable.row();
                tiptable.add("[gray]" + description).left();
                tiptable.row();
                tiptable.margin(8f);
            };

            run.listen();

            Tooltip<Table> tip = new Tooltip<>(tiptable, run);

            tip.setInstant(true);

            ImageButton button = content.addImageButton("white", "toggle", 24, () -> {
                entity.craftItem = crafteditem;
                setConfigure(tile, (byte)crafteditem.id);
                for(Item removeitem : Item.getAllItems()){
                	int removeamount = entity.getItem(removeitem);                	
                	entity.removeItem(removeitem,removeamount);
                }
            }).size(49f, 54f).padBottom(-5.1f).group(group).get();

            button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(crafteditem.region));
            button.addListener(tip);
            boolean checked = false;
            if(entity.craftItem.id == item.id){
            	checked = true;
            }
            button.setChecked(checked);
    		

    		if(++i % 4 == 3){
    				content.row();
    		}

        }

        table.add(content).padTop(140f);
    }

	@Override
	public TileEntity getEntity(){
		return new PowerCrafterEntity();
	}
	
	public static class PowerCrafterEntity extends TileEntity{
		public float power;
		
		public Item craftItem = Item.basicammo;		
		
		@Override
		public void write(DataOutputStream stream) throws IOException{
			stream.writeByte(craftItem.id);
			stream.writeFloat(power);
		}
		
		@Override
		public void read(DataInputStream stream) throws IOException{
			craftItem = Item.getAllItems().get(stream.readByte());
			power = stream.readFloat();
		}
	}

	@Override
	public boolean acceptsPower(Tile tile){
		PowerCrafterEntity entity = tile.entity();
		
		return entity.power + 0.001f <= powerCapacity;
	}
	
	@Override
	public float addPower(Tile tile, float amount){
		PowerCrafterEntity entity = tile.entity();
		
		float canAccept = Math.min(powerCapacity - entity.power, amount);
		
		entity.power += canAccept;
		
		return canAccept;
	}
	
	@Override
	public void setPower(Tile tile, float power){
		PowerCrafterEntity entity = tile.entity();
		entity.power = power;
	}
}
