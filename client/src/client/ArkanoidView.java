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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import dnr.utils.modeleecoutable.EcouteurModele;
import model.Ball;
import model.Player;

public class ArkanoidView extends JPanel implements MouseMotionListener, EcouteurModele{

	private ClientArkanoid client;
	private BufferedImage background;
	private BufferedImage ballTexture;
	private String cwd = System.getProperty("user.dir");
	private HashMap<String, BufferedImage> paddleColors = new HashMap<String, BufferedImage>();
	
	
	public ArkanoidView(ClientArkanoid client, int width, int height, String color){
		this.client = client;
		this.client.getWorld().ajoutEcouteur(this);
		try {
			this.background = ImageIO.read(new File(cwd+"/res/background/background.jpg"));	
			paddleColors.put("turquoise", ImageIO.read(new File(this.cwd+"/res/paddles/paddle_turquoise.png")));
			paddleColors.put("red", ImageIO.read(new File(this.cwd+"/res/paddles/paddle_red.png")));
			paddleColors.put("green", ImageIO.read(new File(this.cwd+"/res/paddles/paddle_green.png")));
			paddleColors.put("yellow", ImageIO.read(new File(this.cwd+"/res/paddles/paddle_yellow.png")));
			this.ballTexture = ImageIO.read(new File(this.cwd+"/res/ball/ball.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			System.out.println(player.getValue().getPseudo());
			String color = player.getValue().getColor();
			BufferedImage paddle = (BufferedImage)(getPaddleColors().get(color));
			g.drawImage(paddle, player.getValue().getPaddle().getX(), player.getValue().getPaddle().getY(), this);
			//g.setColor(Color.BLACK);			
			//g.drawRect(player.getValue().getPaddle().getX(), player.getValue().getPaddle().getY(), player.getValue().getPaddle().getWidth(), 10);
			//g.setColor(colorsList.get(player.getValue().getColor()));				
			//g.fillRect(player.getValue().getPaddle().getX(), player.getValue().getPaddle().getY(), player.getValue().getPaddle().getWidth(), 10);
		}
		
		// The ball is displayed
		Ball ball = client.getWorld().getBallsList().get(0);
		g.drawImage(ballTexture, ball.getPosX(), ball.getPosY(), this);
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

	public HashMap getPaddleColors() {
		return paddleColors;
	}

	public void setPaddleColors(HashMap paddleColors) {
		this.paddleColors = paddleColors;
	}
}
