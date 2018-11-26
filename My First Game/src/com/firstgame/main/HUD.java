package com.firstgame.main;

import java.awt.Color;
import java.awt.Graphics;

public class HUD {
	
	public static int HEALTH = 350;
	public static int HEALTH2 = 350;
	public static int HEALTH3 = 350;
	
	private float greenValue = 255;
	private float greenValue2 = 255;
	private float greenValue3 = 255;
	
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
		HEALTH3 = (int)(Game.clamp(HEALTH3, 0, 350));
		
		greenValue = (float)(HEALTH / 5) * 3;
		greenValue2 = (float)(HEALTH2 / 5) * 3;
		greenValue3 = (float)(HEALTH3 / 5) * 3;
		
		greenValue = Game.clamp(Math.round(greenValue), 0, 255);
		greenValue2 = Game.clamp(Math.round(greenValue2), 0, 255);
		greenValue3 = Game.clamp(Math.round(greenValue3), 0, 255);
		
		if(HEALTH>0 && HEALTH2>0 && HEALTH3>0) return true;
		else {
			if(HEALTH==0) {
				System.out.println("Blau hat gewonnen!");
			}
			else if(HEALTH2==0) {
				System.out.println("Weiss hat gewonnen!");
			}

			return false;
		}
	}
	
	public void render(Graphics g, String mode) {
		if(mode == "Two players") {
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
		
		if(mode == "Smart player") {
			//draw Health bar
			g.setColor(Color.gray);
			g.fillRect(Game.WIDTH/2-375, 15, 350, 24);
			g.setColor(new Color(150, Math.round(greenValue3), 0));
			g.fillRect(Game.WIDTH/2-375, 15, HEALTH3, 24);
			g.setColor(Color.white);
			g.drawRect(Game.WIDTH/2-375, 15, HEALTH3, 24);
		}
	}

}
