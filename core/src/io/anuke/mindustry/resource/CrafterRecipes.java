package io.anuke.mindustry.resource;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import io.anuke.ucore.util.Mathf;

public class CrafterRecipes {
    private static final ObjectMap<Item, ItemStack[]> recipes = Mathf.map(
            Item.ammo, list(stack(Item.coal, 2), stack(Item.steel, 3)),
            Item.missile, list(stack(Item.coal, 2), stack(Item.steel, 4))
    );

    private static final ItemStack[] empty = {};

    public static ItemStack[] get(Item item){
        return recipes.get(item, empty);
    }

    public static Entries<Item, ItemStack[]> getAllRecipes(){
        return recipes.entries();
    }

    private static ItemStack[] list(ItemStack... stacks){
        return stacks;
    }

    private static ItemStack stack(Item item, int amount){
        return new ItemStack(item, amount);
    }
}
