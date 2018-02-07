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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ArkanoidView extends JPanel implements MouseMotionListener{

	private ClientArkanoid client;
	private int width, height;
	private HashMap<String, Rectangle> paddles;
	
	public ArkanoidView(ClientArkanoid client, int width, int height){
		this.client = client;
		this.width = width;
		this.height = height;
		
		paddles = new HashMap<String, Rectangle>();
		
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
	
	public void setPaddleLocation(String pseudo, int posX){
		paddles.get(pseudo).setLocation(posX, (int) paddles.get(pseudo).getY());
		repaint();
	}
	

	public void addNewClientPaddle(String pseudo, int posX) {
		paddles.put(pseudo, new Rectangle(posX, height - (height/2), 40, 10));
		repaint();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(Map.Entry<String, Rectangle> paddle : paddles.entrySet()){
			g.drawRect(paddle.getValue().x, paddle.getValue().y, paddle.getValue().width, paddle.getValue().height);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		client.movePlayerPaddle(e.getX());
	}
}
