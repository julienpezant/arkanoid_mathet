package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Ball;
import model.Player;

public class ArkanoidView extends JPanel implements MouseMotionListener{

	private ClientArkanoid client;
	private ArrayList<Player> playersList;
	private Ball ball;
	
	private int width, height;
	
	private final static HashMap<String, Color> colorsList = new HashMap<String, Color>() {{
	    put("Black", Color.BLACK);
	    put("Yellow", Color.YELLOW);
	    put("Red", Color.RED);
	}};
	
	public ArkanoidView(ClientArkanoid client, int width, int height){
		this.client = client;
		this.width = width;
		this.height = height;
		
		playersList = new ArrayList<Player>();
		ball = new Ball(width/2, height/2, 10);
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		setCursor(blankCursor);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(0, 100, 255));
		
		addMouseMotionListener(this);
	}
	

	// New client has connected to the game
	// One more player and one more paddle to display
	public void addNewClientPaddle(String pseudo, String color, int posX) {
		playersList.add(new Player(pseudo, color, posX));
		repaint();
	}
	
	// Client is now disconnected. We remove it from the game
	public void removeClientPaddle(String pseudo) {
		for(Player player : playersList){
			if(player.getPseudo().equals(pseudo)){
				playersList.remove(player);
				break;
			}
		}
		repaint();
	}
	
	// We update one paddle location, for one client referenced by his pseudo
	public void setPaddleLocation(String pseudo, int posX){
		for(Player player : playersList){
			if(player.getPseudo().equals(pseudo)){
				player.getPaddle().setLocation(posX, player.getPaddle().getY());
				break;
			}
		}
		repaint();
	}

	// JPanel is repaint there
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// All paddles are displayed
		for(Player player : playersList){
			g.setColor(Color.BLACK);			
			g.drawRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
			g.setColor(colorsList.get(player.getColor()));				
			g.fillRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
		}
		
		// The ball is displayed
		g.setColor(Color.BLACK);	
		g.drawOval(ball.getPosX(), ball.getPosY(), ball.getSide(), ball.getSide());
		g.setColor(Color.DARK_GRAY);				
		g.fillOval(ball.getPosX(), ball.getPosY(), ball.getSide(), ball.getSide());
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	// Client has moved his paddle with his mouse
	@Override
	public void mouseMoved(MouseEvent e) {
		client.movePlayerPaddle(e.getX());
	}
}
