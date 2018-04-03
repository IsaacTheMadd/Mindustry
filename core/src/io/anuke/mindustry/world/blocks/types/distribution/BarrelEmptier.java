package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;
import io.anuke.mindustry.world.blocks.types.LiquidBlock.LiquidEntity;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Strings;

public class BarrelEmptier extends LiquidBlock{
	protected final int timerDump = timers++;
	protected final int timerEmpty = timers++;
	
	private Item[] inputs = {};
	private Item output = Item.barrel;
	public float liquidAmount = 5f;
	public int itemCapacity = 90;
	public float emptyTime = 15f;
	public Effect emptyEffect = Fx.smelt;
	private Item unloadingitem = Item.waterbarrel;

	public BarrelEmptier(String name) {
		super(name);
		update = true;
		rotate = false;
		solid = true;
		health = 60;
		liquidCapacity = 20f;

		int it = 0;		
		for(Item i : Item.getAllItems()){
			if(i.holdsliquid == true){
				it++;
			}
		}
		inputs = new Item[it];
		int id = 0;		
		for(Item i : Item.getAllItems()){
			if(i.holdsliquid == true){
				inputs[id] = i;
				id++;
			}
		}
		for(Item i : inputs){
			bars.add(new BlockBar(Color.GREEN, true, tile -> i == null ? -1f : (float)tile.entity.getItem(i) / itemCapacity));
		}
	}
	
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[liquidinfo]Max items/second: " + Strings.toFixed(60f/emptyTime, 1));
	}
	
	@Override
	public void draw(Tile tile){
		LiquidEntity entity = tile.entity();
		Draw.rect(name(), tile.drawx(), tile.drawy());
		
		if(entity.liquid == null) return;
		
		Draw.color(entity.liquid.color);
		Draw.alpha(entity.liquidAmount / liquidCapacity);
		Draw.rect("blank", tile.drawx(), tile.drawy(), 2, 2);
		Draw.color();
	}
	
	@Override
	public void update(Tile tile){
		LiquidEntity entity = tile.entity();
		
		if(entity.timer.get(timerDump, 15)){
			tryDump(tile, -1, output);
		}

		if(entity.timer.get(timerDump, 1)){
			tryDumpLiquid(tile);
		}
		
		for(Item i : inputs){
			if(unloadingitem.itemliquid != entity.liquid || entity.liquid == null || entity.liquidAmount <= 0.01f){
				if(unloadingitem != i && !entity.hasItem(unloadingitem)){
					if(entity.hasItem(i)){
						unloadingitem = i;
					}
				}
			}
		}
		
		if(entity.getItem(output) >= itemCapacity //output full
				|| !entity.hasItem(unloadingitem) //no inputs
				|| entity.liquidAmount + liquidAmount >= liquidCapacity //liquid output full
				|| !entity.timer.get(timerEmpty, emptyTime)){ //not yet time
			return;
		}
		
		entity.removeItem(unloadingitem, 1);
		entity.liquid = unloadingitem.itemliquid;
		entity.liquidAmount += liquidAmount;
		offloadNear(tile, output);
		Effects.effect(emptyEffect, tile.worldx(), tile.worldy());
		
	}
	
	@Override
	public boolean acceptItem(Item item, Tile tile, Tile source){
		LiquidEntity entity = tile.entity();
		boolean isInput = false;
		
		for(Item in : inputs){
			if(in == item){
				isInput = true;
				break;
			}
		}
		
		return isInput && entity.getItem(item) < itemCapacity;
	}

	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		return false;
	}
	
	@Override
	public TileEntity getEntity(){
		return new LiquidEntity();
	}
}
