package io.anuke.mindustry.resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Bundles;

public class Item{
	private static Array<Item> items = new Array<>();
	private static int lastid;

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
		ammo = new Item("ammo"){
			{crafted = true;}
		},
		missile = new Item("missile"){
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
