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

    ArrayList<Individual> population = new ArrayList<Individual>();
    public Screen(){
        setLayout(null);
        setFocusable(true);
    }

    @Override
	public Dimension getPreferredSize(){
		return new Dimension(800,600);
	}

    @Override
	public void paintComponent(Graphics g){
    }

    public void actionPerformed(ActionEvent e ){

    }
}
