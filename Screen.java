import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Screen extends JPanel{
    int p;
    int q; 
    private JTextField enterP;
    private JTextField enterQ;
    private JButton addPQValues;	
    ArrayList<Individual> population = new ArrayList<Individual>();
    public Screen(){
        setLayout(null);
        setFocusable(true);

        enterP(null);
		enterP = new JTextField();
		enterP.setBounds(345, 150, 150, 30);
		add(enterP);

        enterQ(null);
		enterQ = new JTextField();
		enterQ.setBounds(345, 150, 150, 30);
		add(enterQ);

        addPQValues = new JButton();
		addPQValues.setBounds(250, 112, 150, 30);
		addPQValues.setText("Binary Search");
		add(addPQValues);
		addPQValues.addActionListener(this);
    }

    @Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}

    @Override
	public void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    public void reproduce(){

    }

    public void actionPerformed(ActionEvent e ){

        repaint();
    }  
}
