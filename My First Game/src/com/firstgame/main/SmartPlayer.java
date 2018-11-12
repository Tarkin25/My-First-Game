package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

public class SmartPlayer extends GameObject {
	
	Random r;
	Handler handler;
	
	private int ticks = 0;
	
	LinkedList<GameObject> enemy = new LinkedList<GameObject>();
	private GameObject nearest;
	
	public SmartPlayer(float x, float y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		getNearest();
	}

	public void tick() {	
		ticks++;
		
		if(ticks>10) {
			getNearest();
			ticks = 0;
		}
		
		//calculate the directions which lead to "nearest"
		float diffX = x - nearest.getX() - 8;
		float diffY = y - nearest.getY();
		float distance = (float)(Math.sqrt((x-nearest.getX())*(x-nearest.getX()) + (y-nearest.getY())*(y-nearest.getY())));
		
		//when the distance is more than 200, move around like a normal enemy
		if(distance > 200) {
			if(y < 0 || y > Game.HEIGHT-51) {
				speedY *= -1;
			}
			
			if(x < 0 || x > Game.WIDTH-48) {
				speedX *= -1;
			}
		}
		
		//else avoid the nearest enemy + bounce off the borders
		else {
			speedX = (float)((-1.0/distance) * diffX) * (-1);
			speedY = (float)((-1.0/distance) * diffY) * (-1);
			
			if(y < 0 || y > Game.HEIGHT-100) {
				speedY *= (-1);
			}
			
			if(x < 0 || x > Game.WIDTH-48) {
				speedX *= (-1);
			}
		}
		
		x += Game.clamp(speedX, -5, 5);
		y += Game.clamp(speedY, -5, 5);
	}
	
	public void getNearest() {
		//enlist every object that is an enemy
		for(int i=0;i<handler.object.size();i++) {
			if(handler.object.get(i).getId() != ID.Player && handler.object.get(i).getId() != ID.Player2 && handler.object.get(i).getId() != ID.SmartPlayer) {				
				enemy.add(handler.object.get(i));
			}
		}
		
		//take the nearest enemy as "nearest"
		for(int i=0;i<enemy.size()-1;i++) {
			if((float)(Math.sqrt((x-enemy.get(i).getX())*(x-enemy.get(i).getX()) + (y-enemy.get(i).getY())*(y-enemy.get(i).getY()))) < (float)(Math.sqrt((x-enemy.get(i+1).getX())*(x-enemy.get(i+1).getX()) + (y-enemy.get(i+1).getY())*(y-enemy.get(i+1).getY())))) {
				nearest = enemy.get(i);
			}
			else {
				nearest = enemy.get(i+1);
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillRect((int)(x), (int)(y), 32, 32);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)(x),(int)(y),32,32);
	}

}
