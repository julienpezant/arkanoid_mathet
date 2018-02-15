package model;

public class MovementVector {

	private double dx, dy;
	
	public MovementVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getDx(){
		return this.dx;
	}
	
	public double getDy(){
		return this.dy;
	}
	
	public void setMovementVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
}
