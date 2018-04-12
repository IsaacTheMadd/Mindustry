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
		turretfirespeedup = new Research("turretfirespeedup", false, 16),
		turrethealthup = new Research("turrethealthup", false, 16)
	;
	
    public final byte id;
    public final String name;
    public final boolean unlocking;
    public static boolean unlocked = false;
    public static int level = 0;
    public static final int maxlevel = 12;
    public final String description;
    
    	private static final ObjectMap<Research, ItemStack[]> recipes = Mathf.map(
    		turretfirespeedup, list(stack(Item.steel, 80 + (80 * (level / 4))), stack(Item.coal, 50 + (50 * (level / 4)))),
    		turrethealthup, list(stack(Item.steel, 20 + (20 * (level / 4))), stack(Item.titanium, 30 + (30 * (level / 4))))
    	);

    private static final ItemStack[] empty = {};

    public Research(String name, boolean unlocking, int maxlevel){
        this.id = lastid ++;
        this.name = name;
        this.unlocking = unlocking;
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
    
    public static ItemStack[] get(Research research){
        return recipes.get(research, empty);
    }

    public static Entries<Research, ItemStack[]> getAllRecipes(){
        return recipes.entries();
    }

    private static ItemStack[] list(ItemStack... stacks){
        return stacks;
    }

    private static ItemStack stack(Item item, int amount){
        return new ItemStack(item, amount);
    }
    
}
