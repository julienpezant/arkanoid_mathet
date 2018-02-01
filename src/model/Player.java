package model;

public class Player {

	//pseudo, couleur,score, raquette / deplacerraquette
	
	protected String name;
	protected int rgba;
	protected int score;
	protected Paddle paddle;
	
	public Player(String name, int rgba, int score, Paddle paddle) {
		this.name = name;
		this.rgba = rgba;
		this.score = score;
		this.paddle = paddle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRgba() {
		return rgba;
	}

	public void setRgba(int rgba) {
		this.rgba = rgba;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}
	
	
}
