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
	
	public ServerArkanoid(int port){
		this.port = port;
		this.clientsList = new ArrayList<OneClientManager>();
	}
	
	public void startServer(){
		try {
			serverSocket = new ServerSocket(this.port);
			
			System.out.println("Server launched.");
			
			this.world = new World();
			
			while(true){
				System.out.println("Server awaiting for client...");
				socket = this.serverSocket.accept();
				System.out.println("Client accepted!");				
				OneClientManager newClient = new OneClientManager(socket, this);
				clientsList.add(newClient);
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
	
	public static void main(String[] args){
		ServerArkanoid serveur = new ServerArkanoid(5555);
		serveur.startServer();
	}
}
