package model;

public class Player {

	private String pseudo;
	private String color;
	private int score;
	private Paddle paddle;
	
	private double precision;
	
	public Player(String pseudo, String color){
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
	
	public String getColor() {
		return color;
	}
	
	public Paddle getPaddle() {
		return paddle;
	}
	
	public double getPrecision() {
		return precision;
	}
	
	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public int getScore() {
		return score;
	}
	
	public void incrementScore() {
		this.score++;
		System.out.println(pseudo+" : "+score);
	}
	
}
