package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.resource.itemtypes.LiquidItem;
import io.anuke.mindustry.world.BlockBar;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Strings;

public class LiquidItemLoader extends LiquidBlock{
	protected final int timerDump = timers++;
	protected final int timerFill = timers++;
	
	/**Can be null.*/
	public Item input = Item.barrel;
	public int inputAmount = 1;
	public Item output = input;
	public int itemCapacity = 10;
	public int fillTime = 80;
	public Effect craftEffect = Fx.purify;

	public LiquidItemLoader(String name) {
		super(name);
		update = true;
		rotate = false;
		solid = true;
		health = 60;
		liquidCapacity = 21f;

		bars.add(new BlockBar(Color.GREEN, true, tile -> input == null ? -1f : (float)tile.entity.getItem(input) / itemCapacity));
	}
	
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[liquidinfo]Max items/second: " + Strings.toFixed(60f/fillTime, 1));
		if(input != null) list.add("[iteminfo]Item Capacity: " + itemCapacity);
		if(input != null) list.add("[iteminfo]Input item: " + input + " x " + inputAmount);
		list.add("[iteminfo]Output: " + output);
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
		
		if(entity.timer.get(timerFill, fillTime) && entity.liquidAmount >= ((LiquidItem)input).getLiquidCapacity() &&
				(entity.hasItem(input, inputAmount))){
			if(input instanceof LiquidItem && entity.liquid != null)
				((LiquidItem)input).setLiquid(((LiquidItem)input), entity.liquid);
				((LiquidItem)input).setLiquidAmount(((LiquidItem)input), ((LiquidItem)input).getLiquidCapacity());
				entity.liquidAmount -= ((LiquidItem)input).getLiquidCapacity();
			Effects.effect(craftEffect, tile.worldx(), tile.worldy());
			if(entity.timer.get(timerDump, 15)){
				tryDump(tile, -1, output);
			}
		}
	}
	
	@Override
	public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
		return super.acceptLiquid(tile, source, liquid, amount);
	}
	
	@Override
	public boolean acceptItem(Item item, Tile tile, Tile source){
		TileEntity entity = tile.entity();
		return item == input && item instanceof LiquidItem && entity.getItem(input) < itemCapacity;
	}

}
