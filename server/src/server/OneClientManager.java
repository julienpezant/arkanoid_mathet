package server;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.Player;

public class OneClientManager implements Runnable {
	
	private Thread thread;
	private Socket socket;
	private ServerArkanoid server;

	private BufferedReader in;
	private PrintWriter out;
	
	private Player player;

	private static final String PSEUDO = "PSEUDO";
	private static final String PLAYERS_LIST = "PLAYERS_LIST";
	private static final String DISCONNECTION = "DISCONNECTION";

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
		while(true){
			String request;
			
			try {
				request = in.readLine();
				System.out.println("Client's request : "+request);
				
				if(request.equals(PSEUDO)){
					String pseudo = in.readLine();
					this.player = new Player(pseudo, Color.BLACK);
					
					System.out.println("Client's name is "+pseudo+".");
					
					System.out.println("Fetching players list...");
					out.println(PLAYERS_LIST);
					out.println(server.getClientsList().size());
					for(OneClientManager client : server.getClientsList()){
						out.println(client.player.getPseudo());
					}
					out.flush();
					System.out.println("Players list sent to "+pseudo+".");
				}

				if(request.equals(DISCONNECTION)){
					socket.close();
					server.getClientsList().remove(this);
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

}