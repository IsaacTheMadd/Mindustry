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

public class Barrelfiller extends LiquidBlock{
	protected final int timerDump = timers++;
	protected final int timerFill = timers++;
	
	private Item input = Item.barrel;
	private Item output = Item.waterbarrel;
	public float liquidAmount = 5f;
	public int itemCapacity = 90;
	public float fillTime = 15f;
	public Effect fillEffect = Fx.smelt;

	public Barrelfiller(String name) {
		super(name);
		update = true;
		rotate = false;
		solid = true;
		health = 60;
		liquidCapacity = 20f;

		bars.add(new BlockBar(Color.GREEN, true, tile -> input == null ? -1f : (float)tile.entity.getItem(input) / itemCapacity));
	}
	
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[liquidinfo]Max items/second: " + Strings.toFixed(60f/fillTime, 1));
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
		
		if(entity.liquid != null && output.itemliquid != entity.liquid){
			for(Item i : Item.getAllItems()){
				if(i.holdsliquid == true){
					if(i.itemliquid == entity.liquid){
						output = i;
					}
				}
			}
		}
		
		if(entity.getItem(output) >= itemCapacity //output full
				|| !entity.hasItem(input) //no inputs
				|| entity.liquidAmount <= liquidAmount //not enough liquid
				|| !entity.timer.get(timerFill, fillTime)){ //not yet time
			return;
		}
		
		entity.removeItem(input, 1);
		entity.liquidAmount -= liquidAmount;
		offloadNear(tile, output);
		Effects.effect(fillEffect, tile.worldx(), tile.worldy());
		
	}
	
	@Override
	public boolean acceptItem(Item item, Tile tile, Tile source){
		LiquidEntity entity = tile.entity();
		return item == input && entity.getItem(input) < itemCapacity;
	}

	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		LiquidEntity entity = tile.entity();
		
		return entity.liquidAmount + amount < liquidCapacity && (entity.liquid == liquid || entity.liquidAmount <= 0.01f);
	}
	
	@Override
	public TileEntity getEntity(){
		return new LiquidEntity();
	}
}
