package model;

import java.awt.Dimension;
import java.util.ArrayList;

public class World {
	
	public World(Dimension dimension, ArrayList<Ball> balls) {
		this.dimension = dimension;
		this.balls = balls;
	}

	protected Dimension dimension;
	protected ArrayList<Ball> balls;
	protected ArrayList<Brick> bricks;
	protected ArrayList<Player> players;

	public void start() {
		
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public ArrayList<Ball> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Ball> balls) {
		this.balls = balls;
	}

	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	public void setBricks(ArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	

}
