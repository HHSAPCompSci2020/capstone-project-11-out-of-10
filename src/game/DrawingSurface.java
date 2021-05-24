package game;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import networking.AnimalPost;
import networking.OrganismPost;
import networking.PlayerPost;
import networking.TreePost;
import organisms.*;
import processing.core.PApplet;
import processing.core.PImage;
import sprite.Player;
import sprite.Sprite;

import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;

/**
 * Draws and handles game logic
 * @author Timothy Li and Nir Reiter
 *
 */
public class DrawingSurface extends PApplet implements JayLayerListener {

	/**
	 * width of window
	 */
	public static final int WIDTH = 800;
	/**
	 * height of window
	 */
	public static final int HEIGHT = 600;
	/**
	 * how often certain actions are carried out
	 */
	public static final int TICK_RATE = 3000; // milliseconds between ticks
	/**
	 * names of possible organisms to be drawn
	 */
	public static final String[] TYPES = {"tree", "moss", "mouse", "bird", "ram"};
	private static final String[] TYPE_NAMES = {"Yellowberry Tree", "Glowing Moss", "Mouse Hopper", "Flame Bird", "Fluffy Ram"};
	
	private ArrayList<Integer> keysHeld;
	private ArrayList<GButton> buttons;
	private String animalDrawn;
	private int lastTick;
	
	/**
	 * width of the game area
	 */
	public int gameAreaWidth;
	/**
	 * height of the game area
	 */
	public int gameAreaHeight;
	/**
	 * a list of obstacles in the game
	 */
	public ArrayList<Sprite> obstacles;
	private HashMap<String, Organism> organisms;
	private HashMap<String, Organism> allOrganisms;
	private HashMap<String, Player> players;
	private Player thisPlayer;
	private Sound s;
	
	private static PImage playerImage;
	private static PImage obstacleImage;
	/**
	 * a map with organism name corresponding to organism images
	 */
	public static HashMap<String, PImage> organismImages;

	private DatabaseReference room;
	private DatabaseReference thisPlayerRef;
	private DatabaseReference openRoom;
	private int playerMax;
	
	private static final int GAME_LENGTH = 3*60*1000; // 3 minutes
	private boolean gameStarted;
	private boolean gameFinished;
	private int gameStartTime;
	private String winners;
	
	/**
	 * Create a new DrawingSurface which uses a room in the database
	 * @param room The DatabaseReference for the room the player is in
	 */
	public DrawingSurface(DatabaseReference openRoom, DatabaseReference room, int playerMax, String playerName) {
		keysHeld = new ArrayList<Integer>();
		animalDrawn = null;
		String[] soundEffects = new String[]{"title1.mp3","title2.mp3","title3.mp3","title4.mp3","title5.mp3"};
		
		gameAreaWidth = 3000;
		gameAreaHeight = 1500;
		
		obstacles = new ArrayList<>();
		buttons = new ArrayList<>();
		players = new HashMap<>();
		organisms = new HashMap<>();
		allOrganisms = new HashMap<>();
		organismImages = new HashMap<>();
		
		this.openRoom = openRoom;
		this.room = room;
		this.playerMax = playerMax;
		thisPlayerRef = room.child("players").push();
		thisPlayer = new Player(0, 0, null, playerName);
		
		room.child("players").addChildEventListener(new PlayerListener());
		for (int i = 0; i < TYPES.length; i++)
			room.child("organisms").child(TYPES[i]).addChildEventListener(new OrganismListener(TYPES[i]));
		
		s = new Sound();
		
		gameStarted = false;
		gameStartTime = 0;
		gameFinished = false;
		winners = "";
	}
	
	@Override
	public void setup() {
		playerImage = loadImage("player.png");
		obstacleImage = loadImage("obstacle.png");
		organismImages.put("tree", loadImage("yellowberry_tree.png"));
		organismImages.put("moss", loadImage("glowing_moss.png"));
		organismImages.put("mouse", loadImage("mouse_hopper.png"));
		organismImages.put("bird", loadImage("flame_bird.png"));
		organismImages.put("ram", loadImage("fluffy_ram.png"));
		
		buttons.add(new GButton(this, 40,  500, 80, 30, TYPE_NAMES[0]));
		buttons.add(new GButton(this, 190, 500, 80, 30, TYPE_NAMES[1]));
		buttons.add(new GButton(this, 340, 500, 80, 30, TYPE_NAMES[2]));
		buttons.add(new GButton(this, 490, 500, 80, 30, TYPE_NAMES[3]));
		buttons.add(new GButton(this, 640, 500, 80, 30, TYPE_NAMES[4]));
		for (GButton b : buttons)
			b.fireAllEvents(true);
		
		thisPlayer = new Player(30, 30, playerImage, thisPlayer.getName());
		obstacles.add(new Sprite(150, 150, 300, 50, obstacleImage));
		thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
		
		
		// on program exit
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				thisPlayerRef.removeValueAsync();
				if (players.size() == 0) {
					room.removeValueAsync();
					openRoom.removeValueAsync();
				} else if (!gameStarted) // once the game starts, open room no longer exists
					openRoom.child("playerCount").setValueAsync(players.size());
			}
		});
	}
	
	/**
	 * Updates all players and organisms. Called every frame.
	 */
	public void update() {
		if (gameStarted && millis() > gameStartTime + GAME_LENGTH) {
			gameFinished = true;
			double maxScore = thisPlayer.getScore();
			for (Player p : players.values()) {
				if (p.getScore() > maxScore)
					maxScore = p.getScore();
			}
			if (thisPlayer.getScore() >= maxScore)
				winners += thisPlayer.getName() + "\n";
			for (Player p : players.values()) {
				if (p.getScore() >= maxScore)
					winners += p.getName() + "\n";
			}
		}
		
		boolean hasTicked = millis() > lastTick + TICK_RATE;
		
		if (hasTicked) {
			// calculate current player score
			int newScore = 0;
			for (Organism o : organisms.values()) {
				if (o instanceof Animal)
					newScore++;
			}
			thisPlayer.setScore(newScore);
		}
		
		thisPlayer.update(this);
		if (thisPlayer.hasChanged() || hasTicked)
			thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
		
		if (gameStarted) {
			if (hasTicked) {
				ArrayList<Organism> orgs = new ArrayList<Organism>(organisms.values());
				for (int i = 0; i < orgs.size(); i++)
					orgs.get(i).act(this);
				
				orgs = new ArrayList<Organism>(organisms.values());
				for (int i = 0; i < orgs.size(); i++)
					orgs.get(i).getRef().setValueAsync(orgs.get(i).getDataObject()); // update organisms
			}
			
			ArrayList<Organism> other = new ArrayList<Organism>(allOrganisms.values());
			for (int i = 0; i < other.size(); i++) {
				other.get(i).update(this);
			}
		}
		if (hasTicked)
			lastTick = this.millis();
	}

	@Override
	public void draw() {
		if (!gameFinished)
			update();
		
		background(150, 150, 150);
		
		pushMatrix();
		
		// Center the game field on the player
		translate(WIDTH/2, HEIGHT/2);
		translate(-(float)thisPlayer.getX(), -(float)thisPlayer.getY());
		
		// EVERYTHING THAT IS ON THE GAME FIELD (CAN LEAVE PLAYER FIELD OF VIEW) DRAW BELOW HERE
		
		fill(150, 100, 0);
		rect(0, 0, gameAreaWidth, gameAreaHeight); // map background
		
		for (Sprite s : obstacles)
			s.draw(this);
		
		for (Organism o : allOrganisms.values()) {
			o.draw(this);
		}
		
		for (Player p : players.values())
			p.draw(this);
		thisPlayer.draw(this);
		
		popMatrix();
		
		// ALL UI ELEMENTS DRAW BELOW HERE
		int index = -1;
		for (int i = 0; i < TYPES.length; i++) {
			if (TYPES[i] == animalDrawn)
				index = i;
		}
		String text;
		if (index < 0)
			text = "Organism selected: None\n";
		else
			text = "Organism selected: " + TYPE_NAMES[index];
		text += "\nDNA: " + thisPlayer.getBalance() + "\nScore: " + thisPlayer.getScore();
		
		String otherInfo = "Other Players' Scores:\n";
		for (Player p : players.values()) {
			otherInfo += p.getScore() + "\n";
		}
		pushStyle();
		fill(0);
		text(text, 10, 20);
		text(otherInfo, 10, 80);
		if (!gameStarted) {
			fill(100, 100);
			rect(0, 0, WIDTH, HEIGHT);
			textSize(30);
			fill(0);
			text("Waiting for players...", 300, 200);
		}
		if (gameFinished) {
			fill(100, 100);
			rect(0, 0, WIDTH, HEIGHT);
			textSize(30);
			fill(0);
			text("Winner(s):\n" + winners, 300, 100);
		}
		popStyle();
	}
	
	/**
	 * Get the x, y position from a point on the screen to the corresponding point in the game field
	 * @param x X position on the screen
	 * @param y Y position on the screen
	 * @return A Point2D.Double containing the game field coordinates
	 */
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
	
	/**
	 * @param code The key code to check
	 * @return true if the key is held, false otherwise
	 */
	public boolean isKeyHeld(Integer code) {
		return keysHeld.contains(code);
	}
	
	/**
	 * @return -1 if input is to the left, 1 if input is to the right, 0 if no X-axis input
	 */
	public int getXAxisInput() {
		if (isKeyHeld(RIGHT))
			return 1;
		if (isKeyHeld(LEFT))
			return -1;
		return 0;
	}
	
	/**
	 * @return -1 if input is down, 1 if input is up, 0 if no Y-axis input
	 */
	public int getYAxisInput() {
		if (isKeyHeld(UP))
			return 1;
		if (isKeyHeld(DOWN))
			return -1;
		return 0;
	}
	
	@Override
	public void mousePressed() {
		if (!gameStarted)
			return;
		if (animalDrawn != null) {
			Point2D.Double newLocation = windowToGameField(mouseX, mouseY);
			
			Organism o = Organism.createOrganismFromType(animalDrawn, newLocation.x, newLocation.y, this);
			if (o != null && thisPlayer.getBalance() >= o.getCost()) {
				thisPlayer.changeBalance(-o.getCost());
				add(o);
				makeAnimalPlaceSound();
			}
			
			animalDrawn = null;
		}
	}

	/**
	 * Deals with event on G4P buttons. Called internally.
	 * @param button The G4P button being used
	 * @param event The event of the button
	 */
	public void handleButtonEvents(GButton button, GEvent event) {
		if (!gameStarted)
			return;
		if (event == GEvent.CLICKED) {
			for (int i = 0; i < buttons.size(); i++) {
				if (button == buttons.get(i))
					animalDrawn = TYPES[i];
			}
		}
	}
	
	/**
	 * Removes an organism from the game
	 * @param o Organism to remove
	 * @return Whether the removal was successful (true if the organism was present, false otherwise)
	 */
	public boolean remove(Organism o) {
		boolean success = allOrganisms.remove(o.getRef().getKey()) != null;
		organisms.remove(o.getRef().getKey()); // is this one of my organisms?
		o.getRef().removeValueAsync();
		return success;
	}
	
	/**
	 * Adds an organism to the game
	 * @param o Organism to add
	 */
	public void add(Organism o) {
		String type = null;
		if (o instanceof YellowberryTree)
			type = "tree";
		else if (o instanceof GlowingMoss)
			type = "moss";
		else if (o instanceof MouseHopper)
			type = "mouse";
		else if (o instanceof FlameBird)
			type = "bird";
		else if (o instanceof FluffyRam)
			type = "ram";
		DatabaseReference ref = room.child("organisms").child(type).push();
		ref.setValueAsync(o.getDataObject());
		o.setDataRef(ref);
		organisms.put(ref.getKey(), o);
		allOrganisms.put(ref.getKey(), o);
	}
	
	/**
	 * @return The list of all organisms in the game
	 */
	public Collection<Organism> getOrganismList() {
		return allOrganisms.values();
	}
	
	public void changeDNA(int changeInDNA) {
		thisPlayer.changeBalance(changeInDNA);
	}
	
	/**
	 * @param a First Sprite
	 * @param b Second Sprite
	 * @return Distance between the Sprites
	 */
	public static double distance(Sprite a, Sprite b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void makeEatSound() {
		s.makeEatSound();
	}

	public void makeAnimalPlaceSound() {
		s.makeReproduceSound();
	}

	public boolean hitObstacle(Sprite s) {
		for (Sprite o : obstacles) {
			if (o.spriteCollide(s))
				return true;
		}
		return false;
	}
	
	/**
	 * Listens to changes in the players of a room
	 * @author Nir Reiter
	 */
	public class PlayerListener implements ChildEventListener {
		
		private ConcurrentLinkedQueue<Runnable> tasks;
		
		/**
		 * Create a new PlayerListener
		 */
		public PlayerListener() {
			tasks = new ConcurrentLinkedQueue<Runnable>();
			
			DrawingSurface.this.registerMethod("post", this);
		}
		
		/**
		 * Called internally to handle different threads
		 */
		public void post() {
			while (!tasks.isEmpty()) {
				Runnable r = tasks.remove();
				r.run();
			}
		}

		@Override
		public void onCancelled(DatabaseError arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildAdded(DataSnapshot snap, String arg1) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					if (!snap.getRef().equals(thisPlayerRef)) {
						Player p = new Player(0, 0, playerImage, "");
						p.matchPost(snap.getValue(PlayerPost.class));
						players.put(snap.getRef().getKey(), p);
					}
					
					if (players.size() + 1 >= playerMax) {
						gameStarted = true;
						gameStartTime = millis();
					}
				}
			});
		}

		@Override
		public void onChildChanged(DataSnapshot snap, String arg1) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					if (snap.getRef().equals(thisPlayerRef))
						return;
					
					Player p = players.get(snap.getRef().getKey());
					if (p != null)
						p.matchPost(snap.getValue(PlayerPost.class));
				}
			});
		}

		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildRemoved(DataSnapshot snap) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					players.remove(snap.getRef().getKey());
				}
			});
		}
		
	}
	
	/**
	 * Listens to changes in the organisms of the room
	 * @author Nir Reiter
	 *
	 */
	public class OrganismListener implements ChildEventListener {
		
		private ConcurrentLinkedQueue<Runnable> tasks;
		private String type;
		
		/**
		 * Creates a new OrganismListener
		 */
		public OrganismListener(String type) {
			tasks = new ConcurrentLinkedQueue<Runnable>();
			this.type = type;
			DrawingSurface.this.registerMethod("post", this);
		}
		
		/**
		 * Called internally to handle different threads
		 */
		public void post() {
			while (!tasks.isEmpty()) {
				Runnable r = tasks.remove();
				r.run();
			}
		}

		@Override
		public void onCancelled(DatabaseError arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildAdded(DataSnapshot snap, String arg1) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					if (organisms.containsKey(snap.getKey()))
						return;
					
					OrganismPost post = getPost(snap);
					Organism o = Organism.createOrganismFromPost(type, post, DrawingSurface.this);
					o.setDataRef(snap.getRef());
					allOrganisms.put(snap.getKey(), o);
				}
			});
			
		}

		@Override
		public void onChildChanged(DataSnapshot snap, String arg1) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					if (organisms.containsKey(snap.getKey()))
						return;
					
					OrganismPost post = getPost(snap);
					allOrganisms.get(snap.getKey()).matchPost(post);
				}
			});
		}

		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildRemoved(DataSnapshot snap) {
			tasks.add(new Runnable() {
				@Override
				public void run() {
					organisms.remove(snap.getKey());
					allOrganisms.remove(snap.getKey());
				}
			});
		}
		
		public OrganismPost getPost(DataSnapshot snap) {
			if (type == "tree")
				return snap.getValue(TreePost.class);
			else if (type == "mouse" || type == "bird" || type == "ram")
				return snap.getValue(AnimalPost.class);
			else
				return snap.getValue(OrganismPost.class);
		}
		
	}

	@Override
	public void musicStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void musicStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playlistEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void songEnded() {
		// TODO Auto-generated method stub
		
	}
}
