package model;

import client.ClientArkanoid;

public class Player {

	private String pseudo;
	private String color;
	private int score;
	private Paddle paddle;
	
	public Player(String pseudo, String color, int posX){
		this.pseudo = pseudo;
		this.color = color;
		this.score = 0;
		this.paddle = new Paddle(40, posX, ClientArkanoid.HEIGHT - 100);
	}

	public String getPseudo() {
		return pseudo;
	}
	
	public String getColor() {
		return color;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public int getScore() {
		return score;
	}
	
}
