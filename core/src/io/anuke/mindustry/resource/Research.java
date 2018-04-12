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
	test1 = new Research("test1"),
	test2 = new Research("test2")
	;
	
    public final byte id;
    public final String name;
    public final String description;
    
    private static final ObjectMap<Research, ItemStack[]> recipes = Mathf.map(
    );

    private static final ItemStack[] empty = {};

    public Research(String name){
        this.id = lastid ++;
        this.name = name;
        this.description = Bundles.getNotNull("upgrade."+name+".description");

        researches.add(this);
    }

    public String localized(){
        return Bundles.get("upgrade." + name + ".name");
    }

    public static Research getByID(byte id){
        return researches.get(id);
    }

    public static Array<Research> getAllUpgrades() {
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
