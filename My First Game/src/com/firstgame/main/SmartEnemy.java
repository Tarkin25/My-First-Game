package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class SmartEnemy extends GameObject {
	
	Random r;
	Handler handler;
	
	private GameObject player;
	
	public SmartEnemy(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		for(int i=0;i<handler.object.size();i++) {
			if(handler.object.get(i).getId() == ID.Player) {
				player = handler.object.get(i);
			}
		}
		
		speedX = 5;
		
		speedY = 5;
		
		damage = 2;
	}

	public void tick() {		
		if(player!=null) {
			float diffX = x - player.getX() - 8;
			float diffY = y - player.getY();
			float distance = (float)(Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY())));
			
			speedX = (float)((-1.0/distance) * diffX);
			speedY = (float)((-1.0/distance) * diffY);
		}
		
		x += speedX;
		y += speedY;
	}

	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int)(x), (int)(y), 16, 16);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x),(int)(y),16,16);
	}
	
	public void getNearest() {

	}

}
