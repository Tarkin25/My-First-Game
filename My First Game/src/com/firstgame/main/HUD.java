package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static int HEALTH = 350;
	public static int HEALTH2 = 350;
	
	public boolean tick() {		
		HEALTH = Game.clamp(HEALTH, 0, 350);
		HEALTH2 = Game.clamp(HEALTH2,  0,  350);
		
		if(HEALTH>0 && HEALTH2>0) return true;
		else {
			if(HEALTH>0) {
				System.out.println("Spieler 1 hat gewonnen!");
			}
			else {
				System.out.println("Spieler 2 hat gewonnen!");
			}
			//TODO System.out.println("Press 'r' to play again");
			return false;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH/2-375, 15, 350, 24);
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH/2-375, 15, HEALTH, 24);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/2-375, 15, HEALTH, 24);
		
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH/2+25, 15, 350, 24);
		g.setColor(Color.green);
		g.fillRect(Game.WIDTH/2+25, 15, HEALTH2, 24);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/2+25, 15, HEALTH2, 24);
	}

}
