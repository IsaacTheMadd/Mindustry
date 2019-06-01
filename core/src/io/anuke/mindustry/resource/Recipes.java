package io.anuke.mindustry.resource;

import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.Vars;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.DefenseBlocks;
import io.anuke.mindustry.world.blocks.DistributionBlocks;
import io.anuke.mindustry.world.blocks.MovementBlocks;
import io.anuke.mindustry.world.blocks.ProductionBlocks;
import io.anuke.mindustry.world.blocks.WeaponBlocks;

import static io.anuke.mindustry.Vars.state;
import static io.anuke.mindustry.resource.Section.*;

public class Recipes {
	private static final Array<Recipe> list = Array.with(
			new Recipe(defense, DefenseBlocks.ironwall, null, stack(Item.iron, 12)),
			new Recipe(defense, DefenseBlocks.steelwall, null, stack(Item.steel, 12)),
			new Recipe(defense, DefenseBlocks.titaniumwall, null, stack(Item.titanium, 12)),
			new Recipe(defense, DefenseBlocks.warpsteelwall, null, stack(Item.warpsteel, 12)),
			new Recipe(defense, DefenseBlocks.compositewall, null, stack(Item.titanium, 6), stack(Item.warpsteel, 6), stack(Item.depleteduranium, 6)),
			new Recipe(defense, DefenseBlocks.steelwalllarge, null, stack(Item.steel, 12*4)),
			new Recipe(defense, DefenseBlocks.titaniumwalllarge, null, stack(Item.titanium, 12*4)),
			new Recipe(defense, DefenseBlocks.warpsteelwalllarge, null, stack(Item.warpsteel, 12*4)),
			new Recipe(defense, DefenseBlocks.compositewalllarge, null, stack(Item.titanium, 6*4), stack(Item.warpsteel, 6*4), stack(Item.depleteduranium, 6*4)),
			new Recipe(defense, DefenseBlocks.door, null, stack(Item.steel, 3), stack(Item.iron, 3*4)).setDesktop(),
			new Recipe(defense, DefenseBlocks.largedoor, null, stack(Item.steel, 3*4), stack(Item.iron, 3*4*4)).setDesktop(),
			new Recipe(defense, DefenseBlocks.titaniumshieldwall, null, stack(Item.titanium, 16)),
			new Recipe(defense, DefenseBlocks.titaniumshieldwalllarge, null, stack(Item.titanium, 16*4)),
			new Recipe(defense, DefenseBlocks.warpsteelshieldwall, null, stack(Item.warpsteel, 12), stack(Item.titanium, 4)),
			new Recipe(defense, DefenseBlocks.warpsteelshieldwalllarge, null, stack(Item.warpsteel, 12*4), stack(Item.titanium, 4*4)),

			new Recipe(distribution, DistributionBlocks.conveyor, null, stack(Item.iron, 1)),
			new Recipe(distribution, DistributionBlocks.steelconveyor, null, stack(Item.steel, 1)),
			new Recipe(distribution, DistributionBlocks.pulseconveyor, null, stack(Item.warpsteel, 1)),
			new Recipe(distribution, DistributionBlocks.router, null, stack(Item.iron, 2)),
			new Recipe(distribution, DistributionBlocks.junction, null, stack(Item.iron, 2)),
			new Recipe(distribution, DistributionBlocks.tunnel, null, stack(Item.iron, 2)),
			new Recipe(distribution, DistributionBlocks.pulsetunnel, null, stack(Item.warpsteel, 2)),
			new Recipe(distribution, DistributionBlocks.conduit, null, stack(Item.steel, 1)),
			new Recipe(distribution, DistributionBlocks.pulseconduit, null, stack(Item.titanium, 1), stack(Item.steel, 1)),
			new Recipe(distribution, DistributionBlocks.liquidrouter, null, stack(Item.steel, 2)),
			new Recipe(distribution, DistributionBlocks.liquidjunction, null, stack(Item.steel, 2)),
			new Recipe(distribution, DistributionBlocks.sorter, null, stack(Item.steel, 2)),

			new Recipe(weapon, WeaponBlocks.turret, null, stack(Item.iron, 4)),
			new Recipe(weapon, WeaponBlocks.doubleturret, null, stack(Item.iron, 7)),
			new Recipe(weapon, WeaponBlocks.machineturret, null, stack(Item.iron, 14)),
			new Recipe(weapon, WeaponBlocks.shotgunturret, null, stack(Item.iron, 16)),
			new Recipe(weapon, WeaponBlocks.flameturret, null, stack(Item.iron, 12), stack(Item.steel, 9)),
			new Recipe(weapon, WeaponBlocks.sniperturret, null, stack(Item.iron, 15), stack(Item.steel, 10)),
			new Recipe(weapon, WeaponBlocks.laserturret, null, stack(Item.steel, 12), stack(Item.titanium, 12)),
			new Recipe(weapon, WeaponBlocks.mortarturret, null, stack(Item.steel, 25), stack(Item.titanium, 15)),
			new Recipe(weapon, WeaponBlocks.teslaturret, null, stack(Item.steel, 20), stack(Item.titanium, 25), stack(Item.warpsteel, 15)),
			new Recipe(weapon, WeaponBlocks.plasmaturret, null, stack(Item.steel, 10), stack(Item.titanium, 20), stack(Item.warpsteel, 15)),
			new Recipe(weapon, WeaponBlocks.chainturret, null, stack(Item.steel, 50), stack(Item.titanium, 25), stack(Item.warpsteel, 40)),
			new Recipe(weapon, WeaponBlocks.titanturret, null, stack(Item.steel, 70), stack(Item.titanium, 50), stack(Item.warpsteel, 55)),
			new Recipe(weapon, WeaponBlocks.railturret, null, stack(Item.titanium, 20), stack(Item.depleteduranium, 50), stack(Item.warpsteel, 25)),
			new Recipe(weapon, WeaponBlocks.plasmacannon, null, stack(Item.titanium, 58), stack(Item.depleteduranium, 70), stack(Item.warpsteel, 65)),
			new Recipe(weapon, WeaponBlocks.missileturret, null, stack(Item.titanium, 20), stack(Item.depleteduranium, 30), stack(Item.warpsteel, 15), stack(Item.steel, 30)),
			new Recipe(weapon, WeaponBlocks.shieldcannon, null, stack(Item.titanium, 40), stack(Item.depleteduranium, 45), stack(Item.warpsteel, 60), stack(Item.steel, 80)),

			new Recipe(crafting, ProductionBlocks.smelter, null, stack(Item.iron, 50)),
			new Recipe(crafting, ProductionBlocks.crucible, null, stack(Item.titanium, 50), stack(Item.steel, 50)),
			new Recipe(crafting, ProductionBlocks.coalpurifier, null, stack(Item.steel, 10), stack(Item.iron, 10)),
			new Recipe(crafting, ProductionBlocks.titaniumpurifier, null, stack(Item.steel, 30), stack(Item.iron, 30)),
			new Recipe(crafting, ProductionBlocks.oilrefinery, null, stack(Item.steel, 15), stack(Item.iron, 15)),
			new Recipe(crafting, ProductionBlocks.stoneformer, null, stack(Item.steel, 10), stack(Item.iron, 10)),
			new Recipe(crafting, ProductionBlocks.lavasmelter, null, stack(Item.steel, 30), stack(Item.titanium, 15)),
			new Recipe(crafting, ProductionBlocks.weaponFactory, null, stack(Item.steel, 60), stack(Item.iron, 60)).setDesktop(),
			new Recipe(crafting, ProductionBlocks.assembler, null, stack(Item.steel, 5), stack(Item.iron, 20)),
			new Recipe(crafting, DistributionBlocks.barrelfiller, null, stack(Item.steel, 15)),
			new Recipe(crafting, DistributionBlocks.barrelunloader, null, stack(Item.steel, 15)),
			new Recipe(crafting, ProductionBlocks.researchCenter, null, stack(Item.steel, 25), stack(Item.titanium, 10)),

			new Recipe(production, ProductionBlocks.drill, null, stack(Item.iron, 32)),
			new Recipe(production, ProductionBlocks.omnidrill, null, stack(Item.titanium, 40), stack(Item.warpsteel, 40), stack(Item.depleteduranium, 10)),
			new Recipe(production, ProductionBlocks.spawner, null, stack(Item.iron, 2)),

			new Recipe(power, ProductionBlocks.coalgenerator, null, stack(Item.iron, 40)),
			new Recipe(power, ProductionBlocks.thermalgenerator, null, stack(Item.steel, 30), stack(Item.iron, 30)),
			new Recipe(power, ProductionBlocks.combustiongenerator, null, stack(Item.iron, 40)),
			new Recipe(power, ProductionBlocks.rtgenerator, null, stack(Item.titanium, 20), stack(Item.steel, 20)),
			new Recipe(power, ProductionBlocks.nuclearReactor, null, stack(Item.titanium, 40), stack(Item.warpsteel, 40), stack(Item.steel, 50)),
			new Recipe(power, DistributionBlocks.powerbooster, null, stack(Item.steel, 8), stack(Item.iron, 8)),
			new Recipe(power, DistributionBlocks.powerlaser, null, stack(Item.steel, 3), stack(Item.iron, 3)),
			new Recipe(power, DistributionBlocks.powerlasercorner, null, stack(Item.steel, 4), stack(Item.iron, 4)),
			new Recipe(power, DistributionBlocks.powerlaserrouter, null, stack(Item.steel, 5), stack(Item.iron, 5)),

			new Recipe(power, DefenseBlocks.shieldgenerator, null, stack(Item.titanium, 30), stack(Item.warpsteel, 30)),
			new Recipe(power, DefenseBlocks.shieldgeneratorlarge, null, stack(Item.titanium, 30*4), stack(Item.warpsteel, 30*4)),

			new Recipe(distribution, DistributionBlocks.teleporter, null, stack(Item.steel, 30), stack(Item.warpsteel, 40)),
			new Recipe(distribution, MovementBlocks.bridge, null, stack(Item.steel, 1), stack(Item.warpsteel, 2)).setDesktop(),
			new Recipe(distribution, DistributionBlocks.coreunloader, null, stack(Item.iron, 1)),


			new Recipe(power, DefenseBlocks.repairturret, null, stack(Item.iron, 30)),
			new Recipe(power, DefenseBlocks.megarepairturret, null, stack(Item.iron, 20), stack(Item.steel, 30)),

			new Recipe(production, ProductionBlocks.pump, null, stack(Item.steel, 10)),
			new Recipe(production, ProductionBlocks.fluxpump, null, stack(Item.steel, 10), stack(Item.warpsteel, 5))
	);
	
	private static ItemStack stack(Item item, int amount){
		return new ItemStack(item, amount);
	}

	public static Array<Recipe> all(){
		return list;
	}

	public static Recipe getByResult(Block block){
		for(Recipe recipe : list){
			if(recipe.result == block){
				return recipe;
			}
		}
		return null;
	}
	
	public static Array<Recipe> getBy(Section section, Array<Recipe> r){
		for(Recipe recipe : list){
			if(recipe.section == section && !(Vars.mobile && recipe.desktopOnly) && (recipe.research == null || ((recipe.research != null) && !state.researchInventory.getUnlocked(recipe.research))))
				r.add(recipe);
		}
		
		return r;
	}
}
