package io.anuke.mindustry.entities;

import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.net.Net;
import io.anuke.mindustry.net.NetEvents;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.world.blocks.types.Wall;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Timer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static io.anuke.mindustry.Vars.itemGroup;
import static io.anuke.mindustry.Vars.world;

public class ItemEntity extends Entity{
	private Item item;
	public Timer timer;
	public float health;
	public boolean added;
	
	/**Sets this tile entity data to this tile, and adds it if necessary.*/
	public ItemEntity init(Item item, boolean added){
		this.item = item;
		this.added = added;
		
		timer = new Timer(item.timers);
		
		if(added){
			add();
		}
		
		return this;
	}
	
	public void write(DataOutputStream stream) throws IOException{
		
	}
	
	public void read(DataInputStream stream) throws IOException{
		
	}

	public void readNetwork(DataInputStream stream, float elapsed) throws IOException{
		read(stream);
	}
		
	public void collision(Bullet other){
	}
	
	
	public boolean collide(Bullet other){
		return true;
	}
	
	@Override
	public void update(){
	}
	
	
	@Override
	public ItemEntity add(){
		return add(itemGroup);
	}
}
