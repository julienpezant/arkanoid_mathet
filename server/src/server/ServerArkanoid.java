package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.Ball;
import model.Player;
import model.World;

public class ServerArkanoid {
	private int port;
	
	private ServerSocket serverSocket;
	private Socket socket;
	private World world;
	
	private ArrayList<OneClientManager> clientsList;
	private boolean hasOneClient;
	
	public ServerArkanoid(int port){
		this.port = port;
		this.clientsList = new ArrayList<OneClientManager>();
		this.hasOneClient = false;
	}
	
	public void startServer() throws InterruptedException{
		try {
			serverSocket = new ServerSocket(this.port);
			
			System.out.println("Server launched.");
			
			while(true){
				System.out.println("Server awaiting for client...");
				socket = this.serverSocket.accept();
				System.out.println("Client accepted!");	
				
				if(clientsList.size() == 0){
					world = new World(this);
					hasOneClient = true;
				}
				
				OneClientManager newClient = new OneClientManager(socket, this);
				addClient(newClient);
				
				if(hasOneClient){
					this.world.startGame();
					hasOneClient = false;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServer(){
		try {
			socket.close();
			System.out.println("Serveur stopped.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addClient(OneClientManager client){
		clientsList.add(client);
	}
	
	public void removeClient(OneClientManager client){
		clientsList.remove(client);
		
		if(clientsList.size() == 0){
			world.setRunning(false);
		}
	}
	
	public void broadcastNewBallPositionMessage() {
		Ball ball = world.getBallsList().get(0);
		
		for(OneClientManager client : clientsList){
			client.broadcastNewBallPositionMessage(ball.getPosX(), ball.getPosY());
		}
	}
	
	public void broadcastNewPlayerScoreMessage(Player player){
		for(OneClientManager client : clientsList){
			client.broadcastPlayerScoreMessage(player.getPseudo(), player.getScore());
		}
	}
	
	public ArrayList<OneClientManager> getClientsList(){
		return clientsList;
	}
	
	public World getWorld(){
		return world;
	}
	
	public static void main(String[] args) {
		ServerArkanoid serveur = new ServerArkanoid(5555);
		try {
			serveur.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
