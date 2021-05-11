package networking;

import java.util.ArrayList;

public class PlayerPost {
	
	private double balance;
	private double x, y;
	private ArrayList<OrganismPost> organisms;
	
	public PlayerPost() {
		
	}

	public PlayerPost(double balance, double x, double y, ArrayList<OrganismPost> organisms) {
		this.balance = balance;
		this.x = x;
		this.y = y;
		this.organisms = organisms;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public ArrayList<OrganismPost> getOrganisms() {
		return organisms;
	}
	
	public String toString() {
		return "Balance: " + balance + ", Location: (" + x + ", " + y + ")";
	}
}
