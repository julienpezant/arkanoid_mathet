package model;

public class Paddle {
	//x, y, largeur / deplacer
	
	protected int posX;
	protected int posY;
	protected int width;
	protected int height;
	
	public Paddle(int posX, int posY, int width) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = 5;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY =posY;
	}
	
	
}
