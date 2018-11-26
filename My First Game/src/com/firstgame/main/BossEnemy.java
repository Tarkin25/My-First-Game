package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossEnemy extends GameObject {
	
	Random r;
	
	Handler handler;
	
	int timer1 = 40;
	int timer2 = 50;
	
	public BossEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		r = new Random();
		
		speedX = 0;
		
		speedY = 3;
		
		damage = 2;
		
		this.handler = handler;
	}

	public void tick() {
		
		if(timer1 <= 0) {
			speedY = 0;
			timer2--;
		}	
		else {
			timer1--;
		}
		
		if(timer2 <= 0) {
			if(speedX == 0) {
				speedX = 3;
			}
			
			int spawn = r.nextInt(10);
			
			if(spawn == 0) {
				handler.addObject(new BossBullet(x+48, y+48, ID.BossEnemy, handler));
			}
		}
		
		if(x >= Game.WIDTH-96) {
			speedX = 3;
			speedX *= -1;
		}
		
		if(x <= 0) {
			speedX = 3;
		}
		
		x += speedX;
		y += speedY;
	}

	public void render(Graphics g) {	
		g.setColor(Color.red);
		g.fillRect((int)(x), (int)(y), 96, 96);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x),(int)(y),96,96);
	}
	
	public void getNearest() {

	}

}
