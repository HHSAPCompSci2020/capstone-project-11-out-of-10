package game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.ValueEventListener;

import networking.RoomPost;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

/**
 * Represents the menu used to choose a room
 * @author Nir Reiter
 *
 */
public class MenuScreen extends JPanel  {
	
	private JFrame window;
	private JList<String> roomList;
	private DefaultListModel<String> model;
	
	private JButton joinButton;
	private JButton newRoomButton;
	
	private DatabaseReference database;
	private DatabaseReference openRooms;
	private DatabaseReference gameRooms;
	
	private DatabaseReference openRoomToJoin;

	/**
	 * Creates a new menu screen, referencing the database where all the rooms are stored
	 * @param database The reference to the level of the database under which all rooms are stored
	 */
	public MenuScreen(DatabaseReference database) {
		
		this.database = database;
		this.openRooms = database.child("openrooms");
		this.gameRooms = database.child("gamerooms");
		openRooms.addChildEventListener(new RoomChangeListener());
		openRoomToJoin = null;
		
		// SWING GRAPHICS
		
		model = new DefaultListModel<String>();
		
		ActionHandler actionEventHandler = new ActionHandler();
		
		setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		
		roomList = new JList<String>();
		roomList.setModel(model);
		roomList.setBorder(new EtchedBorder(EtchedBorder.RAISED));
		panel.add(roomList, BorderLayout.CENTER);
		
		JLabel title = new JLabel("Open Rooms");
		title.setHorizontalAlignment(JLabel.CENTER);
		panel.add(title, BorderLayout.NORTH);
		add(panel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 5, 15, 15));
		newRoomButton = new JButton("Create a room");
		newRoomButton.addActionListener(actionEventHandler);
		joinButton = new JButton("Join a room");
		joinButton.addActionListener(actionEventHandler);
		buttonPanel.add(newRoomButton);
		buttonPanel.add(joinButton);
		
		panel.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void show() {
		window = new JFrame();
		window.add(this);
		window.setBounds(0, 0, 800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
	
	/**
	 * Joins a room with the given name
	 * @param selection The name of the room to join
	 */
	private void joinRoom(String selection) {

		openRooms.orderByChild("name").equalTo(selection).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error);
			}

			@Override
			public void onDataChange(DataSnapshot snap) {
				
				if (!snap.hasChildren())
					return;
				
				DataSnapshot room = snap.getChildren().iterator().next();
				RoomPost post = room.getValue(RoomPost.class);
				
				if (post.getPlayerCount() >= post.getPlayerMax()) {
					JOptionPane.showMessageDialog(MenuScreen.this, "Too many players in the room!");
					openRoomToJoin = null;
					return;
				}
				
				openRoomToJoin = room.getRef();
				
				if (post.getPlayerCount() + 1 == post.getPlayerMax()) {
					room.getRef().removeValueAsync();
				} else {
					room.child("playerCount").getRef().setValueAsync(post.getPlayerCount() + 1);
				}
				
			}
			
		});
		
		gameRooms.orderByChild("name").equalTo(selection).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onCancelled(DatabaseError error) {
				System.out.println(error);
			}

			@Override
			public void onDataChange(DataSnapshot snap) {
				
				if (!snap.hasChildren() || openRoomToJoin == null)
					return;
				
				DataSnapshot room = snap.getChildren().iterator().next();
				RoomPost post = room.getValue(RoomPost.class);
				
				// Creating the Processing window
				
				MenuScreen.this.window.setVisible(false);
				
				DrawingSurface drawing = new DrawingSurface(openRoomToJoin, room.getRef(), post.getPlayerMax());
				PApplet.runSketch(new String[]{""}, drawing);
				
				PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
				PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
				JFrame window = (JFrame) canvas.getFrame();

				window.setBounds(0, 0, 800, 600);
				window.setMinimumSize(new Dimension(100,100));
				window.setResizable(true);
				window.setTitle("EcoWars");
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				window.setVisible(true);
				
				canvas.requestFocus();
				
				MenuScreen.this.window.dispose();
				
			}
		});
		
	}
	
	public void leaveRoom(DatabaseReference gameRoom) {
		
		gameRoom.child("name").addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onCancelled(DatabaseError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDataChange(DataSnapshot snap) {
				openRooms.orderByChild("name").equalTo(snap.getValue(String.class)).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {

					@Override
					public void onCancelled(DatabaseError arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onDataChange(DataSnapshot snap) {
						if (!snap.hasChildren())
							return;
						
						DataSnapshot room = snap.getChildren().iterator().next();
						RoomPost post = room.getValue(RoomPost.class);
						room.child("playerCount").getRef().setValueAsync(post.getPlayerCount() + 1);
					}
				});
			}
		});
	}
	
	/**
	 * Listens to changes in room information (new rooms added, number of players changes)
	 * @author Nir Reiter
	 *
	 */
	public class RoomChangeListener implements ChildEventListener {
		
		@Override
		public void onCancelled(DatabaseError error) {
			System.out.println(error);
		}
		@Override
		public void onChildAdded(DataSnapshot snap, String arg1) {
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					String name = snap.getValue(RoomPost.class).getName();
					
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							model.add(0, name);
						}
					});
					
				}
			});
			
		}
		@Override
		public void onChildChanged(DataSnapshot snap, String arg1) {
			
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// TODO
				}
			});
			
		}
		@Override
		public void onChildMoved(DataSnapshot arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onChildRemoved(DataSnapshot snap) {
			
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					model.removeElement(snap.getValue(RoomPost.class).getName());
				}
			});
			
		}
		
	}
	
	/**
	 * Listens to Swing actions in the Menu
	 * @author Nir Reiter
	 *
	 */
	private class ActionHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == newRoomButton) {
				
				// get name of room
				String name = JOptionPane.showInputDialog("Please choose a name for your room:");
				if (name == null || name.isEmpty()) {
					JOptionPane.showMessageDialog(MenuScreen.this, "Invalid room name");
					return;
				}
				if (model.contains(name)) {
					JOptionPane.showMessageDialog(MenuScreen.this, "That room name is already in use");
					return;
				}
				
				int playerMax;
				try {
					playerMax = Integer.valueOf(JOptionPane.showInputDialog("Maximum Players?"));
					if (playerMax < 1) {
						JOptionPane.showMessageDialog(MenuScreen.this, "Invalid number: must be greater than 1.");
						return;
					}
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(MenuScreen.this, "Invalid number");
					return;
				}
				
				RoomPost post = new RoomPost(name, playerMax);
				
				openRooms.push().setValueAsync(post);
				gameRooms.push().setValue(post, new CompletionListener() {
					@Override
					public void onComplete(DatabaseError error, DatabaseReference ref) {
						joinRoom(name);
					}
				});
				
			} else if (source == joinButton) {
				String selection = roomList.getSelectedValue();
				if (selection != null)
					joinRoom(selection);
			}
		}
		
	}
}
