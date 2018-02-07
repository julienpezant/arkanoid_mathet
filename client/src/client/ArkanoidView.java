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

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ArkanoidView extends JPanel implements MouseMotionListener{

	private int rect_x, rect_y;
	private int width, height;
	
	public ArkanoidView(int width, int height){
		this.width = width;
		this.height = height;
		
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		// Set the blank cursor to the JFrame.
		setCursor(blankCursor);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setPreferredSize(new Dimension(width, height));
		setBackground(new Color(255, 255, 255));
		
		addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawRect(rect_x, rect_y, 100, 20);
	  }

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("["+e.getX()+" ; "+e.getY()+"]");
		if(e.getX() < width - 100 && e.getX() > 0){
			rect_x = e.getX();
			rect_y = height - (height/5);
			repaint();
		}	
	}

}
