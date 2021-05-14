package networking;

import java.util.HashMap;

/**
 * Represents room information in the database
 * @author Nir Reiter
 *
 */
public class RoomPost {
	
	private String name;
	private int playerMax;
	private HashMap<String, PlayerPost> players;
	
	/**
	 * Creates a new room representation with default values of zero or null
	 */
	public RoomPost() {
		
	}
	
	/**
	 * Creates a new room representation with given information and no players 
	 * @param name Name for the room to have
	 * @param playerMax Maximum number of players allowed in the room
	 */
	public RoomPost(String name, int playerMax) {
		this.name = name;
		this.playerMax = playerMax;
		this.players = new HashMap<String, PlayerPost>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getPlayerMax() {
		return playerMax;
	}
	
	public HashMap<String, PlayerPost> getPlayers() {
		return players;
	}
	
	public String toString() {
		return "Name: " + name + ", Players: " + players.size() + "/" + playerMax;
	}

}
