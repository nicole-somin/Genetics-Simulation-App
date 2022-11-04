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

public class Screen extends JPanel implements ActionListener{
    int p;
    int q; 
    private JTextField enterP;
    private JTextField enterQ;
    private JTextField enterPopSize;
    private JButton addPopSize;
    private JButton addPQValues;	
    ArrayList<Individual> population = new ArrayList<Individual>();
    public Screen(){
        setLayout(null);
        setFocusable(true);

		enterP = new JTextField();
		enterP.setBounds(345, 150, 150, 30);
		add(enterP);

		enterQ = new JTextField();
		enterQ.setBounds(445, 150, 150, 30);
		add(enterQ);

        enterPopSize = new JTextField();
        enterPopSize.setBounds(545, 150, 150, 30);
		add(enterPopSize);

        addPQValues = new JButton();
		addPQValues.setBounds(250, 112, 150, 30);
		addPQValues.setText("add p/q values");
        addPQValues.addActionListener(this);
		add(addPQValues);

        addPopSize = new JButton();
		addPopSize.setBounds(350, 112, 150, 30);
		addPopSize.setText("set population size");
        addPopSize.addActionListener(this);
		add(addPopSize);
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
        if(e.getSource() == addPQValues){
            p = Integer.parseInt(enterP.getText());
            q = Integer.parseInt(enterQ.getText());
            for(int i=0; i<Integer.parseInt(enterPopSize.getText())*p;i++){
                population.add(new Individual(p,p));
            }
            for(int i=0; i<Integer.parseInt(enterPopSize.getText())*q;i++){
                population.add(new Individual(q,q));
            }
        } 
       repaint();
    }  
}
