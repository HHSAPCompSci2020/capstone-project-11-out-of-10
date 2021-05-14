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
	private int playerCount;
	
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
		this.playerCount = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public int getPlayerMax() {
		return playerMax;
	}
	
	public int getPlayerCount() {
		return playerCount;
	}
	
	public String toString() {
		return "Name: " + name + ", Players: " + playerCount + "/" + playerMax;
	}

}
