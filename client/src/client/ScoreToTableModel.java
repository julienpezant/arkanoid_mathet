package client;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import dnr.utils.modeleecoutable.EcouteurModele;
import model.Player;
import model.World;

public class ScoreToTableModel extends AbstractTableModel implements EcouteurModele{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6914435573147217893L;
	
	private final static int COLOR = 0;
	private final static int PSEUDO = 1;
	private final static int SCORE = 2;
	private final static String[] columnNames = {"Couleur", "Pseudonyme", "Score"};
	
	private World world;
	
	public ScoreToTableModel(World world) {
		this.world = world;
		this.world.ajoutEcouteur(this);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public String getColumnName(int index) {
	    return columnNames[index];
	}

	@Override
	public int getRowCount() {
		return world.getPlayersList().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
		ArrayList<Player> playersList = new ArrayList<Player>(world.getPlayersList().values());
		
		switch (columnIndex) {
			case COLOR:
				value = playersList.get(rowIndex).getColor();
				break;
			case PSEUDO:
				value = playersList.get(rowIndex).getPseudo();
				break;
			case SCORE:
				value = String.valueOf(playersList.get(rowIndex).getScore());
				break;
			default:
				System.out.println("Sorry, we are out of players.");
		}
		
		return value;
	}

	@Override
	public void modeleMAJ(Object source) {
		fireTableDataChanged();		
	}
}
