package game;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

	public DatabaseReference room;
	public DatabaseReference thisPlayerRef;
	
	public DrawingSurface(DatabaseReference room) {
		keysHeld = new ArrayList<Integer>();
		
		gameAreaWidth = 5000;
		gameAreaHeight = 3000;
		
		obstacles = new ArrayList<Sprite>();
		buttons = new ArrayList<GButton>();
		players = new HashMap<String, Player>();
		organisms = new ArrayList<Organism>();
		
		this.room = room;
		thisPlayerRef = room.child("players").push();
		
		room.child("players").addChildEventListener(new PlayerListener());
		room.child("organisms").addChildEventListener(new OrganismListener());
	}
	
	@Override
	public void setup() {
		playerImage = loadImage("player.png");
		obstacleImage = loadImage("obstacle.png");
		
		buttons.add(new GButton(this, 40,  500, 80, 30, "Button 1"));
		buttons.add(new GButton(this, 190, 500, 80, 30, "Button 2"));
		buttons.add(new GButton(this, 340, 500, 80, 30, "Button 3"));
		buttons.add(new GButton(this, 490, 500, 80, 30, "Button 4"));
		buttons.add(new GButton(this, 640, 500, 80, 30, "Button 5"));
		for (GButton b : buttons)
			b.fireAllEvents(true);

		animalDrawn = 0;
		
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
		
		for(Organism o : organisms) {
			o.draw(this);
		}
		
		String text = "animal1: " + "number    " + "animal2: " + "number    " + "animal3: " + "number    " + "animal4: "+ "number    " + "animal5: " + "number    " + "Animal selected: " + animalDrawn + "     coins: " + totalDNA;
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
			/*Point2D.Double newLocation = windowToGameField(mouseX, mouseY);
			obstacles.add(new Sprite(newLocation.x-5, newLocation.y-5, 10, 10, loadImage("obstacle.png")));*/
			if(animalDrawn == 1)
				add(new FlameBird(mouseX,mouseY,10,20,loadImage("FlameBird.png"),this));
			else if(animalDrawn == 2)
				add(new FluffyRam(mouseX,mouseY,10,20,loadImage("FluffyRam.png"),this));
			else if(animalDrawn == 3)
				add(new GlowingMoss(mouseX,mouseY,10,20,loadImage("GlowingMoss.png"),this));
			else if(animalDrawn == 4)
				add(new MouseHopper(mouseX,mouseY,10,20,loadImage("MouseHopper.png"),this));
			else if(animalDrawn == 5)
				add(new YellowberryTree(mouseX,mouseY,10,20,loadImage("YellowberryTree.png"),this));
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
	
	
	
	public class PlayerListener implements ChildEventListener {
		
		private ConcurrentLinkedQueue<Runnable> tasks;
		
		public PlayerListener() {
			tasks = new ConcurrentLinkedQueue<Runnable>();
			
			DrawingSurface.this.registerMethod("post", this);
		}
		
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
	
	public class OrganismListener implements ChildEventListener {
		
		private ConcurrentLinkedQueue<Runnable> tasks;

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
