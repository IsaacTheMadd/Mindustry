package io.anuke.mindustry.world.blocks.types.production;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Layer;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Effects.Effect;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

public class Drill extends Block{
	protected final int timerDrill = timers++;
	protected final int timerDump = timers++;
	
	protected float time = 5;
	protected float scaling = 20;
	protected int capacity = 5;
	protected int maxlevel = 3;
	protected Effect drillEffect = Fx.spark;

	public Drill(String name) {
		super(name);
		update = true;
		solid = true;
		layer = Layer.overlay;
	}

    @Override
    public void draw(Tile tile){
        super.draw(tile);

        if(tile.floor().drops == null) return;
        Item item = tile.floor().drops.item;

        TextureRegion region = item.region;
        Tmp.tr1.setRegion(region, 4, 4, 1, 1);

        Draw.rect(Tmp.tr1, tile.worldx(), tile.worldy(), 2f, 2f);
    }
    
	@Override
	public void getStats(Array<String> list){
		super.getStats(list);
		list.add("[iteminfo]Capacity: " + capacity);
		list.add("[iteminfo]Seconds/item: " + time);
	}
	
	@Override
	public void update(Tile tile){
		TileEntity entity = tile.entity;
		float levelmultipler = (Math.max(tile.floor().harvestlevel, 1f) * scaling);
		
		if(!tile.block().isMultiblock()){
			if(tile.floor().drops != null && entity.timer.get(timerDrill, 60 * time + levelmultipler) && tile.floor().harvestlevel <= maxlevel && tile.entity.getItem(tile.floor().drops.item) < capacity){
				offloadNear(tile, tile.floor().drops.item);
				Effects.effect(drillEffect, tile.worldx(), tile.worldy());
			}
		}else{
			if(entity.timer.get(timerDrill, 60 * time + levelmultipler)){
				for(Tile linked : tile.getLinkedTiles()){
					if(linked.floor().drops != null && tile.entity.getItem(linked.floor().drops.item) < capacity && tile.floor().harvestlevel <= maxlevel){
						offloadNear(tile, linked.floor().drops.item);
						Effects.effect(drillEffect, tile.drawx(), tile.drawy());
					}
				}
			}
		}
		
		if(entity.timer.get(timerDump, 30)){
			tryDump(tile);
		}
	}

	@Override
	public boolean isLayer(Tile tile){
		return tile.floor().drops == null || (tile.floor().drops != null && tile.floor().harvestlevel > maxlevel);
	}
	
	@Override
	public void drawLayer(Tile tile){
		Draw.colorl(0.85f + Mathf.absin(Timers.time(), 6f, 0.15f));
		Draw.rect("cross", tile.worldx(), tile.worldy());
		Draw.color();
	}

}
