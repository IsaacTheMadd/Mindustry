package io.anuke.mindustry.world.blocks.types.movement;

import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Layer;

public class Bridge extends Block{
	
	protected Bridge(String name) {
		super(name);
		rotate = true;
        breakable = true;
        breaktime = 10;
        destructible = false;
		layer = Layer.overlay;
        alwaysReplace = true;
	}

}