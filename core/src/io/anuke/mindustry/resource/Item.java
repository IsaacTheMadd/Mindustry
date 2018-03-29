package io.anuke.mindustry.resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Bundles;
import io.anuke.mindustry.entities.ItemEntity;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.resource.itemtypes.*;

public class Item{
	private static Array<Item> items = new Array<>();
	private static int lastid;
	public ItemEntity entity;
	/**Max of timers used.*/
	public int timers = 0;

	public static final Item
		stone = new Item("stone"),
		iron = new Item("iron"),
		coal = new Item("coal"),
		steel = new Item("steel"),
		titanium = new Item("titanium"),
		dirium = new Item("dirium"),
		uranium = new Item("uranium"),
		fevorium = new Item("fevorium"),
		sand = new Item("sand"),
		/*dirt = new Item("dirt"),
		biomass = new Item("biomass"),
		saltpeter = new Item("saltpeter")
		sulfur = new Item("sulfur")
		blackpowder new Item("gunpowder")*/
		basicammo = new Item("basicammo"){
			{crafted = true;}
		},
		missile = new Item("missile"){
			{crafted = true;}
		},
		barrel = new LiquidItem("barrel"){
			{crafted = true;}
		};
		/*glass = new Item("glass"),
		silicon = new Item("silicon");*/

	public int id;
	public final String name;
	public TextureRegion region;
	public float explosiveness = 0f;
	public float flammability = 0f;

	public Boolean crafted = false;

	public Item(String name) {
		this.id = lastid ++;
		this.name = name;
		items.add(this);
	}
	
	public void init(){
		this.region = Draw.region("icon-" + name);
	}

	public String localizedName(){
		return Bundles.get("item." + this.name + ".name");
	}

	public String getDescription() {
		return Bundles.get("item." + this.name + ".description");
	}

	public <T extends ItemEntity> T entity(){
		return (T)entity;
	}
	
	@Override
	public String toString() {
		return localizedName();
	}

	public static Array<Item> getAllItems() {
		return Item.items;
	}

	public static Item getByID(int id){
		return items.get(id);
	}
}
