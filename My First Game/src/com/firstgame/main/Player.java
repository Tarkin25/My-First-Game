package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;

public class Player extends GameObject {
	
	public Player(int x, int y, ID id) {
		super(x,y,id);
		
	}

	public void tick() {
		x += speedX;
		y += speedY;
		
		x = Game.clamp(x, 0, Game.WIDTH-36);
		y = Game.clamp(y, 0, Game.HEIGHT-60);
	}

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, 32, 32);
	}

}
