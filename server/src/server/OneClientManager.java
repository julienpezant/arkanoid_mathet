package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import dnr.utils.modeleecoutable.EcouteurModele;
import model.Ball;
import model.Player;

public class OneClientManager implements Runnable {
	
	private Socket socket;
	private ServerArkanoid server;

	private BufferedReader in;
	private PrintWriter out;
	
	private Player player;
	
	private boolean clientOnline = false;

	private static final String PSEUDO = "PSEUDO";
	private static final String NEW_CLIENT = "NEW_CLIENT";
	private static final String PLAYERS_LIST = "PLAYERS_LIST";
	private static final String NEW_POSITION_PADDLE = "NPP";
	private static final String NEW_POSITION_BALL = "NPB";
	private static final String NEW_PLAYER_SCORE = "NEW_PLAYER_SCORE";
	private static final String DISCONNECTION = "DISCONNECTION";
	private static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";

	public OneClientManager(Socket socket, ServerArkanoid server) {
		this.socket = socket;
		this.server = server;
		
		try{
			this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream());
			new Thread(this).start();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		clientOnline = true;
		
		while(clientOnline){
			String request;
			
			try {
				request = in.readLine();
				//System.out.println("Client's request : "+request);
				
				switch (request) {
	                case PSEUDO:
	                	handlePseudoRequest();
	                	break;
	                case NEW_CLIENT:
	                	handleNewClientRequest();
	                	break;
	                case NEW_POSITION_PADDLE:
	                	handleNewPositionPaddleRequest();
	                	break;
	                case DISCONNECTION:
	                	handleDisconnectionRequest();
	                	clientOnline = false;
	                	break;
	                default:
	                	break;
	            }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}


	// Manager is now receiving the pseudo and color of its client
	private void handlePseudoRequest() throws IOException {
		String pseudo = in.readLine();
		String color = in.readLine();
		
		System.out.println("Client's name is "+pseudo+".");
		
		// New player added to the game
		player = new Player(pseudo, color);
		server.getWorld().addPlayer(player);
		
		// Its client will now receive the players list (pseudo, color and paddle)
		System.out.println("Fetching players list...");
		
		synchronized(server) {
			out.println(PLAYERS_LIST);
			out.println(server.getClientsList().size());
			for(OneClientManager client : server.getClientsList()){
				out.println(client.player.getPseudo());
				out.println(client.player.getColor());
				out.println(client.player.getPaddle().getPosX());
			}
			out.flush();
		}
		
		System.out.println("Players list sent to "+pseudo+".");
	}

	// Manager is notified its client wants to play with others
	private void handleNewClientRequest() {
		// For each client, they now know that someone has join the game
		synchronized(server) {
			for(OneClientManager client : server.getClientsList()){
				if(!client.player.getPseudo().equals(player.getPseudo())){
					client.out.println(NEW_CLIENT);
					client.out.println(player.getPseudo());
					client.out.println(player.getColor());
					client.out.println(player.getPaddle().getPosX());
					client.out.flush();
				}	
			}
		}
	}

	// Manager is notified that his client has modified the position of his paddle
	private void handleNewPositionPaddleRequest() throws NumberFormatException, IOException {
		int posX = Integer.parseInt(in.readLine());
		
		// We move his paddle in the model
		player.getPaddle().move(posX);
		
		// All clients now received the notification to update the position of this specific paddle

		synchronized(server) {
			for(OneClientManager client : server.getClientsList()){
				client.out.println(NEW_POSITION_PADDLE);
				client.out.println(player.getPseudo());
				client.out.println(player.getPaddle().getPosX());
				client.out.flush();
			}
		}
	}
	
	// Manager is notified that the managed client wants to disconnect
	private void handleDisconnectionRequest() throws IOException {
		System.out.println(player.getPseudo() + " has disconnected.");
		
		// Informs to all other client which one is about to leave the game
		synchronized(server) {
			for(OneClientManager client : server.getClientsList()){
				if(!client.player.getPseudo().equals(player.getPseudo())){
					client.out.println(CLIENT_DISCONNECTED);
					client.out.println(player.getPseudo());
					client.out.flush();
				}
			}
		}
		
		// Server delete the client and we close the socket dedicated to him
		server.getWorld().removePlayer(player);
		server.removeClient(this);
		//TODO dans le while, mettre le param a false pour terminer le thread
		socket.close();
	}
	
	/*
	 * BROADCASTS
	 */

	public void broadcastNewBallPositionMessage(long posX, long posY){
		synchronized(server) {
			out.println(NEW_POSITION_BALL);
			out.println(posX);
			out.println(posY);
			out.flush();
		}
	}

	public void broadcastNewPlayerScoreMessage(String pseudo, int score) {
		synchronized(server) {
			out.println(NEW_PLAYER_SCORE);
			out.println(pseudo);
			out.println(score);
			out.flush();
		}
	}

}
