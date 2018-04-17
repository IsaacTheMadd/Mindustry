package io.anuke.mindustry.world.blocks;

import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.resource.Liquid;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.*;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.util.Mathf;

public class Blocks{
	public static final Block
	
	air = new Block("air"){
		//no drawing here
		public void drawCache(Tile tile){}
		
		//update floor blocks for effects, if needed
		public void draw(Tile tile){}
	},
	
	blockpart = new BlockPart(){

	},
	
	deepwater = new Floor("deepwater"){{
		variants = 0;
		solid = true;
		liquidDrop = Liquid.water;
		liquid = true;
		slowspeed = 0.35f;
	}},
	
	water = new Floor("water"){{
		variants = 0;
		liquidDrop = Liquid.water;
		liquid = true;
		slowspeed = 0.6f;
	}},
	
	lava = new Floor("lava"){
		{
			variants = 0;
			liquidDrop = Liquid.lava;
			liquid = true;
			slowspeed = 0.4f;
			damageapp = 12;
			movementEffect = Fx.lavamovementWaves;
		}
		
		@Override
		public void update(Tile tile){
			if(Mathf.chance(0.001 * Timers.delta())){
				Effects.effect(Fx.lava, tile.worldx() + Mathf.range(5f), tile.worldy() + Mathf.range(5f));
			}
			
			if(Mathf.chance(0.002 * Timers.delta())){
				Effects.effect(Fx.lavabubble, tile.worldx() + Mathf.range(3f), tile.worldy() + Mathf.range(3f));
			}
		}
	},
	
	oil = new Floor("oil"){
		{
			variants = 0;
			liquidDrop = Liquid.oil;
			liquid = true;
			slowspeed = 0.3f;
			movementEffect = Fx.oilmovementWaves;
		}
		
		@Override
		public void update(Tile tile){
			if(Mathf.chance(0.0022 * Timers.delta())){
				Effects.effect(Fx.oilbubble, tile.worldx() + Mathf.range(2f), tile.worldy() + Mathf.range(2f));
			}
		}
	},
	
	stone = new Floor("stone"){{
		drops = new ItemStack(Item.stone, 1);
		blends = block -> block != this && !(block instanceof Ore);
	}},
	
	blackstone = new Floor("blackstone"){{
		drops = new ItemStack(Item.blackstone, 1);
	}},
	
	iron = new Ore("iron"){{
		harvestlevel = 2;
		drops = new ItemStack(Item.iron, 1);
	}},
	
	coal = new Ore("coal"){{
		harvestlevel = 3;
		drops = new ItemStack(Item.coal, 1);
	}},
	
	titanium = new Ore("titanium"){{
		harvestlevel = 4;
		drops = new ItemStack(Item.titanium, 1);
	}},
	
	uranium = new Ore("uranium"){{
		harvestlevel = 5;
		drops = new ItemStack(Item.uranium, 1);
	}},
	
	depleteduranium= new Ore("depleteduranium"){{
		harvestlevel = 5;
		drops = new ItemStack(Item.depleteduranium, 1);
	}},
	
	dirt = new Floor("dirt"){{
		drops = new ItemStack(Item.dirt, 1);
	}},
	
	sand = new Floor("sand"){{
		drops = new ItemStack(Item.sand, 1);
	}},
	
	ice = new Floor("ice"){},
	
	snow = new Floor("snow"){},
	
	grass = new Floor("grass"){{
		drops = new ItemStack(Item.dirt, 1);
	}},
	
	sandblock = new StaticBlock("sandblock"){{
		solid = true;
		variants = 3;
	}},
	
	snowblock = new StaticBlock("snowblock"){{
		solid = true;
		variants = 3;
	}},
	
	stoneblock = new StaticBlock("stoneblock"){{
		solid = true;
		variants = 3;
	}},
	
	blackstoneblock = new StaticBlock("blackstoneblock"){{
		solid = true;
		variants = 3;
	}},
	
	grassblock = new StaticBlock("grassblock"){{
		solid = true;
		variants = 2;
	}},
					
	mossblock = new StaticBlock("mossblock"){{
		solid = true;
	}},
	
	shrub = new Rock("shrub"){

	},
	
	rock = new Rock("rock"){{
		variants = 2;
		varyShadow = true;
		drops = new ItemStack(Item.stone, 3);
	}},
	
	icerock = new Rock("icerock"){{
		variants = 2;
		varyShadow = true;
		drops = new ItemStack(Item.stone, 3);
	}},
	
	blackrock = new Rock("blackrock"){{
		variants = 1;
		varyShadow = true;
		drops = new ItemStack(Item.blackstone, 3);
	}},
	
	dirtblock = new StaticBlock("dirtblock"){{
		solid = true;
	}};
}
