package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Bush extends GameObject {

	public Bush(int x, int y, ID id) {
		super(x, y, id);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, 65, 50);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,0,0);
	}

}
