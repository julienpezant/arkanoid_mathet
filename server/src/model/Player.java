package model;

import java.awt.Color;

public class Player {

	private String pseudo;
	private Color color;
	private int score;
	private Paddle paddle;
	
	public Player(String pseudo, Color color){
		this.pseudo = pseudo;
		this.color = color;
		this.score = 0;
		this.paddle = new Paddle();
	}
	
	public void movePaddle(int x){
		paddle.move(x);
	}
	
}
