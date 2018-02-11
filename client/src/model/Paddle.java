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

	public void setWidth(int width) {
		this.width = width;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
}
