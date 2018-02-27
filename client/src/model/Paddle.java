package model;

public class Paddle{
	
	private int x, y;

	public Paddle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setLocation(int x){
		this.x = x;
	}
}
