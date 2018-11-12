package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static int HEALTH = 350;
	public static int HEALTH2 = 350;
	
	private float greenValue = 255;
	private float greenValue2 = 255;
	
	private int level = 1;
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean tick() {		
		HEALTH = (int)(Game.clamp(HEALTH, 0, 350));
		HEALTH2 = (int)(Game.clamp(HEALTH2,  0,  350));
		
		greenValue = (float)(HEALTH / 5) * 3;
		greenValue2 = (float)(HEALTH2 / 5) * 3;
		
		greenValue = Game.clamp(Math.round(greenValue), 0, 255);
		greenValue2 = Game.clamp(Math.round(greenValue2), 0, 255);
		
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
		//left Health bar
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH/2-375, 15, 350, 24);
		g.setColor(new Color(150, Math.round(greenValue), 0));
		g.fillRect(Game.WIDTH/2-375, 15, HEALTH, 24);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/2-375, 15, HEALTH, 24);
		
		//right Health bar
		g.setColor(Color.gray);
		g.fillRect(Game.WIDTH/2+25, 15, 350, 24);
		g.setColor(new Color(150, Math.round(greenValue), 0));
		g.fillRect(Game.WIDTH/2+25, 15, HEALTH2, 24);
		g.setColor(Color.white);
		g.drawRect(Game.WIDTH/2+25, 15, HEALTH2, 24);
	}

}
