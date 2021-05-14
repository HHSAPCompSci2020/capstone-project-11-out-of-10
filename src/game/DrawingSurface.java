package game;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import g4p_controls.GAlign;
import g4p_controls.GButton;
import g4p_controls.GEvent;
import g4p_controls.GLabel;
import networking.PlayerPost;
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
	
	public ArrayList<Integer> keysHeld;
	ArrayList<GButton> buttons;
	private int animalDrawn;
	
	public int gameAreaWidth;
	public int gameAreaHeight;
	public ArrayList<Sprite> obstacles;
	private ArrayList<Organism> organisms;
	private int totalBerries;
	private int totalDNA;
	public HashMap<String, Player> players;
	public Player thisPlayer;
	
	public static PImage playerImage;
	public static PImage obstacleImage;
	public static ArrayList<PImage> organismImages;

	public DatabaseReference room;
	public DatabaseReference thisPlayerRef;
	
	/**
	 * Create a new DrawingSurface which uses a room in the database
	 * @param room The DatabaseReference for the room the player is in
	 */
	public DrawingSurface(DatabaseReference room) {
		keysHeld = new ArrayList<Integer>();
		animalDrawn = -1;
		
		gameAreaWidth = 5000;
		gameAreaHeight = 3000;
		
		obstacles = new ArrayList<Sprite>();
		buttons = new ArrayList<GButton>();
		players = new HashMap<String, Player>();
		organisms = new ArrayList<Organism>();
		
		organismImages = new ArrayList<PImage>();
		
		this.room = room;
		thisPlayerRef = room.child("players").push();
		
		room.child("players").addChildEventListener(new PlayerListener());
		room.child("organisms").addChildEventListener(new OrganismListener());
	}
	
	@Override
	public void setup() {
		playerImage = loadImage("player.png");
		obstacleImage = loadImage("obstacle.png");
		organismImages.add(loadImage("yellowberry_tree.png"));
		organismImages.add(loadImage("glowing_moss.png"));
		organismImages.add(loadImage("mouse_hopper.png"));
		organismImages.add(loadImage("flame_bird.png"));
		organismImages.add(loadImage("fluffy_ram.png"));
		
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
				thisPlayerRef.removeValueAsync();
				if (players.size() == 0)
					room.removeValueAsync();		
			}
		});
	}
	
	/**
	 * Updates all players and organisms. Called every frame.
	 */
	public void update() {
		thisPlayer.update(this);
		if (thisPlayer.hasChanged())
			thisPlayerRef.setValueAsync(thisPlayer.getDataObject());
		
		for (Organism o : organisms)
			o.act(this);
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
		
		for(Organism o : organisms)
			o.draw(this);
		
		for (Player p : players.values())
			p.draw(this);
		thisPlayer.draw(this);
		
		popMatrix();
		
		// ALL UI ELEMENTS DRAW BELOW HERE
		
		String text = "Animal selected: " + animalDrawn + "     coins: " + totalDNA;
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
	
	public void mousePressed() {
		if (animalDrawn >= 0 && animalDrawn <= 4) {
			Point2D.Double newLocation = windowToGameField(mouseX, mouseY);
			PImage imageToUse = organismImages.get(animalDrawn);
			organisms.add(Organism.createOrganismFromCode(animalDrawn, newLocation.x, newLocation.y));
			animalDrawn = -1;
		}
	}

	/**
	 * Deals with event on G4P buttons. Called internally.
	 * @param button The G4P button being used
	 * @param event The event of the button
	 */
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
	
	/**
	 * Removes an organism from the game
	 * @param o Organism to remove
	 * @return Whether the removal was successful (true if the organism was present, false otherwise)
	 */
	public boolean remove(Organism o) {
		return organisms.remove(o);
	}
	
	/**
	 * Adds an organism to the game
	 * @param o Organism to add
	 */
	public void add(Organism o) {
		organisms.add(o);
	}
	
	/**
	 * @return The list of all organisms in the game
	 */
	public ArrayList<Organism> getList() {
		return organisms;
	}
	
	public int getTotalBerries() {
		return totalBerries;
	}
	
	public void setTotalBerries(int newTotal) {
		totalBerries = newTotal;
	}
	
	public void changeDNA(int changeInDNA) {
		totalDNA += changeInDNA;
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
	 * Listens to changes in the organisms in of the room
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
		public void onChildAdded(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildChanged(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onChildRemoved(DataSnapshot arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
