package model;

import java.util.ArrayList;

public class Ball {

	private double posX, posY;
	private MovementVector v;
	private double speed;
	private int radius;
	private double alpha;
	private ArrayList<String> scorers = new ArrayList<String>();
	
	public Ball(){
		this(World.WIDTH/2, World.HEIGHT/2, new MovementVector(0,-1), 1, 26);
	}
	
	public Ball(int posX, int posY, MovementVector v, double speed, int radius){
		this.posX = posX;
		this.posY = posY;
		this.v = v;
		this.speed = speed;
		this.radius = radius;
		this.setAlpha(0);
	}
	
	public void move(){
		posX = (posX + v.getDx());
		posY = (posY + v.getDy());
	}
	
	public void updateMovementVector(double dx, double dy){
		v.setMovementVector(dx, dy);
	}
	
	public void replace(int nx, int ny) {
		this.posX = nx;
		this.posY = ny;
	}
	
	public double getMovX() {
		return v.getDx();
	}
	
	public double getMovY() {
		return v.getDy();
	}
	
	public double getPosX(){
		return posX;
	}
	
	public double getPosY(){
		return posY;
	}
	
	public long getXPixel() {
		return Math.round(posX);
	}
	
	public long getYPixel() {
		return Math.round(posY);
	}
	
	public int getRadius(){
		return radius;
	}
	
	public double getLimitLeft() {
		return posX;
	}
	
	public double getLimitTop() {
		return posY;
	}
	
	public double getLimitBottom() {
		return posY + radius;
	}
	
	public double getLimitRight() {
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
	
	public double getSpeed() {
		return this.speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	
	
}
