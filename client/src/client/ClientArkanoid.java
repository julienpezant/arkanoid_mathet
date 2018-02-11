package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class ClientArkanoid extends JFrame implements Runnable {
	
	private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private String pseudo;
    private String color;
    
    //private Player player;
    
    private ArkanoidView arkanoidView;
	private boolean clientOnline = false;
    
    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    // Server config
    private static final String SERVER = "localhost";
    private static final int PORT = 5555;

    // Protocol
    private static final String PSEUDO = "PSEUDO";
    private static final String NEW_CLIENT = "NEW_CLIENT";
	private static final String PLAYERS_LIST = "PLAYERS_LIST";
	private static final String NEW_POSITION_PADDLE = "NPP";
	private static final String DISCONNECTION = "DISCONNECTION";
	private static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";
	
	public ClientArkanoid(String pseudo, String color) {
		// Notre fenêtre principale
		super(pseudo);
		
		this.pseudo = pseudo;
		this.color = color;
		//this.player = new Player(pseudo, color, 0);
		
		// On récupère notre Container du JFrame
		Container c = this.getContentPane();
		setLayout(new BorderLayout());
		
		arkanoidView = new ArkanoidView(this, WIDTH, HEIGHT);
		c.add(arkanoidView, BorderLayout.CENTER);
		
		// On redimensionne les composants du JFrame, lui donne une taille et l'affiche
		pack();
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    addWindowListener(new WindowAdapter() {
	        @Override
	        public void windowClosing(WindowEvent event) {
	            try {
					broadcastDisconnectionMessage();
					dispose();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });
		
		try {
            socket = new Socket(SERVER, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
            new Thread(this).start();
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public void run() {
		clientOnline = true;
		
        out.println(PSEUDO);
        out.println(pseudo);
        out.println(color);
        out.flush();
        
        while(clientOnline){
            String message;
            try {
                message = in.readLine();
                //System.out.println("Message retrieved : "+message+".");
                
                switch (message) {
	                case PLAYERS_LIST:
	                	handlePlayersListMessage();
	                	break;
	                case NEW_CLIENT:
	                	handleNewClientMessage();
	                	break;
	                case NEW_POSITION_PADDLE:
	                	handleNewPositionPaddleMessage();
	                	break;
	                case CLIENT_DISCONNECTED:
	                	handleClientDisconnectedMessage();
	                	break;
	                default:
	                	break;
	            }
                
            } catch (IOException e) {
            	e.printStackTrace();
        	}
        }
	}

	public void movePlayerPaddle(int posX){
		broadcastNewPositionPaddleRequest(posX);
	}
	
	// HANDLERS
	
	private void handleNewPositionPaddleMessage() throws IOException {
		String pseudo = in.readLine();
		int posX = Integer.parseInt(in.readLine());
    	arkanoidView.setPaddleLocation(pseudo, posX);
	}

	private void handlePlayersListMessage() throws NumberFormatException, IOException {
		int length = Integer.parseInt(in.readLine());
		
		for(int i = 0 ; i < length; i++)
			handleNewClientMessage();
		
		broadcastNewClientRequest();
	}
	
	private void handleNewClientMessage() throws IOException{
		String pseudo = in.readLine();
		String color = in.readLine();
    	int posX = Integer.parseInt(in.readLine());
    	arkanoidView.addNewClientPaddle(pseudo, color, posX);
	}
	
	private void handleClientDisconnectedMessage() throws IOException {
		String pseudo = in.readLine();
		arkanoidView.removeClientPaddle(pseudo);
	}

	// BROADCASTS
	
	private void broadcastNewClientRequest(){
		out.println(NEW_CLIENT);
        out.flush();
	}
	
	private void broadcastNewPositionPaddleRequest(int posX){
		out.println(NEW_POSITION_PADDLE);
		out.println(posX);
		out.flush();
	}

	protected void broadcastDisconnectionMessage() throws IOException {
		out.println(DISCONNECTION);
		out.flush();
		clientOnline = false;
		socket.close();
	}
}
