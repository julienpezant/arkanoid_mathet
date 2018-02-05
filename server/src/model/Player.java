package model;

import java.awt.Color;

public class Player {

	private String pseudo;
	private Color color;
	private int score;
	private Paddle paddle;
	
	private int precision;
	
	public Player(String pseudo, Color color){
		this.pseudo = pseudo;
		this.color = color;
		this.score = 0;
		this.paddle = new Paddle();
		this.precision = -1;
	}
	
	public void movePaddle(int x){
		paddle.move(x);
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
	
	public int getPrecision() {
		return precision;
	}
	
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void incrementScore() {
		this.score++;
	}
	
}
