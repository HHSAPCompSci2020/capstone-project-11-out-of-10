package game;
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
import organisms.Organism;
import organisms.*;
import processing.core.PApplet;
import processing.core.PImage;
import sprite.Player;
import sprite.Sprite;

/**
 * Draws and handles game logic
 * @author Timothy Li
 *
 */
public class DrawingSurface extends PApplet {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	public static final int TICK_RATE = 5000; // milliseconds between ticks
	
	public ArrayList<Integer> keysHeld;
	ArrayList<GButton> buttons;
	public String animalDrawn;
	public int lastOrganismTick;
	
	public int gameAreaWidth;
	public int gameAreaHeight;
	public ArrayList<Sprite> obstacles;
	public HashMap<String, Organism> organisms;
	public HashMap<String, Organism> otherOrganisms;
	public int totalBerries;
	public HashMap<String, Player> players;
	public Player thisPlayer;
	
	public static PImage playerImage;
	public static PImage obstacleImage;
	public static HashMap<String, PImage> organismImages;

	public DatabaseReference room;
	public DatabaseReference thisPlayerRef;
	public DatabaseReference openRoom;
	public int playerMax;
	
	public boolean gameStarted;
	
	/**
	 * Create a new DrawingSurface which uses a room in the database
	 * @param room The DatabaseReference for the room the player is in
	 */
	public DrawingSurface(DatabaseReference openRoom, DatabaseReference room, int playerMax) {
		keysHeld = new ArrayList<Integer>();
		animalDrawn = null;
		
		gameAreaWidth = 5000;
		gameAreaHeight = 3000;
		
		obstacles = new ArrayList<Sprite>();
		buttons = new ArrayList<GButton>();
		players = new HashMap<String, Player>();
		organisms = new HashMap<String, Organism>();
		otherOrganisms = new HashMap<>();
		organismImages = new HashMap<>();
		
		this.openRoom = openRoom;
		this.room = room;
		this.playerMax = playerMax;
		thisPlayerRef = room.child("players").push();
		
		room.child("players").addChildEventListener(new PlayerListener());
		room.child("organisms").addChildEventListener(new OrganismListener());
		
		gameStarted = false;
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
		
		buttons.add(new GButton(this, 40,  500, 80, 30, "Yellowberry Tree"));
		buttons.add(new GButton(this, 190, 500, 80, 30, "Glowing Moss"));
		buttons.add(new GButton(this, 340, 500, 80, 30, "Mouse Hopper"));
		buttons.add(new GButton(this, 490, 500, 80, 30, "Flame Bird"));
		buttons.add(new GButton(this, 640, 500, 80, 30, "Fluffy Ram"));
		for (GButton b : buttons)
			b.fireAllEvents(true);
		
		thisPlayer = new Player(30, 30, playerImage);
		obstacles.add(new Sprite(150, 150, 300, 50, obstacleImage));
		thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
		
		
		// on program exit
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println(players);
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
		boolean hasTicked = this.millis() > this.lastOrganismTick + TICK_RATE;
		
		thisPlayer.update(this);
		if (thisPlayer.hasChanged() || hasTicked)
			thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
		
		if (hasTicked) {
			for (Organism o : organisms.values())
				o.act(this);
		}
		
		for (Organism o : organisms.values())
			o.update(this);
		for (Organism o : otherOrganisms.values())
			o.update(this);
		
		if (hasTicked)
			lastOrganismTick = this.millis();
	}

	@Override
	public void draw() {
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
		
		for(Organism o : organisms.values())
			o.draw(this);
		
		for (Player p : players.values())
			p.draw(this);
		thisPlayer.draw(this);
		
		popMatrix();
		
		// ALL UI ELEMENTS DRAW BELOW HERE
		
		String text = "Animal selected: " + animalDrawn + "     coins: " + thisPlayer.getBalance();
		fill(0);
		text(text, 10, 20);
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
		if (animalDrawn != null) {
			Point2D.Double newLocation = windowToGameField(mouseX, mouseY);
			
			Organism o = Organism.createOrganismFromType(animalDrawn, newLocation.x, newLocation.y, this);
			if (o != null && thisPlayer.getBalance() >= o.getCost()) {
				thisPlayer.changeBalance(-o.getCost());
				add(o);
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
		if (event == GEvent.CLICKED) {
			if (button == buttons.get(0))
				animalDrawn = "tree";
			else if (button == buttons.get(1))
				animalDrawn = "moss";
			else if (button == buttons.get(2))
				animalDrawn = "mouse";
			else if (button == buttons.get(3))
				animalDrawn = "bird";
			else if (button == buttons.get(4))
				animalDrawn = "ram";
		}
	}
	
	/**
	 * Removes an organism from the game
	 * @param o Organism to remove
	 * @return Whether the removal was successful (true if the organism was present, false otherwise)
	 */
	public boolean remove(Organism o) {
		if (organisms.remove(o.getRef().getKey()) != null) { // is this one of my organisms?
			o.getRef().removeValueAsync();
			return true;
		}
		return false;
	}
	
	/**
	 * Adds an organism to the game
	 * @param o Organism to add
	 */
	public void add(Organism o) {
		DatabaseReference ref = room.child("organisms").push();
		ref.setValueAsync(o.getDataObject());
		o.setDataRef(ref);
		organisms.put(ref.getKey(), o);
	}
	
	/**
	 * @return The list of all organisms in the game
	 */
	public Collection<Organism> getList() {
		return organisms.values();
	}
	
	public int getTotalBerries() {
		return totalBerries;
	}
	
	public void setTotalBerries(int newTotal) {
		totalBerries = newTotal;
	}
	
	public void changeDNA(int changeInDNA) {
		thisPlayer.changeBalance(changeInDNA);
	}
	
	/**
	 * @param a First Sprite
	 * @param b Second Sprite
	 * @return Distance between the Sprites
	 */
	public double distance(Sprite a, Sprite b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return Math.sqrt(dx*dx + dy*dy);
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
					if (snap.getRef().equals(thisPlayerRef))
						return;
					
					Player p = new Player(0, 0, playerImage);
					p.matchPost(snap.getValue(PlayerPost.class));
					players.put(snap.getRef().getKey(), p);
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
		
		/**
		 * Creates a new OrganismListener
		 */
		public OrganismListener() {
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
			OrganismPost post = getPost(snap);
			Organism o = Organism.createOrganismFromPost(post);
			
			o.setDataRef(snap.getRef());
			otherOrganisms.put(snap.getKey(), o);
		}

		@Override
		public void onChildChanged(DataSnapshot snap, String arg1) {
			OrganismPost post = getPost(snap);
			otherOrganisms.get(snap.getKey()).matchPost(post);
			
		}

		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildRemoved(DataSnapshot snap) {
			otherOrganisms.remove(snap.getKey());
			
		}
		
		public OrganismPost getPost(DataSnapshot snap) {
			String type = snap.getRef().getParent().getKey();
			if (type == "tree")
				return snap.getValue(TreePost.class);
			else if (type == "mouse" || type == "bird" || type == "ram")
				return snap.getValue(AnimalPost.class);
			else
				return snap.getValue(OrganismPost.class);
		}
		
	}
}
