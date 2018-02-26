package client;

import javax.swing.JOptionPane;

import client.ArkanoidView;
import client.ClientArkanoid;

public class Launcher {
	public static void main(String[] args) {
	    String name = JOptionPane.showInputDialog(null, "Entrez un pseudonyme.", "Pseudonyme", JOptionPane.QUESTION_MESSAGE);    
        String color = (String) JOptionPane.showInputDialog(null, "Sélectionnez votre couleur", "Couleur", JOptionPane.QUESTION_MESSAGE, null, ArkanoidView.COLORS, ArkanoidView.COLORS[0]);
		new ClientArkanoid(name, color);
    }
}
