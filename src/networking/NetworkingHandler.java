package networking;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import game.DrawingSurface;
import game.MainScreen;
import organisms.Animal;
import organisms.Organism;
import sprite.Player;

public class NetworkingHandler implements ChildEventListener {

	private ConcurrentLinkedQueue<Runnable> tasks;
	private DrawingSurface drawing;
	private DatabaseReference database;
	private String playerID;
	
	public NetworkingHandler(DrawingSurface drawing) {   // This threading strategy will work with Processing programs. Just use this code inside your PApplet.
		tasks = new ConcurrentLinkedQueue<Runnable>();
		this.drawing = drawing;
		drawing.registerMethod("post", this);
		
		// Database
		FileInputStream refreshToken;
		try {
			
			refreshToken = new FileInputStream("EcoWarsKey.json");
			
			FirebaseOptions options = new FirebaseOptions.Builder()
				    .setCredentials(GoogleCredentials.fromStream(refreshToken))
				    .setDatabaseUrl("https://ecowars-db164-default-rtdb.firebaseio.com")
				    .build();

				FirebaseApp.initializeApp(options);
				DatabaseReference database = FirebaseDatabase.getInstance().getReference();
				this.database = database.child("posts");

				database.addChildEventListener(this);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setup(String playerID) {
		this.playerID = playerID;
		Player p = drawing.main.player1;
		database.child(playerID).setValueAsync(new PlayerPost(p.getBalance(), p.getX(), p.getY(), null));
	}
	
	public void post() {
		while (!tasks.isEmpty()) {
			Runnable r = tasks.remove();
			r.run();
		}
	}
	
	public void postOrganism(Organism o) {
		int type;
		type = 0;
		OrganismPost post = new OrganismPost(type, o.getX(), o.getY(), (o instanceof Animal) ? ((Animal) o).getCurrentFood() : 0);
		database.push().setValueAsync(post);
	}
	
	public void postPlayer(Player p) {
		PlayerPost post = new PlayerPost(p.getBalance(), p.getX(), p.getY(), null);
		database.push().setValueAsync(post);
	}
	
	@Override
	public void onCancelled(DatabaseError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChildAdded(DataSnapshot snap, String arg1) {
		
		tasks.add(new Runnable() {
			@Override
			public void run() {
				
				OrganismPost post = snap.getValue(OrganismPost.class);
				drawing.main.player1.setLocation(post.getX(), post.getY());
				System.out.println(post);
			}
		});
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
