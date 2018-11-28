package com.firstgame.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.firstgame.main.Game.STATE;

public class Menu extends MouseAdapter {
	
	private Game game;
	private Handler handler;
	
	public Menu(Game game) {
		this.game = game;
		this.handler = game.handler;
	}
	
	public void mousePressed(MouseEvent e) {
		
		int mx = e.getX();
		int my = e.getY();
		
		if(game.gameState == STATE.Menu) {
			if(mouseOver(mx,my,Game.WIDTH/2-100,100,200,50)) {
				game.gameState = STATE.PvP;
				game.twoPlayers();
			}
			if(mouseOver(mx,my,Game.WIDTH/2-100,175,200,50)) {
				game.gameState = STATE.Smart;
				game.smartPlayer();
			}
			if(mouseOver(mx,my,Game.WIDTH/2-100,250,200,50)) {
				System.exit(1);
			}
		}
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < mx+width) {
			if(my > y && my < my+height) {
				return true;
			}else return false;
		}else return false;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font font = new Font("cambria", 1, 50);
		Font font2 = new Font("cambria", 1, 35);
		
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("Menu", Game.WIDTH/2-80, 75);
		
		g.setFont(font2);
		g.drawString("PvP", Game.WIDTH/2-40, 135);
		g.drawString("AI", Game.WIDTH/2-47, 210);
		g.drawString("Exit", Game.WIDTH/2-33, 285);
		
		g.drawRect(Game.WIDTH/2 - 100, 100, 200, 50);
		g.drawRect(Game.WIDTH/2 - 100, 175, 200, 50);
		g.drawRect(Game.WIDTH/2 - 100, 250, 200, 50);
	}

}
