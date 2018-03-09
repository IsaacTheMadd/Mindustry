package io.anuke.mindustry.world.blocks;

import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.types.Wall;
import io.anuke.mindustry.world.blocks.types.defense.*;

public class DefenseBlocks{
	static final int wallHealthMultiplier = 4;
	
	public static final Block

	stonewall = new Wall("stonewall"){{
		health = 40*wallHealthMultiplier;
	}},

	ironwall = new Wall("ironwall"){{
		health = 80*wallHealthMultiplier;
	}},

	steelwall = new Wall("steelwall"){{
		health = 110*wallHealthMultiplier;
	}},

	titaniumwall = new Wall("titaniumwall"){{
		health = 150*wallHealthMultiplier;
	}},
	diriumwall = new Wall("duriumwall"){{
		health = 190*wallHealthMultiplier;
	}},
	compositewall = new Wall("compositewall"){{
		health = 280*wallHealthMultiplier;
	}},
	steelwalllarge = new Wall("steelwall-large"){{
		health = 110*4*wallHealthMultiplier;
		width = height = 2;
	}},
	titaniumwalllarge = new Wall("titaniumwall-large"){{
		health = 150*4*wallHealthMultiplier;
		width = height = 2;
	}},
	diriumwalllarge = new Wall("duriumwall-large"){{
		health = 190*4*wallHealthMultiplier;
		width = height = 2;
	}},
	compositewalllarge = new Wall("compositewall-large"){{
		health = 280*4*wallHealthMultiplier;
		width = height = 2;
	}},
	titaniumshieldwall = new ShieldedWallBlock("titaniumshieldwall"){{
		health = 150*wallHealthMultiplier;
	}},
	titaniumshieldwalllarge = new ShieldedWallBlock("titaniumshieldwall-large"){{
		health = 150*4*wallHealthMultiplier;
		width = height = 2;
		powerCapacity = powerCapacity*4;
	}},
	diriumshieldwall = new ShieldedWallBlock("duriumshieldwall"){{
		health = 190*wallHealthMultiplier;
		powerPerDamage = 0.04f;
		powerCapacity = powerCapacity+3;
	}},
	diriumshieldwalllarge = new ShieldedWallBlock("duriumshieldwall-large"){{
		health = 190*4*wallHealthMultiplier;
		width = height = 2;
		powerCapacity = powerCapacity+3*4;
		powerPerDamage = 0.04f;
	}},



	repairturret = new RepairTurret("repairturret"){
		{
			range = 30;
			reload = 20f;
			health = 60;
			powerUsed = 0.08f;
		}
	},

	megarepairturret = new RepairTurret("megarepairturret"){
		{
			range = 44;
      		reload = 12f;
			health = 90;
			powerUsed = 0.13f;
		}
	},

	shieldgenerator = new ShieldBlock("shieldgenerator"){
		{
			health = 100*wallHealthMultiplier;
		}
	},

	shieldgeneratorlarge = new ShieldBlock("shieldgenerator-large"){
		{
			health = 100*4*wallHealthMultiplier;
			width = height = 2;
			shieldRadius = 40f*4;
			maxRadius = 40f*4;
			powerCapacity = 80f*4;
		}
	},
	door = new Door("door"){{
		health = 90*wallHealthMultiplier;
	}},
	largedoor = new Door("door-large"){{
		openfx = Fx.dooropenlarge;
		closefx = Fx.doorcloselarge;
		health = 90*4*wallHealthMultiplier;
		width = height = 2;
	}};
}
