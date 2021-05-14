package networking;

import java.util.HashMap;

public class RoomPost {
	
	private String name;
	private int playerMax;
	private HashMap<String, PlayerPost> players;
	
	public RoomPost() {
		
	}
	
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
