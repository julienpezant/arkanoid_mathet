package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
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
    private Color color;
    
    private ArkanoidView arkanoidView;
    private int width, height;

    // Server config
    private static final String SERVER = "localhost";
    private static final int PORT = 5555;

    // Protocol
    private static final String PSEUDO = "PSEUDO";
    private static final String NEW_CLIENT = "NEW_CLIENT";
	private static final String PLAYERS_LIST = "PLAYERS_LIST";
	private static final String NEW_POSITION_PADDLE = "NPP";
	private static final String DISCONNECTION = "DISCONNECTION";
	
	public ClientArkanoid(String pseudo, Color color) {
		// Notre fenêtre principale
		super(pseudo);
		
		this.pseudo = pseudo;
		this.color = color;
		
		width = 400;
		height = 200;
		
		// On récupère notre Container du JFrame
		Container c = this.getContentPane();
		setLayout(new BorderLayout());
		
		arkanoidView = new ArkanoidView(this, width, height);
		c.add(arkanoidView, BorderLayout.CENTER);
		
		// On redimensionne les composants du JFrame, lui donne une taille et l'affiche
		pack();
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
        out.println(PSEUDO);
        out.println(pseudo);
        out.flush();
        
        while(true){
            String message;
            try {
                message = in.readLine();

                System.out.println("Message retrieved : "+message+".");
                if(message.equals(PLAYERS_LIST)){
                	
                    int length = Integer.parseInt(in.readLine());

                    if(length > 0){
                        for(int i = 0 ; i < length; i++){
                        	String pseudo = in.readLine();
                        	int posX = Integer.parseInt(in.readLine());
                        	arkanoidView.addNewClientPaddle(pseudo, posX);
                        }
                    }
                    System.out.println("coucou");
                    out.println(NEW_CLIENT);
                    out.println(pseudo);
                    out.flush();
            	}
                if(message.equals(NEW_POSITION_PADDLE)){
                	String pseudo = in.readLine();
                	int posX = Integer.parseInt(in.readLine());
                	arkanoidView.setPaddleLocation(pseudo, posX);
                }
                if(message.equals(NEW_CLIENT)){
                	String pseudo = in.readLine();
                	int posX = Integer.parseInt(in.readLine());
                	arkanoidView.addNewClientPaddle(pseudo, posX);
                }
            } catch (IOException e) {
            	e.printStackTrace();
        	}
        }
	}

	public void movePlayerPaddle(int posX){
		out.println(NEW_POSITION_PADDLE);
		out.println(posX);
		out.flush();
	}
	
	public String getPseudo(){
		return pseudo;
	}
}
