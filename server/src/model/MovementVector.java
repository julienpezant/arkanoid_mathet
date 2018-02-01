package model;

public class MovementVector {

	private int dx, dy;
	
	public MovementVector(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public int getDx(){
		return this.dx;
	}
	
	public int getDy(){
		return this.dy;
	}
	
	public void setMovementVector(int dx, int dy){
		this.dx = dx;
		this.dy = dy;
	}
}
