package com.firstgame.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -6494256809522819072L;
	
	
	public static final int WIDTH = 800, HEIGHT = WIDTH / 12 * 9;
	
	private Thread thread;
	private boolean running = false;	
	private Random r;	
	public Handler handler;
	private HUD hud;	
	private Spawn spawner;	
	public SmartPlayer smartPlayer;	
	private String mode;
	private Menu menu;
	public boolean died = false;
	
	public enum STATE {
		Menu,
		PvP,
		Smart
	};
	
	public STATE gameState = STATE.Menu;

	public Game() {
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler);
		
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(menu);
		
		new Window(WIDTH, HEIGHT, "My First Game!", this);
		
		r = new Random();
	}
	
	public void smartPlayer() {
		mode = "Smart player";
		
		for(int i=0;i<5;i++) {
			int chance = r.nextInt(100);
			
			if(chance<20) {
				handler.addObject(new SlowEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SlowEnemy));
			}
			else if(chance<70) {
				handler.addObject(new NormalEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.NormalEnemy));
			}
			else {
				handler.addObject(new FastEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.FastEnemy));
			}
		}
		
		smartPlayer = new SmartPlayer(WIDTH/2-32, HEIGHT/2-32, ID.SmartPlayer, handler);
		handler.addObject(smartPlayer);
		
		spawner = new Spawn(handler, hud, smartPlayer);
	}
	
	public void twoPlayers() {
		mode = "Two players";
		
		handler.addObject(new Player(WIDTH/2-32,HEIGHT/2-32, ID.Player, handler));
		handler.addObject(new Player2(WIDTH/2-50, HEIGHT/2-10, ID.Player2, handler));
		
		for(int i=0;i<10;i++) {
			int chance = r.nextInt(100);
			
			if(chance<20) {
				handler.addObject(new SlowEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.SlowEnemy));
			}
			else if(chance<70) {
				handler.addObject(new NormalEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.NormalEnemy));
			}
			else {
				handler.addObject(new FastEnemy(r.nextFloat()*Game.WIDTH, r.nextFloat()*Game.HEIGHT, ID.FastEnemy));
			}
		}
		
		spawner = new Spawn(handler, hud, null);
	}

	public synchronized void start() {
		
		thread = new Thread(this);
		thread.start();
		running = true;
		
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() { 
		
		this.requestFocus();
		
		//This is a game loop I copied from the Internet, it is used by many programs. 
		//The game loop updates the game window as fast as it's able to and displays the current refreshing rate (FPS) in the console.		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running) {
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta>=1) {
				tick();
				delta--;
			}
			
			if(running) {
				render();
				frames++;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			}
			
		}
		
		stop();
		
	}
	
	private void tick() {		
		if(gameState == STATE.PvP || gameState == STATE.Smart) {
			if(hud.tick()) {
				handler.tick();
				spawner.tick();
			}
			else {
				died = true;
				gameState = STATE.Menu;
			}
		}
		
		if(gameState == STATE.Menu) {
			menu.tick();
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == STATE.PvP || gameState == STATE.Smart) {
			hud.render(g, mode);
		}
		else if(gameState == STATE.Menu) {
			if(died == true) {
				g.dispose();
				bs.show();
			}
			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var <= min) {
			return var = min;
		}
		
		else if(var >= max) {
			return var = max;
		}
		
		else {
			return var;
		}
	}
	
	

	public static void main(String[] args) {
		
		new Game();

	}

}
