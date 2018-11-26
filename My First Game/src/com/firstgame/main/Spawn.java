package com.firstgame.main;

import java.util.LinkedList;
import java.util.Random;

public class Spawn {
	
	private Handler handler;
	private HUD hud;
	
	private Random r = new Random();
	int chance;
	int smart = 1;
	int smart2 = 1;
	
	SmartPlayer smartPlayer;
	
	private int tempScore = 0;
	private int level = 1;
	int bossTimer = 101;
	
	LinkedList<GameObject> savedEnemies = new LinkedList<GameObject>();
	
	public Spawn(Handler handler, HUD hud, SmartPlayer smartPlayer) {
		this.handler = handler;
		this.hud = hud;
		this.smartPlayer = smartPlayer;
	}
	
	public void tick() {
		tempScore++;
		
		if(tempScore>=1000 && level < 10) {
			tempScore = 0;
			level++;
			hud.setLevel(hud.getLevel() + 1);
			
			chance = r.nextInt(100);
			
			if(chance<20) {
				handler.addObject(new SlowEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SlowEnemy));
			}
			else if(chance>19 && chance<60) {
				handler.addObject(new NormalEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.NormalEnemy));
			}
			else {
				handler.addObject(new FastEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.FastEnemy));
			}
			
			for(int i=0;i<handler.object.size();i++) {
				if(handler.object.get(i).getId() == ID.SmartPlayer) {
					handler.object.get(i).getNearest();
				}
			}
			
		}
		
		if(level == 10 && tempScore <= 2000) {
			
			for(int i=0;i<handler.object.size();i++) {
				if(handler.object.get(i).getId() != ID.Player && handler.object.get(i).getId() != ID.Player2 && handler.object.get(i).getId() != ID.BossEnemy && handler.object.get(i).getId() != ID.BossBullet) {
					savedEnemies.add(handler.object.get(i));
				}
			}
			
			for(int i=0;i<handler.object.size();i++) {
				if(handler.object.get(i).getId() != ID.Player && handler.object.get(i).getId() != ID.Player2 && handler.object.get(i).getId() != ID.BossEnemy && handler.object.get(i).getId() != ID.BossBullet) {
					handler.removeObject(handler.object.get(i));
				}
			}
			
			if(tempScore == 0) {
				handler.addObject(new BossEnemy(Game.WIDTH/2, 0, ID.BossEnemy, handler));
			}
			
		}
		
		if(tempScore>=2000 && level == 10) {
			tempScore = 0;
			for(int i=0;i<handler.object.size();i++) {
				if(handler.object.get(i).getId() == ID.BossEnemy) {
					handler.removeObject(handler.object.get(i));
				}
			}
			
			for(int i=0;i<savedEnemies.size();i++) {
				handler.addObject(savedEnemies.get(i));
			}
			level = 1;
		}
		
		if(HUD.HEALTH2<100) {
			if(smart2>0) {
				handler.addObject(new SmartEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SmartEnemy, handler));
				smart2 = 0;
			}
		}
		
		if(HUD.HEALTH<100) {
			if(smart>0) {
				handler.addObject(new SmartEnemy2(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SmartEnemy, handler));
				smart = 0;
			}
		}
	}

}
