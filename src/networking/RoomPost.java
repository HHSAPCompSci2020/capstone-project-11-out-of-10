package networking;

import java.util.ArrayList;

public class RoomPost {
	
	private String name;
	private int playerMax;
	private ArrayList<PlayerPost> players;
	
	public RoomPost() {
		
	}
	
	public RoomPost(String name, int playerMax) {
		this.name = name;
		this.playerMax = playerMax;
		this.players = new ArrayList<PlayerPost>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getPlayerMax() {
		return playerMax;
	}
	
	public ArrayList<PlayerPost> getPlayers() {
		return players;
	}
	
	public String toString() {
		return "Name: " + name + ", Players: " + players.size() + "/" + playerMax;
	}

}
