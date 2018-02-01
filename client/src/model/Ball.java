package model;

public class Ball {

	private int posX, posY;
	private MovementVector v;
	private int speed;
	private int radius;
	
	public Ball(int posX, int posY, MovementVector v, int speed, int radius){
		this.posX = posX;
		this.posY = posY;
		this.v = v;
		this.speed = speed;
		this.radius = radius;
	}
	
	public void move(){
		posX = (posX + v.getDx()) * speed;
		posY = (posY + v.getDy()) * speed;
	}
	
	public void updateMovementVector(int dx, int dy){
		v.setMovementVector(dx, dy);
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public int getRadius(){
		return radius;
	}
	
	
	
}
