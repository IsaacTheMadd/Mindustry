package io.anuke.mindustry.resource;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.resource.Item;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Bundles;

public class CraftedItem extends Item{
	public static final CraftedItem

		ammo = new CraftedItem("ammo"),
		missile = new CraftedItem("missile");
	
	private String description;
	
	private CraftedItem(String name) {
		super(name);
        this.description = Bundles.getNotNull("item."+name+".description");
	}

	public String getDescription() {
		return description;
	}

}
