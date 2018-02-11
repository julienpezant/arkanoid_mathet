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

import model.Player;

public class ArkanoidView extends JPanel implements MouseMotionListener{

	private ClientArkanoid client;
	private int width, height;
	//private HashMap<String, Rectangle> paddles;
	private ArrayList<Player> playersList;
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
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		setCursor(blankCursor);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(255, 255, 255));
		
		addMouseMotionListener(this);
	}
	

	public void addNewClientPaddle(String pseudo, String color, int posX) {
		playersList.add(new Player(pseudo, color, posX));
		repaint();
	}
	
	public void removeClientPaddle(String pseudo) {
		for(Player player : playersList){
			if(player.getPseudo().equals(pseudo)){
				playersList.remove(player);
				break;
			}
		}
		repaint();
	}
	
	public void setPaddleLocation(String pseudo, int posX){
		for(Player player : playersList){
			if(player.getPseudo().equals(pseudo)){
				player.getPaddle().setLocation(posX, player.getPaddle().getY());
				break;
			}
		}
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Player player : playersList){
			g.setColor(colorsList.get("Black"));			
			g.drawRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
			g.setColor(colorsList.get(player.getColor()));				
			g.fillRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		client.movePlayerPaddle(e.getX());
	}
}
