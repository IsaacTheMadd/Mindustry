package io.anuke.mindustry.entities.bullettypes;


import io.anuke.mindustry.entities.Bullet;
import io.anuke.mindustry.entities.BulletType;
import io.anuke.mindustry.entities.enemies.Enemy;
import io.anuke.ucore.entities.Entities;
import io.anuke.ucore.entities.SolidEntity;
import io.anuke.ucore.util.Angles;

import static io.anuke.mindustry.Vars.enemyGroup;

public abstract class MissileBullet extends BulletType{
	
	protected float Trackingspeed = 7f;
	protected float Trackingradius = 55f;
		
	public MissileBullet(float speed, int damage){
		super(speed, damage);
	}
	
	public void update(Bullet b){
		track(b);
	}

	public void track(Bullet b){
		SolidEntity entity = (Enemy)Entities.getClosest(enemyGroup,
				b.x, b.y, Trackingradius, e-> e instanceof Enemy && !((Enemy)e).isDead());
		if(entity != null){
			b.velocity.setAngle(Angles.predictAngle(b.x, b.y,
					entity.x, entity.y, 1f, 1f, Trackingspeed));

		}
	}
}
