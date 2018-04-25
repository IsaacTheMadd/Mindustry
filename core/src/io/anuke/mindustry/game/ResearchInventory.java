package io.anuke.mindustry.game;

import io.anuke.mindustry.resource.Research;

import java.util.Arrays;

import static io.anuke.mindustry.Vars.debug;

public class ResearchInventory {
    private final researchData[] researches = new researchData[Research.getAllResearch().size];
    private boolean updated;

    public boolean isUpdated(){
        return updated;
    }

    public void setUpdated(boolean updated){
        this.updated = updated;
    }

    public void clearResearches(){
        updated = true;
        fill(0, false);

        if(debug){
            fill(99999, true);
        }
    }

    public void fill(int level, boolean unlocked){
        updated = true;
        for(Research research : Research.getAllResearch()){
            if(researches[research.id] != null){
                if (getLevel(research) + level <= research.maxLevel) {
                    researches[research.id].setLevel(researches[research.id].getLevel() + level);
                }else{
                    researches[research.id].setLevel(research.maxLevel);
                }
                if (researches[research.id].getUnlocked() != unlocked) {
                    researches[research.id].setUnlocked(unlocked);
                }
            }else{
                researches[research.id] = new researchData(0,false);
            }
        }
    }

    public int getLevel(Research research){
        if(researches[research.id] != null){
            return researches[research.id].getLevel();
        }else{
            return 0;
        }
    }

    public boolean getUnlocked(Research research){
        if(researches[research.id] != null){
            return researches[research.id].getUnlocked();
        }else{
            return false;
        }
    }

    public void addResearch(Research research, int levels){
        updated = true;
        if(researches[research.id] != null){
            researches[research.id].level += levels;
        }else{
            researches[research.id] = new researchData(levels,false);
        }
    }

    public void addResearch(Research research, boolean unlocked){
        updated = true;
        if(researches[research.id] != null){
            researches[research.id].unlocked = unlocked;
        }else{
            researches[research.id] = new researchData(0,unlocked);
        }
    }

    public boolean hasResearch(Research[] research, int[] levels){
        if(research.length == levels.length){
            for (int i = 0; i <= research.length; i++)
                if (!hasResearch(research[i], levels[1]))
                    return false;
        }
        return true;
    }

    public boolean hasResearch(Research[] research, int[] levels, int scaling){
        if(research.length == levels.length) {
            for (int i = 0; i <= research.length; i++)
                if (!hasResearch(research[i], levels[i] * scaling))
                    return false;
        }
        return true;
    }

    public boolean hasResearch(Research research, int level){
        updated = true;
        return researches[research.id].level >= level;
    }

    public int[] getResearchLevels(){
        updated = true;
        int[] tmp = new int[researches.length];

        for(int i = 0; i <= researches.length; i++){
            tmp[i] = researches[i].level;
        }

        return tmp;
    }

    public boolean[] getResearchUnlocks(){
        updated = true;
        boolean[] tmp = new boolean[researches.length];

        for(int i = 0; i <= researches.length; i++){
            tmp[i] = researches[i].unlocked;
        }

        return tmp;
    }

    protected class researchData{
        protected int level;
        protected boolean unlocked;

        public researchData(int level, boolean unlocked){
            this.level = level;
            this.unlocked = unlocked;
        }

        public int getLevel(){
            return level;
        }

        public boolean getUnlocked(){
            return unlocked;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setUnlocked(boolean unlocked) {
            this.unlocked = unlocked;
        }
    }
}
