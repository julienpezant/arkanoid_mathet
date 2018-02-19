package client;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.World;

public class ScoreView extends JPanel{
	
	private JTable myTable;

	public ScoreView(World world){
		myTable = new JTable(new ScoreToTableModel(world));
		myTable.setAutoCreateRowSorter(true);
		setLayout(new BorderLayout());
		add(new JScrollPane(myTable), BorderLayout.CENTER);
	}
	
	public JTable getMyTable(){
		return myTable;
	}

}
