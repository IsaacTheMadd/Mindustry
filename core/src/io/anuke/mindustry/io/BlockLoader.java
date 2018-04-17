package io.anuke.mindustry.io;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.blocks.*;
import io.anuke.ucore.util.Log;

public class BlockLoader {
    static final ObjectIntMap<String> defaultMap = map(
            "air", 0,
            "blockpart", 1,
            "deepwater", 2,
            "water", 3,
            "lava", 4,
            "oil", 5,
            "stone", 6,
            "blackstone", 7,
            "iron", 8,
            "coal", 9,
            "titanium", 10,
            "uranium", 11,
            "dirt", 12,
            "sand", 13,
            "ice", 14,
            "snow", 15,
            "grass", 16,
            "sandblock", 17,
            "snowblock", 18,
            "stoneblock", 19,
            "blackstoneblock", 20,
            "grassblock", 21,
            "mossblock", 22,
            "shrub", 23,
            "rock", 24,
            "icerock", 25,
            "blackrock", 26,
            "dirtblock", 27,
            "stonewall", 28,
            "ironwall", 29,
            "steelwall", 30,
            "titaniumwall", 31,
            "duriumwall", 32,
            "compositewall", 33,
            "steelwall-large", 34,
            "titaniumwall-large", 35,
            "duriumwall-large", 36,
            "titaniumshieldwall", 37,
            "repairturret", 38,
            "megarepairturret", 39,
            "shieldgenerator", 40,
            "door", 41,
            "door-large", 42,
            "conduit", 43,
            "pulseconduit", 44,
            "liquidrouter", 45,
            "conveyor", 46,
            "steelconveyor", 47,
            "poweredconveyor", 48,
            "router", 49,
            "junction", 50,
            "conveyortunnel", 51,
            "liquidjunction", 52,
            "liquiditemjunction", 53,
            "powerbooster", 54,
            "powerlaser", 55,
            "powerlaserrouter", 56,
            "powerlasercorner", 57,
            "teleporter", 58,
            "sorter", 59,
            "core", 60,
            "pump", 61,
            "fluxpump", 62,
            "smelter", 63,
            "crucible", 64,
            "coalpurifier", 65,
            "titaniumpurifier", 66,
            "oilrefinery", 67,
            "stoneformer", 68,
            "lavasmelter", 69,
            "drill", 70,
            "omnidrill", 71,
            "coalgenerator", 72,
            "thermalgenerator", 73,
            "combustiongenerator", 74,
            "rtgenerator", 75,
            "nuclearreactor", 76,
            "turret", 77,
            "doubleturret", 78,
            "machineturret", 79,
            "shotgunturret", 80,
            "flameturret", 81,
            "sniperturret", 82,
            "mortarturret", 83,
            "laserturret", 84,
            "waveturret", 85,
            "plasmaturret", 86,
            "chainturret", 87,
            "titancannon", 88,
            "fevoriumdrill", 89,
            "railcannon", 90,
            "playerspawn", 91,
            "enemyspawn", 92
    );
    static final IntMap<Block> blockmap = new IntMap<>();

    public static void load(){

        Block[] blockClasses = {
            Blocks.air,
            DefenseBlocks.compositewall,
            DistributionBlocks.conduit,
            ProductionBlocks.drill,
            WeaponBlocks.chainturret,
            SpecialBlocks.enemySpawn
            //add any new block sections here
        };

        for(String string : defaultMap.keys()){
            Block block = Block.getByName(string);
            blockmap.put(defaultMap.get(string, -1), block);
        }

        for(Block block : Block.getAllBlocks()){
            block.init();
        }

        Log.info("Total blocks loaded: {0}", Block.getAllBlocks().size);
    }

    public static Block getByOldID(int id){
        return blockmap.get(id);
    }

    private static ObjectIntMap<String> map(Object... objects){
        ObjectIntMap<String> map = new ObjectIntMap<>();
        for(int i = 0; i < objects.length/2; i ++){
            map.put((String)objects[i*2], (int)objects[i*2+1]);
        }
        return map;
    }
}
