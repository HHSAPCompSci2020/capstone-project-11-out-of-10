package networking;

import java.util.ArrayList;

/**
 * How Player information is stored in the database
 * @author Nir Reiter
 *
 */
public class PlayerPost {
	
	private double balance;
	private double x, y;
	
	/**
	 * Creates a new post with default values of zero
	 */
	public PlayerPost() {
		
	}

	/**
	 * Creates a new post with given information
	 * @param balance Balance of the Player
	 * @param x X position of the Player
	 * @param y Y position of the Player
	 */
	public PlayerPost(double balance, double x, double y) {
		this.balance = balance;
		this.x = x;
		this.y = y;
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
	
	public String toString() {
		return "Balance: " + balance + ", Location: (" + x + ", " + y + ")";
	}
}
