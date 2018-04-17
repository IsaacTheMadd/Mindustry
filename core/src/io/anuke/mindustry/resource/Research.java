package io.anuke.mindustry.resource;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;

import io.anuke.ucore.util.Bundles;
import io.anuke.ucore.util.Mathf;

public class Research {
    private static Array<Research> researches = new Array<>();
    private static byte lastid;
    
	public static final Research
		turretfirespeedup = new Research("turretfirespeedup", false, 16, stack(Item.steel, 80), stack(Item.coal, 50)),
		turrethealthup = new Research("turrethealthup", false, 16, stack(Item.steel, 20), stack(Item.titanium, 30))
	;
	
    public final byte id;
    public final String name;
    public final boolean unlocking;
    public static boolean unlocked = false;
    public static final int maxlevel = 12;
    public final String description;
    public final ItemStack[] cost;
    public int level = 0;
    
    public Research(String name, boolean unlocking, int maxlevel, ItemStack... cost){
        this.id = lastid ++;
        this.name = name;
        this.unlocking = unlocking;
        this.cost = cost;
        this.description = Bundles.getNotNull("research."+name+".description");

        researches.add(this);
    }

    public String localized(){
        return Bundles.get("research." + name + ".name");
    }

    public static Research getByID(byte id){
        return researches.get(id);
    }

    public static Array<Research> getAllResearch() {
        return researches;
    }


    private static ItemStack stack(Item item, int amount){
        return new ItemStack(item, amount);
    }
    
}
