package io.anuke.mindustry.world.blocks.types.distribution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import io.anuke.mindustry.entities.TileEntity;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.ItemStack;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.scene.style.TextureRegionDrawable;
import io.anuke.ucore.scene.ui.ButtonGroup;
import io.anuke.ucore.scene.ui.ImageButton;
import io.anuke.ucore.scene.ui.layout.Table;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Tmp;

import static io.anuke.mindustry.Vars.state;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CoreUnloader extends Block{
	protected final int timerDump = timers++;

	protected int capacity = 1;
	
	
	public CoreUnloader(String name) {
		super(name);
		update = true;
		solid = true;
	}

	@Override
	public void update(Tile tile){
		UnloaderEntity entity = tile.entity();
		if(entity.timer.get(timerDump, 5) && entity.hasItem(entity.unloadItem)){
			tryDump(tile, -1, entity.unloadItem);
		}
		if(entity.getItem(entity.unloadItem) >= capacity){
			return;
		}
		if(state.inventory.hasItem(entity.unloadItem, 1)){
			offloadNear(tile, entity.unloadItem);
			if(Net.server() || !Net.active()) state.inventory.removeItems(new ItemStack(entity.unloadItem, 1));
		}
	}

	@Override
	public void draw(Tile tile){
		super.draw(tile);
		
		UnloaderEntity entity = tile.entity();
		
		TextureRegion region = entity.unloadItem.region;
		Tmp.tr1.setRegion(region, 4, 4, 1, 1);
		
		Draw.rect(Tmp.tr1, tile.worldx(), tile.worldy(), 2f, 2f);
	}

	
	@Override
	public void configure(Tile tile, byte data) {
		UnloaderEntity entity = tile.entity();
		if(entity != null){
			entity.unloadItem = Item.getByID(data);
		}
	}

	@Override
	public boolean isConfigurable(Tile tile){
		return true;
	}
	
	@Override
	public void buildTable(Tile tile, Table table){
		UnloaderEntity entity = tile.entity();

		Array<Item> items = Item.getAllItems();

		ButtonGroup<ImageButton> group = new ButtonGroup<>();
		Table cont = new Table();
		cont.margin(4);
		cont.marginBottom(5);

		cont.add().colspan(4).height(105f);
		cont.row();

		for(int i = 0; i < items.size; i ++){
			final int f = i;
			ImageButton button = cont.addImageButton("white", "toggle", 24, () -> {
				entity.unloadItem = items.get(f);
				setConfigure(tile, (byte)f);
			}).size(38, 42).padBottom(-5.1f).group(group).get();
			button.getStyle().imageUp = new TextureRegionDrawable(new TextureRegion(items.get(i).region));
			button.setChecked(entity.unloadItem.id == f);

			if(i%4 == 3){
				cont.row();
			}
		}

		table.add(cont);
	}
	
	@Override
	public TileEntity getEntity(){
		return new UnloaderEntity();
	}

	public static class UnloaderEntity extends TileEntity{
		public Item unloadItem = Item.basicammo;
		
		@Override
		public void write(DataOutputStream stream) throws IOException{
			stream.writeByte(unloadItem.id);
		}
		
		@Override
		public void read(DataInputStream stream) throws IOException{
			unloadItem = Item.getAllItems().get(stream.readByte());
		}
	}
}
