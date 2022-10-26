import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Screen extends JPanel{
    int p;
    int q;

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
}
