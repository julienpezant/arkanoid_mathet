package model;

import java.util.ArrayList;

public class Ball {

	private int posX, posY;
	private MovementVector v;
	private int speed;
	private int radius;
	private ArrayList<String> scorers = new ArrayList();
	
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
	
	public int getMovX() {
		return v.getDx();
	}
	
	public int getMovY() {
		return v.getDy();
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
	
	public int getLimitLeft() {
		return posX - radius;
	}
	
	public int getLimitTop() {
		return posY - radius;
	}
	
	public int getLimitBottom() {
		return posY + radius;
	}
	
	public int getLimitRight() {
		return posX + radius;
	}

	public ArrayList<String> getScorers() {
		return scorers;
	}

	public void setScorers(ArrayList<String> scorers) {
		this.scorers = scorers;
	}

	public void addScorer(String scorer) {
		this.scorers.add(scorer);
	}
	
	public void removeScorers() {
		this.scorers = new ArrayList<String>();
	}
	
	
	
}
