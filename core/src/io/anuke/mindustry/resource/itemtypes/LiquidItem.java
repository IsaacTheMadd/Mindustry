package io.anuke.mindustry.resource.itemtypes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.LiquidBlock.LiquidEntity;
import io.anuke.ucore.entities.Entity;
import io.anuke.mindustry.resource.Liquid;

public class LiquidItem extends Item{
	protected float liquidCapacity = 10f;
	public Entity entity;
	
	public LiquidItem(String name) {
		super(name);
	}

	public float getLiquidCapacity() {
		return liquidCapacity;
	}

	public Liquid getLiquid(LiquidItem item) {
		LiquidItemEntity entity = item.entity();
		return entity.liquid;
	}

	public <T extends Entity> T entity(){
		return (T)entity;
	}

	public void setLiquid(LiquidItem item, Liquid liquid) {
		LiquidItemEntity entity = item.entity();
		entity.liquid = liquid;
	}

	public float getLiquidAmount(LiquidItem item) {
		LiquidItemEntity entity = item.entity();
		return entity.liquidAmount;
	}

	public void setLiquidAmount(LiquidItem item, float liquidAmount ) {
		LiquidItemEntity entity = item.entity();
		entity.liquidAmount = liquidAmount;
	}

	public Entity getEntity(){
		return new LiquidItemEntity();
	}

	public static class LiquidItemEntity extends Entity{
		public Liquid liquid;
		public float liquidAmount = 0;

		public void write(DataOutputStream stream) throws IOException{
			stream.writeByte(liquid == null ? -1 : liquid.id);
			stream.writeByte((byte)(liquidAmount));
		}
		
		public void read(DataInputStream stream) throws IOException{
			byte id = stream.readByte();
			liquid = id == -1 ? null : Liquid.getByID(id);
			liquidAmount = stream.readByte();
		}
	}
}
