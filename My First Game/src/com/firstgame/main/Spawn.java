package com.firstgame.main;

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
	
	public Spawn(Handler handler, HUD hud, SmartPlayer smartPlayer) {
		this.handler = handler;
		this.hud = hud;
		this.smartPlayer = smartPlayer;
	}
	
	public void tick() {
		tempScore++;
		
		if(tempScore>=1000) {
			tempScore = 0;
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
			
			smartPlayer.getNearest();
		}
		
		if(HUD.HEALTH2<200) {
			if(smart2>0) {
				handler.addObject(new SmartEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SmartEnemy, handler));
				smart2 = 0;
			}
		}
		
		if(HUD.HEALTH<200) {
			if(smart>0) {
				handler.addObject(new SmartEnemy2(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SmartEnemy, handler));
				smart = 0;
			}
		}
	}

}
