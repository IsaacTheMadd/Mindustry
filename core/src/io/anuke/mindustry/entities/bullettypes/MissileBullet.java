package io.anuke.mindustry.entities.bullettypes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import io.anuke.mindustry.entities.Bullet;
import io.anuke.mindustry.entities.BulletType;
import io.anuke.mindustry.entities.effect.DamageArea;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.ucore.graphics.Hue;
import io.anuke.ucore.graphics.Draw;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.core.Timers;
import io.anuke.ucore.entities.BaseBulletType;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.graphics.Lines;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.enemyGroup;
import static io.anuke.mindustry.graphics.Fx.*;

public abstract class MissileBullet extends BulletType{
	
	protected float Trackingspeed = 7f;
	protected float Trackingradius = 55f;
		
	public MissileBullet(float speed, int damage, String name){
		super(speed, damage, name);
	}
	
	@Override
	public void draw(Bullet b){
		Draw.rect(b.name, b.x, b.y, b.angle());
		Draw.reset();
	}
	
	public void update(Bullet b){
		SolidEntity entity = (Enemy)Entities.getClosest(enemyGroup,
				b.x, b.y, Trackingradius, e-> e instanceof Enemy && !((Enemy)e).isDead());
		if(entity != null){
			
			b.velocity.setAngle(Angles.predictAngle(b.x, b.y, 
					entity.x, entity.y, 1f, 1f, Trackingspeed));
			
		}
	}
}
