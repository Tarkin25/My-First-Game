package com.firstgame.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	
	private boolean[] keyDown = new boolean[8];
	
	public KeyInput(Handler handler) {
		this.handler = handler;
		
		for(int i=0;i<8;i++) {
			keyDown[i] = false;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player2) {
				// key events for all objects with ID = Player2
				
				if(key == KeyEvent.VK_UP) {
					tempObject.setSpeedY(-5);
					keyDown[0] = true;
				}
				
				if(key == KeyEvent.VK_DOWN) {
					tempObject.setSpeedY(5);
					keyDown[1] = true;
				}
				
				if(key == KeyEvent.VK_LEFT) {
					tempObject.setSpeedX(-5);
					keyDown[2] = true;
				}
				
				if(key == KeyEvent.VK_RIGHT) {
					tempObject.setSpeedX(5);
					keyDown[3] = true;
				}
			}
			
			if(tempObject.getId() == ID.Player) {
				// key events for all objects with ID = Player
				
				if(key == KeyEvent.VK_W) {
					tempObject.setSpeedY(-5);
					keyDown[4] = true;
				}
				
				if(key == KeyEvent.VK_S) {
					tempObject.setSpeedY(5);
					keyDown[5] = true;
				}
				
				if(key == KeyEvent.VK_A) {
					tempObject.setSpeedX(-5);
					keyDown[6] = true;
				}
				
				if(key == KeyEvent.VK_D) {
					tempObject.setSpeedX(5);
					keyDown[7] = true;
				}
			}
			
		}
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player2) {
				//key events for all objects with ID = Player2
				
				if(key == KeyEvent.VK_UP) {
					//tempObject.setSpeedY(0);
					keyDown[0] = false;
				}
				
				if(key == KeyEvent.VK_DOWN) {
					//tempObject.setSpeedY(0);
					keyDown[1] = false;
				}
				
				if(key == KeyEvent.VK_LEFT) {
					//tempObject.setSpeedX(0);
					keyDown[2] = false;
				}
				
				if(key == KeyEvent.VK_RIGHT) {
					//tempObject.setSpeedX(0);
					keyDown[3] = false;
				}
				
				//vertical movement
				if(!keyDown[0] && !keyDown[1]) tempObject.setSpeedY(0);
				
				//horizontal movement
				if(!keyDown[2] && !keyDown[3]) tempObject.setSpeedX(0);
				
			}
			
			if(tempObject.getId() == ID.Player) {
				//key events for all objects with ID = Player
				
				if(key == KeyEvent.VK_W) {
					//tempObject.setSpeedY(0);
					keyDown[4] = false;
				}
				
				if(key == KeyEvent.VK_S) {
					//tempObject.setSpeedY(0);
					keyDown[5] = false;
				}
				
				if(key == KeyEvent.VK_A) {
					//tempObject.setSpeedX(0);
					keyDown[6] = false;
				}
				
				if(key == KeyEvent.VK_D) {
					//tempObject.setSpeedX(0);
					keyDown[7] = false;
				}
				
				//vertical movement
				if(!keyDown[4] && !keyDown[5]) tempObject.setSpeedY(0);
				
				//horizontal movement
				if(!keyDown[6] && !keyDown[7]) tempObject.setSpeedX(0);
			}
		}
	}

}
