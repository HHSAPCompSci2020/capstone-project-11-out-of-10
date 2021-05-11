package game;

import g4p_controls.GButton;
import g4p_controls.GEvent;

public class MenuScreen extends Screen {
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	DrawingSurface surface;
	
	GButton startButton;

	public MenuScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		startButton = new GButton(surface, WIDTH/2 - 25, HEIGHT/2 - 15, 50, 30);
	}
	
	@Override
	public void setup() {
		
	}
	
	@Override
	public void draw() {
		
	}
	
	public void handleButtonEvents(GButton button, GEvent event) {
		if (event == GEvent.CLICKED) {
			if (button == startButton) {
				surface.currentScreen = surface.main;
				surface.net.setup("playerA");
			}
		}
	}
	
}
