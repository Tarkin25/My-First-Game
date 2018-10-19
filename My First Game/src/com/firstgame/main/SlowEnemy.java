package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class SlowEnemy extends GameObject {
	
	Random r;
	
	public SlowEnemy(int x, int y, ID id) {
		super(x, y, id);
		
		r = new Random();
		
		speedX = r.nextInt(5);
		
		speedY = r.nextInt(5);
		
		int damage = 10;
	}

	public void tick() {
		x += speedX;
		y += speedY;
		
		if(y <= 0 || y >= Game.HEIGHT-48) speedY *= -1;
		if(x <= 0 || x >= Game.WIDTH-16) speedX *= -1;
	}

	public void render(Graphics g) {	
		g.setColor(Color.red);
		g.fillRect(x, y, 16, 16);
	}

}
