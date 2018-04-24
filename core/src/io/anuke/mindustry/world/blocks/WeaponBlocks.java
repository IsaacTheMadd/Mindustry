package io.anuke.mindustry.world.blocks;

import com.badlogic.gdx.graphics.Color;
import io.anuke.mindustry.entities.BulletType;
import io.anuke.mindustry.entities.effect.TeslaOrb;
import io.anuke.mindustry.graphics.Fx;
import io.anuke.mindustry.resource.Item;
import io.anuke.mindustry.resource.Research;
import io.anuke.mindustry.world.Block;
import io.anuke.mindustry.world.Tile;
import io.anuke.mindustry.world.blocks.types.defense.LaserTurret;
import io.anuke.mindustry.world.blocks.types.defense.PowerTurret;
import io.anuke.mindustry.world.blocks.types.defense.Turret;
import io.anuke.ucore.core.Effects;
import io.anuke.ucore.util.Angles;
import io.anuke.ucore.util.Mathf;

import static io.anuke.mindustry.Vars.state;

public class WeaponBlocks{
	private static int healthmutli = state.researchInventory.getLevel(Research.turrethealthup) / 4;
	private static float reloadmulti = state.researchInventory.getLevel(Research.turretfirespeedup) / (Research.turretfirespeedup.maxLevel + 1.5f);
	
	
	public static Block
	
	turret = new Turret("turret"){
		{
			range = 52;
			reload = 15f - (15f * reloadmulti);
			bullet = BulletType.stone;
			health = 45 + (45 * healthmutli);
			ammo = Item.basicammo;
		}
	},
	
	doubleturret = new Turret("doubleturret"){
		{
			range = 44;
			reload = 13f - (13f * reloadmulti);
			bullet = BulletType.stone;
			ammo = Item.basicammo;
			ammoMultiplier = 14;
			health = 55 + (55 * healthmutli);
		}
		
		@Override
		protected void shoot(Tile tile){
			TurretEntity entity = tile.entity();

			for(int i : Mathf.signs){
				tr.trns(entity.rotation, 4, -2 * i);
				bullet(tile, entity.rotation);
			}
		}
	},
	
	machineturret = new Turret("machineturret"){
		{
			range = 65;
			reload = 7f - (7f * reloadmulti);
			bullet = BulletType.iron;
			ammo = Item.tier2ammo;
			health = 65 + (65 * healthmutli);
		}
	},
	
	shotgunturret = new Turret("shotgunturret"){
		{
			range = 50;
			reload = 30f - (30f * reloadmulti);
			bullet = BulletType.iron;
			ammo = Item.tier2ammo;
			health = 70 + (70 * healthmutli);
			shots = 5;
			inaccuracy = 15f;
			shotDelayScale = 0.7f;
		}
	},
	
	flameturret = new Turret("flameturret"){
		{
			range = 45f;
			reload = 5f - (5f * reloadmulti);
			bullet = BulletType.flame;
			ammo = Item.flamerammo;
			health = 90 + (90 * healthmutli);
			inaccuracy = 4f;
		}
	},
	
	sniperturret = new Turret("sniperturret"){
		{
			shootsound = "railgun";
			range = 120;
			reload = 50f - (50f * reloadmulti);
			bullet = BulletType.sniper;
			ammo = Item.railammo;
			health = 70 + (70 * healthmutli);
			shootEffect = Fx.railshot;
		}
	},
	
	mortarturret = new Turret("mortarturret"){
		{
			shootsound = "bigshot";
			rotatespeed = 0.2f;
			range = 120;
			reload = 55f - (55f * reloadmulti);
			bullet = BulletType.flak;
			shots = 3;
			inaccuracy = 9f;
			ammo = Item.flakammo;
			ammoMultiplier = 5;
			health = 110 + (110 * healthmutli);
			shootEffect = Fx.mortarshot;
			shootShake = 2f;
		}
	},
	
	laserturret = new LaserTurret("laserturret"){
		{
			shootsound = "laser";
			beamColor = Color.SKY;
			range = 60;
			reload = 4f - (4f * reloadmulti);
			damage = 10;
			health = 110 + (110 * healthmutli);
			powerUsed = 0.2f;
		}
	},
	
	teslaturret = new PowerTurret("waveturret"){
		{
			shootsound = "tesla";
			range = 70;
			reload = 15f - (15f * reloadmulti);
			bullet = BulletType.shell;
			health = 140 + (140 * healthmutli);
		}
		
		@Override
		public void shoot(Tile tile){
			TurretEntity entity = tile.entity();

			float len = 4f;

			new TeslaOrb(tile.drawx() + Angles.trnsx(entity.rotation, len), tile.drawy() + Angles.trnsy(entity.rotation, len), range, 9).add();
		}
	},
		
	plasmaturret = new Turret("plasmaturret"){
		{
			shootsound = "flame2";
			inaccuracy = 7f;
			range = 60f;
			reload = 3f - (3f * reloadmulti);
			bullet = BulletType.plasmaflame;
			ammo = Item.flamerammo;
			health = 180 + (180 * healthmutli);
			ammoMultiplier = 40;
		}
	},
	
	chainturret = new Turret("chainturret"){
		{
			shootsound = "bigshot";
			inaccuracy = 8f;
			range = 80f;
			reload = 20f - (20f * reloadmulti);
			bullet = BulletType.chain;
			ammo = Item.chainammo;
			health = 430 + (430 * healthmutli);
			width = height = 2;
			shootCone = 9f;
			ammoMultiplier = 8;
			shots = 2;
			bursts = 20;
			burstDelay = 5f - (5f * reloadmulti);
			shootEffect = Fx.chainshot;
		}

		@Override
		protected void shoot(Tile tile){
			TurretEntity entity = tile.entity();
			
			float len = 8;
			float space = 3.5f;
			
			for(int i = -1; i < 1; i ++){
				tr.trns(entity.rotation, len, Mathf.sign(i) * space);
				bullet(tile, entity.rotation);
				Effects.effect(shootEffect, tile.drawx() + tr.x,
						tile.drawy() + tr.y, entity.rotation);
			}
			
			Effects.shake(1f, 1f, tile.worldx(), tile.worldy());
		}
	},
	
	titanturret = new Turret("titancannon"){
		{
			shootsound = "blast";
			range = 120f;
			reload = 23f - (23f * reloadmulti);
			bullet = BulletType.titanshell;
			ammo = Item.titanammo;
			health = 800 + (800 * healthmutli);
			ammoMultiplier = 4;
			width = height = 3;
			rotatespeed = 0.07f;
			shootCone = 9f;
			shootEffect = Fx.titanshot;
			shootShake = 3f;
		}
	},
	
	railturret = new Turret("railturret"){
		{
			shootsound = "bigshot";
			range = 340f;
			reload = 95f - (95f * reloadmulti);
			bullet = BulletType.railsniper;
			ammo = Item.warpsteelammo;
			health = 540 + (540 * healthmutli);
			ammoMultiplier = 4;
			width = height = 2;
			shootCone = 9f;
			shootEffect = Fx.railshot;
			shootShake = 2f;
		}
	},
	
	plasmacannon = new PowerTurret("plasmacannon"){
		{
			shootsound = "tesla";
			range = 225;
			reload = 24f - (24f * reloadmulti);
			bullet = BulletType.pulseshot;
			health = 920 + (920 * healthmutli);
			width = height = 3;
			powerUsed = 0.8f;
		}
	},
	
	missileturret = new Turret("missileturret"){
		{
			shootsound = "missile";
			inaccuracy = 8f;
			range = 220f;
			reload = 42f - (42f * reloadmulti);
			bullet = BulletType.missile;
			ammo = Item.missile;
			health = 630 + (630 * healthmutli);
			width = height = 2;
			shootCone = 9f;
			ammoMultiplier = 1;
			shots = 2;
			shootEffect = Fx.chainshot;
			bursts = 2;
		}

		@Override
		protected void shoot(Tile tile){
			TurretEntity entity = tile.entity();
			
			float len = 8;
			float space = 3.7f;
			
			for(int i = -1; i < 1; i ++){
				tr.trns(entity.rotation, len, Mathf.sign(i) * space);
				bullet(tile, entity.rotation);
				Effects.effect(shootEffect, tile.drawx() + tr.x,
						tile.drawy() + tr.y, entity.rotation);
			}
			
			Effects.shake(0.2f, 0.2f, tile.worldx(), tile.worldy());
		}
	},
	
	shieldcannon = new PowerTurret("shieldcannon"){
		{
			shootsound = "tesla";
			range = 235;
			reload = 63f - (63f * reloadmulti);
			bursts = 3;
			burstDelay = 12f;
			bullet = BulletType.shieldball;
			health = 1220 + (1220 * healthmutli);
			width = height = 4;
			shootEffect = Fx.titanshot;
			powerUsed = 1.2f;
		}
	};
}
