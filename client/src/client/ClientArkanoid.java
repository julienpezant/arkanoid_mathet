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

import model.Player;
import model.World;

public class ClientArkanoid extends JFrame implements Runnable {

	private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private String pseudo;
    private String color;
	private boolean clientOnline = false;
    
	private World world;
	
    private ArkanoidView arkanoidView;

    // Server config
    private static final String SERVER = "localhost";
    private static final int PORT = 5555;
    // Protocol
    private static final String PSEUDO = "PSEUDO";
    private static final String NEW_CLIENT = "NEW_CLIENT";
	private static final String PLAYERS_LIST = "PLAYERS_LIST";
	private static final String NEW_POSITION_PADDLE = "NPP";
	private static final String NEW_POSITION_BALL = "NPB";
	private static final String NEW_PLAYER_SCORE = "NEW_PLAYER_SCORE";
	private static final String DISCONNECTION = "DISCONNECTION";
	private static final String CLIENT_DISCONNECTED = "CLIENT_DISCONNECTED";
	
	
	public ClientArkanoid(String pseudo, String color) {
		// Notre fenêtre principale
		super(pseudo);
		
		this.pseudo = pseudo;
		this.color = color;
		
		this.world = new World();
		
		// JFrame container
		Container c = this.getContentPane();
		setLayout(new BorderLayout());
		
		// JPanel to contain the game
		arkanoidView = new ArkanoidView(this, World.WIDTH, World.HEIGHT);
		c.add(arkanoidView, BorderLayout.CENTER);
		
		// JFrame now displayable
		pack();
		setSize(World.WIDTH, World.HEIGHT);
		setVisible(true);
		
		// WindowListener on the default close operation
		// Broadcast to the server the disconnection message
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
		    // The socket is initialized, with its BufferedReader and PrintWriter
            socket = new Socket(SERVER, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            
    	    // Thread is started and ready to receive and broadcast messages
            new Thread(this).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}

	// Thread started
	@Override
	public void run() {
		// Client is now online
		clientOnline = true;
		
		// Client broadcast his pseudo and color to the server
        out.println(PSEUDO);
        out.println(pseudo);
        out.println(color);
        out.flush();
        
        // Client is now able to receive message from the server
        while(clientOnline){
            String message;
            try {
                message = in.readLine();
                //System.out.println("Message retrieved : "+message+".");
                
                // We route the received message to handle it correctly
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
	                case NEW_POSITION_BALL:
	                	handleNewPositionBallMessage();
	                	break;
	                case NEW_PLAYER_SCORE:
	                	handleNewPlayerScoreMessage();
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

	// Client wants to update the position of his paddle
	// We broadcast a message to the server, the position is updated in the model
	public void movePlayerPaddle(int posX){
		broadcastNewPositionPaddleRequest(posX);
	}
	
	/**
	 * HANDLERS
	 */
	
	// Server send the new position of the paddle referenced by the pseudo
	private void handleNewPositionPaddleMessage() throws IOException {
		String pseudo = in.readLine();
		int posX = Integer.parseInt(in.readLine());
		
		// World officialy updates the position
		world.setLocationPlayerPaddle(pseudo, posX);
	}
	
	// Server send the new position of the paddle referenced by the pseudo
	private void handleNewPositionBallMessage() throws IOException {
		int posX = Integer.parseInt(in.readLine());
		int posY = Integer.parseInt(in.readLine());
		
		//System.out.println("[x:"+posX+" ; y:"+posY+"]");
		
		// World officialy updates the position
		world.setLocationBall(posX, posY);
	}

	// Server send the players list to the newly connected client
	private void handlePlayersListMessage() throws NumberFormatException, IOException {
		int length = Integer.parseInt(in.readLine());
		
		// For each player
		for(int i = 0 ; i < length; i++)
			handleNewClientMessage();
		
		// Now the client has all data, other client will see the new client
		broadcastNewClientRequest();
	}
	
	// Server send data about the newly connected client, or other players already connected
	private void handleNewClientMessage() throws IOException{
		String pseudo = in.readLine();
		String color = in.readLine();
    	int posX = Integer.parseInt(in.readLine());
    	
    	world.addPlayer(new Player(pseudo, color, posX));
	}
	
	// Server send the new score for one player
	private void handleNewPlayerScoreMessage() {
		
	}
	
	// Server notifies other clients that someone has disconnected
	private void handleClientDisconnectedMessage() throws IOException {
		String pseudo = in.readLine();
		
		world.removePlayer(world.getPlayersList().get(pseudo));
	}

	/**
	 * BROADCASTS
	 */
	
	// Client broadcast that he's about to be in-game
	// Server will inform all other clients that the new client is about to play
	private void broadcastNewClientRequest(){
		out.println(NEW_CLIENT);
        out.flush();
	}
	
	// Client has updated his paddle position
	// Server will be notify
	private void broadcastNewPositionPaddleRequest(int posX){
		out.println(NEW_POSITION_PADDLE);
		out.println(posX);
		out.flush();
	}

	// Client wants to disconnect
	protected void broadcastDisconnectionMessage() throws IOException {
		out.println(DISCONNECTION);
		out.flush();
		clientOnline = false;
		socket.close();
	}
	
	public World getWorld(){
		return world;
	}
}
