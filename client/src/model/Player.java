package model;

public class Player {

	private String pseudo;
	private String color;
	private int score;
	private Paddle paddle;
	
	public Player(String pseudo, String color, int score, int posX){
		this.pseudo = pseudo;
		this.color = color;
		this.score = score;
		this.paddle = new Paddle(40, posX, World.HEIGHT - 100);
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
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
