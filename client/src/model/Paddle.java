package model;

public class Paddle{
	
	private int width, x, y;

	public Paddle(int width, int x, int y) {
		this.width = width;
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return width;
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
