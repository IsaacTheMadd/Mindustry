package io.anuke.mindustry.world.blocks.types.production;

import io.anuke.mindustry.Vars;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.resource.Research;
import io.anuke.mindustry.resource.Weapon;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.function.Listenable;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.Tooltip;
import io.anuke.ucore.scene.ui.layout.Table;

import static io.anuke.mindustry.Vars.*;

public class ResearchCenter extends Block{

    public ResearchCenter(String name){
        super(name);
        solid = true;
        destructible = true;
    }

    @Override
    public boolean isConfigurable(Tile tile){
        return true;
    }
    
    @Override
    public void buildTable(Tile tile, Table table) {
        int i = 0;

        Table content = new Table();

        for(Research research : Research.getAllResearch()){

            ItemStack[] requirements = research.cost;

            Table tiptable = new Table();

            Listenable run = ()->{
                tiptable.clearChildren();

                String description = research.description;

                tiptable.background("pane");
                tiptable.add("[orange]" + research.localized(), 0.5f).left().padBottom(2f);

                Table reqtable = new Table();

                tiptable.row();
                tiptable.add(reqtable).left();

                if(research.level <= research.maxlevel){
                    for(ItemStack s : requirements){
                    	int cost = s.amount + ((s.amount * research.level) / 4);
                        int amount = Math.min(state.inventory.getAmount(s.item), cost);
                        reqtable.addImage(s.item.region).padRight(3).size(8*2);
                        reqtable.add(
                                (amount >= cost ? "" : "[RED]")
                                        + amount + " / " +cost, 0.5f).left();
                        reqtable.row();
                    }
                }

                tiptable.row();
                tiptable.add().size(4);
                tiptable.row();
                tiptable.add("[gray]" + description).left();
                tiptable.row();
                if(research.level >= research.maxlevel){
                    tiptable.add("$text.purchased").padTop(4).left();
                }
                tiptable.margin(8f);
            };

            run.listen();

            Tooltip<Table> tip = new Tooltip<>(tiptable, run);

            tip.setInstant(true);

            ImageButton button = content.addImageButton("white", 8*4, () -> {

                if(!Net.client()){
                	ItemStack[] reqmultied = new ItemStack[requirements.length];
                	for(int s = 0; s < requirements.length; s++){
                		ItemStack stack = requirements[s];
                		ItemStack multiedstack = new ItemStack(stack.item, stack.amount + ((stack.amount * research.level) / 4));
                		reqmultied[s] = multiedstack;
                	}
                	state.inventory.removeItems(reqmultied);
            		if(research.unlocking && !research.unlocked){
            			research.unlocked = true;
            		}
            		research.level++;
            		run.listen();
            		Effects.sound("purchase");
                }
                
            }).size(49f, 54f).padBottom(-5).get();

            button.setDisabled(() -> research.level >= research.maxlevel || !state.inventory.hasItems(requirements));
            button.getStyle().imageUp = new TextureRegionDrawable(Draw.region(research.name));
            button.addListener(tip);

            if(++i % 3 == 0){
                content.row();
            }
        }

        table.add(content).padTop(140f);
    }
}
