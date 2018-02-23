package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import dnr.utils.modeleecoutable.AbstractModeleEcoutable;

public class World extends AbstractModeleEcoutable{
	private ArrayList<Ball> ballsList;
	private LinkedHashMap<String, Player> playersList;
	
	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	
	public World(){
		ballsList = new ArrayList<Ball>();
		ballsList.add(new Ball(WIDTH/2, HEIGHT/2, 10));
		playersList = new LinkedHashMap<String, Player>();
	}
	
	public ArrayList<Ball> getBallsList() {
		return ballsList;
	}

	public LinkedHashMap<String, Player> getPlayersList() {
		return playersList;
	}

	public void addBall(Ball ball){
		ballsList.add(ball);
		fireChangement();
	}
	
	public void addPlayer(Player player){
		playersList.put(player.getPseudo(), player);
		fireChangement();
	}
	
	public void removePlayer(Player player){
		playersList.remove(player.getPseudo(), player);
		fireChangement();
	}

	public void setLocationBall(int posX, int posY) {
		ballsList.get(0).replace(posX, posY);
		fireChangement();
	}

	public void setLocationPlayerPaddle(String pseudo, int posX) {
		playersList.get(pseudo).getPaddle().setLocation(posX);
		fireChangement();
	}
}
