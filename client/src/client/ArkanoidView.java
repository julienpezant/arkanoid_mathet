package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dnr.utils.modeleecoutable.EcouteurModele;
import model.Ball;
import model.Player;
import model.World;

public class ArkanoidView extends JPanel implements MouseMotionListener, EcouteurModele{

	/**
	 * 
	 */
	private static final long serialVersionUID = 551712751228799378L;
	
	private ClientArkanoid client;
	private BufferedImage background;
	private BufferedImage ballTexture;
	private HashMap<String, BufferedImage> paddleColors = new HashMap<String, BufferedImage>();
	public final static String[] COLORS = {"turquoise", "red", "green", "yellow"};
	
	public ArkanoidView(ClientArkanoid client, int width, int height, String color){
		this.client = client;
		this.client.getWorld().ajoutEcouteur(this);
		
		// Paddle images array
		try {
			background = ImageIO.read(this.getClass().getResource("/background/background.jpg"));
			paddleColors.put("turquoise", ImageIO.read(this.getClass().getResource("/paddles/paddle_turquoise.png")));
			paddleColors.put("red", ImageIO.read(this.getClass().getResource("/paddles/paddle_red.png")));
			paddleColors.put("green", ImageIO.read(this.getClass().getResource("/paddles/paddle_green.png")));
			paddleColors.put("yellow", ImageIO.read(this.getClass().getResource("/paddles/paddle_yellow.png")));
			ballTexture = ImageIO.read(this.getClass().getResource("/ball/ball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		g.drawImage(background, 0, 0, this);
		
		for(Map.Entry<String, Player> player : client.getWorld().getPlayersList().entrySet()){
			String color = player.getValue().getColor();
			BufferedImage paddle = (BufferedImage)(getPaddleColors().get(color));
			g.drawImage(paddle, player.getValue().getPaddle().getX(), player.getValue().getPaddle().getY(), this);
		}
		
		// The ball is displayed
		Ball ball = client.getWorld().getBallsList().get(0);
		g.drawImage(ballTexture, ball.getPosX(), ball.getPosY(), this);
	}
	
	// Client has moved his paddle with his mouse
	@Override
	public void mouseMoved(MouseEvent e) {
		if(!(e.getX() + 172 >= World.WIDTH))
			client.movePlayerPaddle(e.getX());
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {}


	@Override
	public void modeleMAJ(Object source) {
		repaint();
	}

	public HashMap<String, BufferedImage> getPaddleColors() {
		return paddleColors;
	}
}
