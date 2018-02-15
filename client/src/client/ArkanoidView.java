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
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dnr.utils.modeleecoutable.EcouteurModele;
import model.Ball;
import model.Player;

public class ArkanoidView extends JPanel implements MouseMotionListener, EcouteurModele{

	private ClientArkanoid client;
	
	private final static HashMap<String, Color> colorsList = new HashMap<String, Color>() {{
	    put("Black", Color.BLACK);
	    put("Yellow", Color.YELLOW);
	    put("Red", Color.RED);
	}};
	
	public ArkanoidView(ClientArkanoid client, int width, int height){
		this.client = client;
		
		client.getWorld().ajoutEcouteur(this);
		
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

	// JPanel is repaint there
	public void paintComponent(Graphics g){
		super.paintComponent(g);

		for(Map.Entry<String, Player> player : client.getWorld().getPlayersList().entrySet()){
			g.setColor(Color.BLACK);			
			g.drawRect(player.getValue().getPaddle().getX(), player.getValue().getPaddle().getY(), player.getValue().getPaddle().getWidth(), 10);
			g.setColor(colorsList.get(player.getValue().getColor()));				
			g.fillRect(player.getValue().getPaddle().getX(), player.getValue().getPaddle().getY(), player.getValue().getPaddle().getWidth(), 10);
		}
		
		// The ball is displayed
		Ball ball = client.getWorld().getBallsList().get(0);
		g.setColor(Color.BLACK);	
		g.drawOval(ball.getPosX(), ball.getPosY(), ball.getSide(), ball.getSide());
		g.setColor(Color.DARK_GRAY);				
		g.fillOval(ball.getPosX(), ball.getPosY(), ball.getSide(), ball.getSide());
	}
	// Client has moved his paddle with his mouse
	@Override
	public void mouseMoved(MouseEvent e) {
		client.movePlayerPaddle(e.getX());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {}


	@Override
	public void modeleMAJ(Object source) {
		repaint();
	}
}
