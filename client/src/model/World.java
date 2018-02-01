package model;

import java.util.ArrayList;

public class World {

	public static final int WIDTH = 200;
	public static final int HEIGHT = 100;
	
	private ArrayList<Brick> bricksList = new ArrayList<Brick>();
	private ArrayList<Ball> ballsList = new ArrayList<Ball>();
	private ArrayList<Player> playersList = new ArrayList<Player>();
	
	public World(ArrayList<Brick> bricksList, ArrayList<Ball> ballsList){
		this.bricksList = bricksList;
		this.ballsList = ballsList;
		this.playersList = new ArrayList<Player>();
	}
	
	public void startGame(){
		
	}
	
	public void addPlayer(Player player){
		playersList.add(player);
	}
}
