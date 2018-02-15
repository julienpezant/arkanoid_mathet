package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import model.World;

public class ServerArkanoid {
	private int port;
	
	private ServerSocket serverSocket;
	private Socket socket;
	private World world;
	
	private ArrayList<OneClientManager> clientsList;
	
	private boolean hasOneClient = false;
	
	public ServerArkanoid(int port){
		this.port = port;
		this.clientsList = new ArrayList<OneClientManager>();
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
					this.world = new World();
					hasOneClient = true;
				}
				
				OneClientManager newClient = new OneClientManager(socket, this);
				this.world.ajoutEcouteur(newClient);
				clientsList.add(newClient);
				
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
