package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class NormalEnemy extends GameObject {
	
	Random r;
	
	public NormalEnemy(int x, int y, ID id) {
		super(x, y, id);
		
		r = new Random();
		
		speedX = r.nextInt(5);
		
		speedY = r.nextInt(5);
		
		int damage = 5;
	}

	public void tick() {
		x += directX(x,speedX);
		y += directY(y, speedY);
	}

	public void render(Graphics g) {	
		g.setColor(Color.red);
		g.fillRect(x, y, 16, 16);
	}

}
