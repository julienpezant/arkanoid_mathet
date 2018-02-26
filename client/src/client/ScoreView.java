package client;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.World;

public class ScoreView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2612633884493878142L;
	
	private JTable myTable;

	public ScoreView(World world){
		myTable = new JTable(new ScoreToTableModel(world));
		myTable.setAutoCreateRowSorter(true);
		setLayout(new BorderLayout());
		Dimension d = myTable.getPreferredSize();
		JScrollPane scrollPane = new JScrollPane(myTable);
		System.out.println(myTable.getRowHeight()*myTable.getRowCount()+1);
		scrollPane.setPreferredSize(new Dimension(d.width, 100));
		add((scrollPane), BorderLayout.NORTH);
	}
	
	public JTable getMyTable(){
		return myTable;
	}

}
