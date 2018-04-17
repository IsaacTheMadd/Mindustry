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
		blackstone = new Item("blackstone"),
		iron = new Item("iron"),
		coal = new Item("coal"),
		steel = new Item("steel"),
		titanium = new Item("titanium"),
		warpsteel = new Item("warpsteel"),
		uranium = new Item("uranium"),
		depleteduranium = new Item("depleteduranium"),
/*		copper = new Item("copper"),
		gold = new Item("gold"),
		lead = new Item("lead"),
		aluminum = new Item("aluminum"),
		silver = new Item("silver"),
		tungsten = new Item("tungsten"),
		wolfrite = new Item("wolfrite"),
		staballoy = new Item("staballoy"),
		brass = new Item("brass"),
		zinc = new Item("zinc"),
		nickel = new Item("nickel"),
		plastic = new Item("plastic"),*/
		sand = new Item("sand"),
		dirt = new Item("dirt"),
/*
		glass = new Item("glass"){
			{crafted = true;}
		},
*/
		silicon = new Item("silicon"){
			{crafted = true;}
		},

		biomass = new Item("biomass"){
			{crafted = true;}
		},
		
		saltpeter = new Item("saltpeter"){
			{crafted = true;}
		},

		sulfur = new Item("sulfur"){
			{crafted = true;}
		},

		blackpowder = new Item("gunpowder"){
			{crafted = true;}
		},

		basicammo = new Item("basicammo"){
			{crafted = true;}
		},

		tier2ammo = new Item("tier2ammo"){
			{crafted = true;}
		},

		railammo = new Item("railammo"){
			{crafted = true;}
		},

		flakammo = new Item("flakammo"){
			{crafted = true;}
		},
		
		flamerammo = new Item("flamerammo"){
			{crafted = true;}
		},

		chainammo = new Item("chainammo"){
			{crafted = true;}
		},

		titanammo = new Item("titanammo"){
			{crafted = true;}
		},

		warpsteelammo = new Item("warpsteelammo"){
			{crafted = true;}
		},

		missile = new Item("missile"){
			{crafted = true;}
		},

		barrel = new Item("barrel"){
			{crafted = true;}
		},

		waterbarrel = new Item("barrel-water"){{
			holdsliquid = true;
			itemliquid = Liquid.water;
		}},
		lavabarrel = new Item("barrel-lava"){{
			holdsliquid = true;
			itemliquid = Liquid.lava;
		}},
		oilbarrel = new Item("barrel-oil"){{
			holdsliquid = true;
			itemliquid = Liquid.oil;
			explosiveness = 1f;
			flammability = 1f;
		}},
		cryobarrel = new Item("barrel-cryofluid"){{
			holdsliquid = true;
			itemliquid = Liquid.cryofluid;
		}},
		plasmabarrel = new Item("barrel-plasma"){{
			holdsliquid = true;
			itemliquid = Liquid.plasma;
		}};

	public int id;
	public final String name;
	public TextureRegion region;
	public float explosiveness = 0f;
	public float flammability = 0f;

	public Boolean crafted = false;
	public Boolean holdsliquid = false;
	public Liquid itemliquid;

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
