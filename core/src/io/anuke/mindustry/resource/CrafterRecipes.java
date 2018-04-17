package io.anuke.mindustry.resource;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entries;
import io.anuke.ucore.util.Mathf;

public class CrafterRecipes {
    private static final ObjectMap<Item, ItemStack[]> recipes = Mathf.map(
            Item.silicon, list(stack(Item.coal, 1), stack(Item.sand, 2)),
            Item.biomass, list(stack(Item.dirt, 2)),
            Item.saltpeter, list(stack(Item.sand, 1), stack(Item.dirt, 1)),
            Item.sulfur, list(stack(Item.coal, 1), stack(Item.dirt, 3)),
            Item.blackpowder, list(stack(Item.coal, 1), stack(Item.sulfur, 1), stack(Item.saltpeter, 3)),
            Item.basicammo, list(stack(Item.coal, 1), stack(Item.iron, 2)),
            Item.tier2ammo, list(stack(Item.blackpowder, 2), stack(Item.iron, 4)),
            Item.railammo, list(stack(Item.steel, 4)),
            Item.flamerammo, list(stack(Item.coal, 4), stack(Item.steel, 4)),
            Item.flakammo, list(stack(Item.blackpowder, 2), stack(Item.steel, 2), stack(Item.coal, 4)),
            Item.chainammo, list(stack(Item.blackpowder, 2), stack(Item.uranium, 2)),
            Item.titanammo, list(stack(Item.blackpowder, 4), stack(Item.uranium, 6)),
            Item.warpsteelammo, list(stack(Item.blackpowder, 2), stack(Item.warpsteel, 6)),
            Item.missile, list(stack(Item.blackpowder, 2), stack(Item.steel, 4)),
            Item.barrel, list( stack(Item.steel, 2))
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
