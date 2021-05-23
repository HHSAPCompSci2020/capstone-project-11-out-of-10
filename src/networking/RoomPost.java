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
	
	/**
	 * gets name of room
	 * @return name of room
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * gets maximum player count in room
	 * @return maximum player count in room
	 */
	public int getPlayerMax() {
		return playerMax;
	}
	
	/**
	 * gets current number of players in room
	 * @return current player count
	 */
	public int getPlayerCount() {
		return playerCount;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ", Players: " + playerCount + "/" + playerMax;
	}

}
