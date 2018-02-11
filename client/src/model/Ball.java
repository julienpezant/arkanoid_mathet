package model;

public class Ball {

	private int posX, posY, side;
	
	public Ball(int posX, int posY, int side){
		this.posX = posX;
		this.posY = posY;
		this.side = side;
	}
	
	public void replace(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}
	
	public int getSide(){
		return side;
	}
}
