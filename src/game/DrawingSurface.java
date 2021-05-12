package game;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;

import organisms.Organism;

import processing.core.PApplet;
import sprite.Player;
import sprite.Sprite;

public class DrawingSurface extends PApplet {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public ArrayList<Integer> keysHeld;
	ArrayList<GButton> buttons;
	private int animalDrawn;
	
	public int gameAreaWidth;
	public int gameAreaHeight;
	public ArrayList<Sprite> obstacles;
	private static ArrayList<Organism> organisms;
	public ArrayList<Player> players;
	public Player thisPlayer;

	public DatabaseReference room;
	public DatabaseReference thisPlayerRef;
	
	public DrawingSurface(DatabaseReference room) {
		keysHeld = new ArrayList<Integer>();
		
		gameAreaWidth = 5000;
		gameAreaHeight = 3000;
		
		obstacles = new ArrayList<Sprite>();
		buttons = new ArrayList<GButton>();
		players = new ArrayList<Player>();
		
		this.room = room;
		thisPlayerRef = room.child("players").push();
	}
	
	@Override
	public void setup() {
		buttons.add(new GButton(this, 40,  500, 80, 30, "Button 1"));
		buttons.add(new GButton(this, 190, 500, 80, 30, "Button 2"));
		buttons.add(new GButton(this, 340, 500, 80, 30, "Button 3"));
		buttons.add(new GButton(this, 490, 500, 80, 30, "Button 4"));
		buttons.add(new GButton(this, 640, 500, 80, 30, "Button 5"));
		for (GButton b : buttons)
			b.fireAllEvents(true);

		animalDrawn = 0;
		
		thisPlayer = new Player(30, 30, loadImage("player.png"));
		obstacles.add(new Sprite(150, 150, 300, 50, loadImage("obstacle.png")));
		thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (players.size() == 0)
					room.removeValueAsync();
				else
					thisPlayerRef.removeValueAsync();
			}
		});
	}
	
	public void update() {
		thisPlayer.update(this);
		if (thisPlayer.hasChanged())
			thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
	}

	@Override
	public void draw() {
		update();
		
		background(150, 150, 150);
		
		pushMatrix();
		
		// Center the game field on the player
		translate(WIDTH/2, HEIGHT/2);
		translate(-(float)thisPlayer.getX(), -(float)thisPlayer.getY());
		
		fill(150, 100, 0);
		rect(0, 0, gameAreaWidth, gameAreaHeight); // map background
		
		thisPlayer.draw(this);
		for (Sprite s : obstacles)
			s.draw(this);
		
		popMatrix();
		
		// ALL UI ELEMENTS DRAW BELOW HERE
		
		String text = "animal1: " + "number    " + "animal2: " + "number    " + "animal3: " + "number    " + "animal4: "+ "number    " + "animal5: " + "number    " + "Animal selected: " + animalDrawn + "     coins: " + "number";
		fill(0);
		text(text, 10, 20);
	}
	
	public Point2D.Double windowToGameField(double x, double y) {
		return new Point2D.Double(x + thisPlayer.getX() - WIDTH/2, y + thisPlayer.getY() - HEIGHT/2);
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
	
	public void mousePressed() {
		if (animalDrawn >= 1 && animalDrawn <= 5) {
			Point2D.Double newLocation = windowToGameField(mouseX, mouseY);
			obstacles.add(new Sprite(newLocation.x-5, newLocation.y-5, 10, 10, loadImage("obstacle.png")));
			animalDrawn = 0;
		}
	}

	public void handleButtonEvents(GButton button, GEvent event) {
		if (event == GEvent.CLICKED) {
			for (int i = 0; i < buttons.size(); i++) {
				if (button == buttons.get(i)) {
					System.out.println(i);
					animalDrawn = i+1;
				}
			}
		}
	}
	
	public boolean remove(Organism o) {
		return organisms.remove(o);
	}
	
	public void add(Organism o) {
		organisms.add(o);
	}
}
