package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BossBullet extends GameObject {
	
	Random r;
	Handler handler;
	
	public BossBullet(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		
		r = new Random();
		
		speedX = r.nextFloat()*8;
		
		speedY = 8;
		
		damage = 2;
		
		this.handler = handler;
	}

	public void tick() {
		if(y <= 0) {
			speedY = 8;
		}
		
		if(y >= Game.HEIGHT-48) {
			speedY = 8;
			speedY *= -1; 
		}
		
		if(x >= Game.WIDTH-16) {
			speedX = r.nextFloat()*8;
			speedX *= -1;
		}
		
		if(x <= 0) {
			speedX = r.nextFloat()*8;
		}
		
		if(y <= 0 || y >= Game.HEIGHT-48 || x >= Game.WIDTH-16 || x <= 0) {
			handler.removeObject(this);
		}
		
		x += speedX;
		y += speedY;
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)(x), (int)(y), 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x),(int)(y),16,16);
	}
	
	public void getNearest() {

	}

}
