package game;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;

import organisms.Organism;
import networking.NetworkingHandler;

import processing.core.PApplet;
import sprite.Player;
import sprite.Sprite;

public class DrawingSurface extends PApplet {
	
	public Screen currentScreen;
	public MainScreen main;
	public MenuScreen menu;
	public ArrayList<Integer> keysHeld;
	
	public NetworkingHandler net;
	
	public DrawingSurface() {
		net = new NetworkingHandler(this);
		keysHeld = new ArrayList<Integer>();
	}
		

	/*public void settings() {
		size(600, 440);
	}*/

	

	public void setup() {
		main = new MainScreen(this);
		main.setup();
		main.setActive(false);
		
		menu = new MenuScreen(this);
		menu.setup();
		currentScreen = menu;
	}

	public void draw() {
		currentScreen.update();
		currentScreen.draw();
	}
	
	public void switchScreen(Screen newScreen) {
		currentScreen.setActive(false);
		newScreen.setActive(true);
		currentScreen = newScreen;
	}
	
	public void keyPressed() {
		if (!keysHeld.contains(keyCode))
			keysHeld.add(keyCode);
	}
	
	public void keyReleased() {
		keysHeld.remove(new Integer(keyCode));
	}
	
	public boolean isKeyHeld(Integer code) {
		return keysHeld.contains(code);
	}
	
	public int getXAxisInput() {
		if (isKeyHeld(RIGHT))
			return 1;
		if (isKeyHeld(LEFT))
			return -1;
		return 0;
	}
	
	public int getYAxisInput() {
		if (isKeyHeld(UP))
			return 1;
		if (isKeyHeld(DOWN))
			return -1;
		return 0;
	}

	public void handleButtonEvents(GButton button, GEvent event) {
		currentScreen.handleButtonEvents(button, event);
	}
}
