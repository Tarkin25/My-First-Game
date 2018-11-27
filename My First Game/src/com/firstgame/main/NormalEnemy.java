package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class NormalEnemy extends GameObject {
	
	Random r;
	
	public NormalEnemy(float x, float y, ID id) {
		super(x, y, id);
		
		r = new Random();
		
		speedX = r.nextFloat()*5;
		
		speedY = r.nextFloat()*5;
		
		damage = 2;
	}

	public void tick() {
		if(y <= 0) {
			speedY = r.nextFloat()*5;
		}
		
		if(y >= Game.HEIGHT-48) {
			speedY = r.nextFloat()*5;
			speedY *= -1; 
		}
		
		if(x >= Game.WIDTH-16) {
			speedX = r.nextFloat()*5;
			speedX *= -1;
		}
		
		if(x <= 0) {
			speedX = r.nextFloat()*5;
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
