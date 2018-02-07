package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

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
		
		ArkanoidView arkanoidView = new ArkanoidView((int) width, (int) height);
		c.add(arkanoidView, BorderLayout.CENTER);
		
		// On redimensionne les composants du JFrame, lui donne une taille et l'affiche
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println(arkanoidView.getSize().toString());
	}
}
