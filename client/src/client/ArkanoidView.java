package client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.colorchooser.ColorSelectionModel;

import model.Ball;
import model.Player;

public class ArkanoidView extends JPanel implements MouseMotionListener, ActionListener{

	private ClientArkanoid client;
<<<<<<< HEAD
	private int width, height;
	private ArrayList<Player> playersList;
	private ArrayList<Ball> ballsList;
	private String color;
	private String basePath = new File("").getAbsolutePath();
	private BufferedImage background;

=======
	private ArrayList<Player> playersList;
	private Ball ball;
	
	private int width, height;
	
	private final static HashMap<String, Color> colorsList = new HashMap<String, Color>() {{
	    put("Black", Color.BLACK);
	    put("Yellow", Color.YELLOW);
	    put("Red", Color.RED);
	}};
>>>>>>> f84a88d59bd4f948905db065f4a6fb00cfe6bbb2
	
	public ArkanoidView(ClientArkanoid client, int width, int height, String color){
		this.client = client;
		this.width = width;
		this.height = height;
		this.color = color;
		try {			
			File backgroundFile = new File(basePath+"\\res\\img\\background\\retro.jpg");
			this.background = ImageIO.read(backgroundFile);
		}catch (IOException e) {
			e.printStackTrace();
			this.background = null;
		}
		
		playersList = new ArrayList<Player>();
<<<<<<< HEAD
		ballsList = new ArrayList<Ball>();
=======
		ball = new Ball(width/2, height/2, 10);
>>>>>>> f84a88d59bd4f948905db065f4a6fb00cfe6bbb2
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		setCursor(blankCursor);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setPreferredSize(new Dimension(width, height));
<<<<<<< HEAD
=======
		setBackground(new Color(0, 100, 255));
>>>>>>> f84a88d59bd4f948905db065f4a6fb00cfe6bbb2
		
		addMouseMotionListener(this);
	}
	
	public void addNewBall(int posX, int posY) {
		ballsList.add(new Ball(posX, posY));
		repaint();
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
		g.drawImage(this.background,0,0,null);
		for(Player player : playersList){
<<<<<<< HEAD
			//g.fillRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
			g.drawImage(player.getPaddle().getPaddleColored(), player.getPaddle().getX(), player.getPaddle().getY(), null);
=======
			g.setColor(Color.BLACK);			
			g.drawRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
			g.setColor(colorsList.get(player.getColor()));				
			g.fillRect(player.getPaddle().getX(), player.getPaddle().getY(), player.getPaddle().getWidth(), 10);
>>>>>>> f84a88d59bd4f948905db065f4a6fb00cfe6bbb2
		}
		g.setColor(Color.BLACK);	
		g.drawOval(ball.getPosX(), ball.getPosY(), ball.getSide(), ball.getSide());
		g.setColor(Color.DARK_GRAY);				
		g.fillOval(ball.getPosX(), ball.getPosY(), ball.getSide(), ball.getSide());
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		client.movePlayerPaddle(e.getX());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e);		
	}
}
