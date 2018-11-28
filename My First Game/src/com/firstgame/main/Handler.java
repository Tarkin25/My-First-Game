package com.firstgame.main;

import java.awt.Graphics;
import java.util.LinkedList;

import com.firstgame.main.Game.STATE;

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	Game game;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public void tick() {		
		if(game.gameState != STATE.Menu) {
			for(int i=0; i<object.size(); i++) {
				GameObject tempObject = object.get(i);
				
				tempObject.tick();
			}	
		}
	}
	
	public void render(Graphics g) {
		if(game.gameState != STATE.Menu) {
			for(int i=0; i<object.size(); i++) {
				GameObject tempObject = object.get(i);
				
				tempObject.render(g);
			}
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

}
