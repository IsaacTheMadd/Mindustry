package io.anuke.mindustry.entities.effect;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.entities.effect.DamageArea;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.entities.DestructibleEntity;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.Entity;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.function.Consumer;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.util.Mathf;
import io.anuke.ucore.util.Timer;

import static io.anuke.mindustry.Vars.enemyGroup;
import static io.anuke.mindustry.Vars.inverseshieldGroup;

public class InverseShield extends Entity{
	public boolean active;
	public float radius = 40f;
	int damage = 4;

	static final int timerDamage = 0;
	static final int timerShrink = 1;
	private float uptime = 0f;
	public Timer timer = new Timer(2);
	
	public InverseShield(float x, float y, float radius, int damage){
		set(x, y);
		this.radius = radius;
		this.damage = damage;
	}
	
	public float drawSize(){
		return 150;
	}

	@Override
	public void update(){
		Effects.effect(Fx.coreexplosion, x, y);
		float alpha = 0.1f;
		Interpolation interp = Interpolation.fade;
		
		if(active){
			uptime = interp.apply(uptime, 1f, alpha * Timers.delta());
		}else{
			uptime = interp.apply(uptime, 0f, alpha * Timers.delta());
		if(uptime <= 0.05f)
				remove();
		}
		uptime = Mathf.clamp(uptime);
		
		if(radius < 0.01f) remove();
		
		if(timer.get(timerShrink, 40)) radius = Mathf.lerp(radius, 0f, Timers.delta() * 0.005f);
		
		if(timer.get(timerDamage, 6)){
			Entities.getNearby(enemyGroup, x, y, radius * 2*uptime + 10, entity->{
				DestructibleEntity enemy = (DestructibleEntity)entity;
				if(enemy instanceof Enemy){
					float dst =  entity.distanceTo(this);
					
					if(dst < drawRadius()/2f){
						enemy.damage(damage);
						((Enemy)enemy).extraspeedmulti = 0.3f;
					}else{
						((Enemy)enemy).extraspeedmulti = 1f;
					}
				}
			});
		}
	}
	
	@Override
	public void draw(){
		if(!(radius <= 1f)){
			return;
		}
		
		float rad = drawRadius();
		Draw.rect("circle2", x, y, rad, rad);
	}
	
	float drawRadius(){
		return (radius*2 + Mathf.sin(Timers.time(), 25f, 2f));
	}
	
	public void removeDelay(){
		active = false;
	}
	
	@Override
	public InverseShield add(){
		return super.add(inverseshieldGroup);
	}
	
	@Override
	public void added(){
		active = true;
	}
	
	@Override
	public void removed(){
		active = false;
		uptime = 0f;
	}
	
}
