package model;

public class Paddle {
	
	public static final int HEIGHT = 31;
	public static final int WIDTH = 172;
	
	private static final int POS_Y = 620;
	private int posX, posY;
	
	private MovementVector v;
	
	public Paddle(){
		this.posX = World.WIDTH/2;
		this.posY = POS_Y;
		this.v = new MovementVector(0,0);
	}
	
	public void move(int x){
		this.posX = x;
	}
	
	public void updateMovementVector(int dx){
		v.setMovementVector(dx, 0);
	}
	
	public double getMovX() {
		return v.getDx();
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getCenter() {
		return posX + (int) Math.ceil(WIDTH / 2);
	}
	
	public int getLimitRight() {
		return posX + WIDTH;
	}
}
