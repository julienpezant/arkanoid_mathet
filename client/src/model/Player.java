package model;

import java.awt.Color;
import java.util.HashMap;

public class Player {

	private String pseudo;
	private Color color;
	private int score;
	private Paddle paddle;
	private HashMap<String,Paddle> players;
	
	public Player(String pseudo, Color color, Paddle paddle){
		this.pseudo = pseudo;
		this.color = color;
		this.score = 0;
		this.paddle = paddle;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Paddle getPaddle() {
		return paddle;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
