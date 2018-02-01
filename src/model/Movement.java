package model;

public class Movement {
	
	protected int movX;
	protected int movY;
	
	public Movement(int movX, int movY) {
		this.movX = movX;
		this.movY = movY;
	}

	public int getMovX() {
		return movX;
	}

	public void setMovX(int movX) {
		this.movX = movX;
	}

	public int getMovY() {
		return movY;
	}

	public void setMovY(int movY) {
		this.movY = movY;
	}
}
