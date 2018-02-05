package model;

public class Paddle {
	
	public static final int HEIGHT = 2;
	public static final int WIDTH = 10;
	
	private static final int POS_Y = 95;
	private int posX, posY;
	
	public Paddle(){
		this.posX = World.WIDTH/2;
		this.posY = POS_Y;
	}
	
	public void move(int x){
		this.posX = x;
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
