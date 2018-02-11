package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

	private void handleDisconnectionRequest() throws IOException {
		System.out.println(player.getPseudo() + " has disconnected.");
		
		for(OneClientManager client : server.getClientsList()){
			if(!client.player.getPseudo().equals(player.getPseudo())){
				client.out.println(CLIENT_DISCONNECTED);
				client.out.println(player.getPseudo());
				client.out.flush();
			}
		}
		
		server.getClientsList().remove(this);
		socket.close();
	}

	private void handleNewPositionPaddleRequest() throws NumberFormatException, IOException {
		int posX = Integer.parseInt(in.readLine());
		player.getPaddle().move(posX);
		for(OneClientManager client : server.getClientsList()){
			client.out.println(NEW_POSITION_PADDLE);
			client.out.println(player.getPseudo());
			client.out.println(posX);
			client.out.flush();
		}
	}

	private void handleNewClientRequest() {
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

	private void handlePseudoRequest() throws IOException {
		String pseudo = in.readLine();
		String color = in.readLine();
		player = new Player(pseudo, color);
		
		System.out.println("Client's name is "+pseudo+".");
		
		System.out.println("Fetching players list...");
		out.println(PLAYERS_LIST);
		out.println(server.getClientsList().size());
		for(OneClientManager client : server.getClientsList()){
			out.println(client.player.getPseudo());
			out.println(client.player.getColor());
			out.println(client.player.getPaddle().getPosX());
		}
		out.flush();
		System.out.println("Players list sent to "+pseudo+".");
	}

}
