package model;

public class Ball {
	//x, y, vecteur (movement), vitesse, rayon / deplacer
	
	protected int posX;
	protected int posY;
	protected Movement movement;
	protected int radius;
	
	public Ball(int posX, int posY, Movement movement, int radius) {
		this.posX = posX;
		this.posY = posY;
		this.movement = movement;
		this.radius = radius;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Movement getMovement() {
		return movement;
	}

	public void setMovement(Movement movement) {
		this.movement = movement;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public void move() {
		this.posX += this.movement.getMovX();
		this.posY += this.movement.getMovY();
	}
	
	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	
	
	
}
