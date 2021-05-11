package game;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import organisms.Organism;
import sprite.Player;
import sprite.Sprite;

public class MainScreen extends Screen {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	DrawingSurface surface;
	
	public ArrayList<Sprite> obstacles;
	private static ArrayList<Organism> organisms;
	public Player player1;
	
	public int gameAreaWidth;
	public int gameAreaHeight;
	
	ArrayList<GButton> buttons;
	GLabel lblOut;
	private int animalDrawn;
	
	public MainScreen(DrawingSurface surface) {
		super(800, 600);
		this.surface = surface;
		
		obstacles = new ArrayList<Sprite>();
		buttons = new ArrayList<GButton>();
		
		gameAreaWidth = 5000;
		gameAreaHeight = 3000;
	}
	
	@Override
	public void setup() {
		buttons.add(new GButton(surface, 40,  500, 80, 30, "Button 1"));
		buttons.add(new GButton(surface, 190, 500, 80, 30, "Button 2"));
		buttons.add(new GButton(surface, 340, 500, 80, 30, "Button 3"));
		buttons.add(new GButton(surface, 490, 500, 80, 30, "Button 4"));
		buttons.add(new GButton(surface, 640, 500, 80, 30, "Button 5"));
		for (GButton b : buttons)
			b.fireAllEvents(true);

		lblOut = new GLabel(surface, 50, 30, 560, 20, "");
		animalDrawn = 0;
		
		player1 = new Player(30, 30, surface.loadImage("player.png"));
		obstacles.add(new Sprite(150, 150, 300, 50, surface.loadImage("obstacle.png")));
	}
	
	public void update() {
		player1.update(surface);
	}
	
	public void draw() {
		/* DRAWING */
		
		surface.background(150, 150, 150);
		
		surface.pushMatrix();
		
		// Center the game field on the player
		surface.translate(WIDTH/2, HEIGHT/2);
		surface.translate(-(float)player1.getX(), -(float)player1.getY());
		
		surface.fill(150, 100, 0);
		surface.rect(0, 0, gameAreaWidth, gameAreaHeight); // map background
		
		player1.draw(surface);
		for (Sprite s : obstacles)
			s.draw(surface);
		
		surface.popMatrix();
		
		// ALL UI ELEMENTS DRAW BELOW HERE
		
		String text = "animal1: " + "number    " + "animal2: " + "number    " + "animal3: " + "number    " + "animal4: "+ "number    " + "animal5: " + "number    " + "Animal selected: " + animalDrawn + "     coins: " + "number";
		surface.fill(0);
		surface.text(text, 10, 20);
	}
	
	public Point2D.Double windowToGameField(double x, double y) {
		return new Point2D.Double(x + player1.getX() - WIDTH/2, y + player1.getY() - HEIGHT/2);
	}
	
	public void mousePressed() {
		if (animalDrawn >= 1 && animalDrawn <= 5) {
			Point2D.Double newLocation = windowToGameField(surface.mouseX, surface.mouseY);
			obstacles.add(new Sprite(newLocation.x-5, newLocation.y-5, 10, 10, surface.loadImage("obstacle.png")));
			animalDrawn = 0;
		}
	}
	
	public void handleButtonEvents(GButton button, GEvent event) {
		if (event == GEvent.CLICKED) {
			for (int i = 0; i < buttons.size(); i++) {
				if (button == buttons.get(i)) {
					System.out.println(i);
					animalDrawn = i;
				}
			}
		}
	}
	
	public void remove(Organism o) {
		organisms.remove(o);
	}
	
	public void add(Organism o) {
		organisms.add(o);
	}

}
