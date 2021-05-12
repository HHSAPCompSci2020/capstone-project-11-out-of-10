package networking;

import java.util.ArrayList;

public class PlayerPost {
	
	private double balance;
	private double x, y;
	
	public PlayerPost() {
		
	}

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
