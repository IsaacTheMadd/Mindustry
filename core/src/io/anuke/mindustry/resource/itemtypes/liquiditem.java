package io.anuke.mindustry.resource.itemtypes;

import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.resource.Liquid;

public class liquiditem extends Item{
	protected float liquidCapacity = 10f;
	private Liquid liquid;
	private float liquidAmount;
	
	public liquiditem(String name) {
		super(name);
	}

	public float getLiquidCapacity(Item item) {
		return liquidCapacity;
	}

	public Liquid getLiquid() {
		return liquid;
	}

	public void setLiquid(Liquid liquid) {
		this.liquid = liquid;
	}

	public float getLiquidAmount() {
		return liquidAmount;
	}

	public void setLiquidAmount(float liquidAmount) {
		this.liquidAmount = liquidAmount;
	}
}
