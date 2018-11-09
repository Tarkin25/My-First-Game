package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player2 extends GameObject {
	
	Handler handler;
	
	public Player2(int x, int y, ID id, Handler handler) {
		super(x,y,id);
		this.handler = handler;
		
	}

	public void tick() {
		
		x += speedX;
		y += speedY;
		
		x = Game.clamp(x, 0, Game.WIDTH-36);
		y = Game.clamp(y, 0, Game.HEIGHT-60);
		
		collision();
	}
	
	private void collision() {
		for(int i=0;i<handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.SlowEnemy || tempObject.getId() == ID.NormalEnemy || tempObject.getId() == ID.FastEnemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					HUD.HEALTH2 -= tempObject.getDamage();
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, 32, 32);
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}

}
