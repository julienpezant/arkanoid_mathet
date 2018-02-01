package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ArkanoidGUI extends JFrame {

	public ArkanoidGUI(){
		// Notre fenêtre principale
		super("Arkanoid");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		// On récupère notre Container du JFrame
		Container c = this.getContentPane();
		setLayout(new BorderLayout());
		
		ArkanoidView game = new ArkanoidView((int) width*4/5, (int) height*4/5);
		c.add(game, BorderLayout.CENTER);

		JPanel chat = new JPanel();
		chat.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		chat.setPreferredSize(new Dimension(((int) width), ((int) height*1/5)));
		c.add(chat, BorderLayout.SOUTH);
		
		JPanel playersList = new JPanel();
		playersList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		playersList.setPreferredSize(new Dimension(((int) width*1/5), ((int) height)));
		c.add(playersList, BorderLayout.EAST);
		
		// On redimensionne les composants du JFrame, lui donne une taille et l'affiche
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println(game.getSize().toString());
	}
	
}
