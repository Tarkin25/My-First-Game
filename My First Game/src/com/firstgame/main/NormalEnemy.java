package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class NormalEnemy extends GameObject {
	
	Random r;
	
	public NormalEnemy(int x, int y, ID id) {
		super(x, y, id);
		
		r = new Random();
		
		speedX = r.nextInt(5);
		
		speedY = r.nextInt(5);
		
		damage = 2;
	}

	public void tick() {
		if(y <= 0) {
			speedY = r.nextInt(5);
		}
		
		if(y >= Game.HEIGHT-48) {
			speedY = r.nextInt(5);
			speedY *= -1; 
		}
		
		if(x >= Game.WIDTH-16) {
			speedX = r.nextInt(5);
			speedX *= -1;
		}
		
		if(x <= 0) {
			speedX = r.nextInt(5);
		}
		
		x += speedX;
		y += speedY;
	}

	public void render(Graphics g) {	
		g.setColor(Color.red);
		g.fillRect(x, y, 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,16,16);
	}

}
