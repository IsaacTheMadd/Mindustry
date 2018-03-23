package io.anuke.mindustry.ai;

import com.badlogic.gdx.ai.pfa.Heuristic;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.movement.Bridge;
import io.anuke.ucore.function.Predicate;

import static io.anuke.mindustry.Vars.tilesize;

public class Heuristics {
    /**How many times more it costs to go through a destructible block than an empty block.*/
    static final float solidMultiplier = 5.5f;
    /**How many times more it costs to go through a tile that touches a solid block.*/
    static final float occludedMultiplier = 5f;
    /**How many times more it costs to go through a tile that applies damage.*/
    static final float damagingMultiplier = 4.5f;
    /**How many times more it costs to go through a tile that applies damage.*/
    static final float slowingMultiplier = 3f;

    /**Calculates the fastest path. No priorities, just avoids solid blocks.*/
    public static class FastestHeuristic implements Heuristic<Tile> {

        @Override
        public float estimate(Tile node, Tile other){
            //Get Manhattan distance cost
            float cost = Math.abs(node.worldx() - other.worldx()) + Math.abs(node.worldy() - other.worldy());

            //If either one of the tiles is a breakable solid block (that is, it's player-made),
            //increase the cost by the tilesize times the solid block multiplier
            //Also add the block health, so blocks with more health cost more to traverse
            if(node.breakable() && node.block().solid) cost += tilesize* solidMultiplier + node.block().health;
            if(other.breakable() && other.block().solid) cost += tilesize* solidMultiplier + other.block().health;
            
            //If either one of the tiles is a floor tile with either a slowing or a damaging effect without a bridge over it,
            //Increase the cost by the multiplier times the damage per tick and/or by the multiplier times the slowspeed
            if(node.floor().slowspeed < 1 && !(node.block() instanceof Bridge)) cost += 1 / node.floor().slowspeed * slowingMultiplier;
            if(other.floor().slowspeed < 1 && !(other.block() instanceof Bridge)) cost += 1 / other.floor().slowspeed * slowingMultiplier;
            if(node.floor().damageapp > 0 && !(node.block() instanceof Bridge)) cost += node.floor().damageapp / node.floor().damagetime * damagingMultiplier;
            if(other.floor().damageapp > 0 && !(other.block() instanceof Bridge)) cost += other.floor().damageapp / other.floor().damagetime * damagingMultiplier;

            //if this block has solid blocks near it, increase the cost, as we don't want enemies hugging walls
            if(node.occluded) cost += tilesize*occludedMultiplier;

            return cost;
        }
    }

    /**Calculates the fastest and most destructive path based on a block predicate.*/
    public static class DestrutiveHeuristic implements Heuristic<Tile> {
        /**Should return whether a block if "free", e.g. whether it's an important target*/
        private final Predicate<Block> frees;

        public DestrutiveHeuristic(Predicate<Block> frees){
            this.frees = frees;
        }

        @Override
        public float estimate(Tile node, Tile other){
            //Get Manhattan distance cost
            float cost = Math.abs(node.worldx() - other.worldx()) + Math.abs(node.worldy() - other.worldy());

            //If either one of the tiles is a breakable solid block (that is, it's player-made),
            //increase the cost by the tilesize times the solid block multiplier
            //Also add the block health, so blocks with more health cost more to traverse
            if(node.breakable() && node.block().solid) cost += tilesize* solidMultiplier + node.block().health;
            if(other.breakable() && other.block().solid) cost += tilesize* solidMultiplier + other.block().health;

            //if this block has solid blocks near it, increase the cost, as we don't want enemies hugging walls
            if(node.occluded) cost += tilesize*occludedMultiplier;

            if(other.getLinked() != null) other = other.getLinked();
            if(node.getLinked() != null) node = node.getLinked();

            //If either one of the tiles is a floor tile with either a slowing or a damaging effect without a bridge over it,
            //Increase the cost by the multiplier times the damage per tick and/or by the multiplier times the slowspeed
            if(node.floor().slowspeed < 1 && !(node.block() instanceof Bridge)) cost += 1 / node.floor().slowspeed * slowingMultiplier;
            if(other.floor().slowspeed < 1 && !(other.block() instanceof Bridge)) cost += 1 / other.floor().slowspeed * slowingMultiplier;
            if(node.floor().damageapp > 0 && !(node.block() instanceof Bridge)) cost += node.floor().damageapp / node.floor().damagetime * damagingMultiplier;
            if(other.floor().damageapp > 0 && !(other.block() instanceof Bridge)) cost += other.floor().damageapp / other.floor().damagetime * damagingMultiplier;

            //check if it's free
            if(frees.test(other.block()) || frees.test(node.block())) cost = 0;

            return cost;
        }
    }
}
