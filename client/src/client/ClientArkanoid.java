package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientArkanoid implements Runnable {
	
	private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private String pseudo;

    // Server config
    private static final String SERVER = "localhost";
    private static final int PORT = 5555;

    // Protocol
    private static final String PSEUDO = "PSEUDO";
	private static final String PLAYERS_LIST = "PLAYERS_LIST";
	private static final String DISCONNECTION = "DISCONNECTION";
	
	public ClientArkanoid(String pseudo) {
		try {
            this.pseudo = pseudo;

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
                        	System.out.println(in.readLine()+" connected.");
                        }
                    }
            	}
            } catch (IOException e) {
                    e.printStackTrace();
            }
        }
	}
	
	public static void main(String[] args) {
		ClientArkanoid client = new ClientArkanoid("Yanis");
    }

}
