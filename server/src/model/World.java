package model;

import java.util.ArrayList;
import java.util.Random;

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
		while(true) {
			
		}
		
	}
	
	public void addPlayer(Player player){
		playersList.add(player);
	}
	
	/**
	 * Detects collisions between the balls and the limits of the world and paddles
	 * Applies rebounds (by updating the movement vectors)
	 * Increments scores
	 * Detects who is the most precise when returning the ball
	 * 
	 * Doesn't handle balls that exit the world by the bottom yet.
	 * Doesn't handle rebounds between balls (or bricks)
	 */
	public void detectCollisions() {
		for(Ball ball : ballsList) {
			
			//tests if there's a rebound on the left side
			if(ball.getLimitLeft() <= 0) {
				ball.updateMovementVector(ball.getMovX() * -1,ball.getMovY());
			}
			//tests if there's a rebound on the right side
			if(ball.getLimitRight() >= WIDTH) {
				ball.updateMovementVector(ball.getMovX() * -1,ball.getMovY());
			}
			//tests if there's a rebound on the top, people may be scoring points
			if(ball.getLimitTop() <= 0) {
				
				//for each player, if their name is in the ball's scorers list, increments their score
				for(Player player : playersList) {
					for(String pseudo : ball.getScorers()) {
						if(pseudo.equals(player.getPseudo())) {
							player.incrementScore();
						}
					}
					
				}
				//since points where given, resets the scorers, and applies the rebound
				ball.removeScorers();
				ball.updateMovementVector(ball.getMovX(),ball.getMovY() * -1);
			}
			
			//tests if there's a rebound on paddles and adds scorers accordingly
			boolean paddleRebound = false;
			int bestPrecision = -1;
			
			for(Player player : playersList) {
				//for each player's paddle
				Paddle paddle = player.getPaddle();
				int paddleTop = paddle.getPosY();
				int paddleCenter = paddle.getCenter();
				int paddleLimitLeft = paddle.getPosX();
				int paddleLimitRight = paddle.getLimitRight();
				int playerPrecision;
				/* 
				 * tests if the ball reached the y coordinate of the top of the paddle
				 * may warrant changes later on
				 */
				
				if (ball.getLimitBottom() == paddleTop) {
					//tests if the ball is actually touching the paddle
					if(ball.getPosX() >= paddleLimitLeft && ball.getPosX() <= paddleLimitRight) {
						
						//The ball touched a paddle, therefore there is a rebound
						paddleRebound = true;
						
						/* 
						 * gets the precision of the player that touched the ball, aka the distance between the center of the
						 * player's paddle and the spot at which the ball collided with it.
						 */
						
						//touched the right or the center of the paddle
						if(ball.getPosX() >= paddleCenter) {
							playerPrecision = ball.getPosX() - paddleCenter;
						}else {
						//touched the left of the paddle
							playerPrecision =  paddleCenter - ball.getPosX();
						}
						player.setPrecision(playerPrecision);
						/*
						 * if the players' precision is better than the current best precision, updates the latter.
						 * also removes the former scorers from the list if their precision is lower
						 * if his precision is at least as good, adds him to the list
						 */
						if(bestPrecision == -1 || bestPrecision >= playerPrecision) {
							if(bestPrecision > playerPrecision) {
								ball.removeScorers();
								bestPrecision = playerPrecision;
							}
							ball.addScorer(player.getPseudo());
						}
					}
				}
			}
			if(paddleRebound) {
				/*
				 * here we use the paddles' movement vectors to alter the trajectory of the ball post rebound
				 * there was a rebound but there may be several players with the highest precision
				 * for now, a random one is picked among the scorers 
				 */
				ArrayList<String> scorers = ball.getScorers();
				String pseudo = scorers.get(new Random().nextInt(scorers.size()));
				for (Player player : playersList) {
					if(pseudo.equals(player.getPseudo())) {
						Paddle paddle = player.getPaddle();
						int paddleMovX = paddle.getMovX();
						ball.updateMovementVector(ball.getMovX() + paddleMovX, ball.getMovY() * -1);
						break;
					}
				}
			}
			
			if(ball.getLimitBottom() >= HEIGHT) {
				//the ball reached the bottom and is put back in front of a random paddle
				Player player = playersList.get(new Random().nextInt(playersList.size()));
				Paddle paddle = player.getPaddle();
				int paddleMovX = paddle.getMovX();
				int newPosX = paddle.getCenter();
				int newPosY = paddle.getPosY() + ball.getRadius() + 1;
				ball.replace(newPosX,  newPosY);
				
				//the ball is sent back using the chosen paddle's current movement vector and a random y value
				ball.updateMovementVector(paddleMovX, new Random().nextInt(HEIGHT) * -1);
				
			}
		}
	}
}
