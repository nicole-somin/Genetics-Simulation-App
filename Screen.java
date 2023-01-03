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
    private double p;
    private double q; 
    private double recRat;
    private double domRat;
    private double hetRat;
    private double recNum;
    private double domNum;
    private double hetNum;
    private double popSize;
    private int randDomNum = 0;
    private int randRecNum = 0;
    private int randHetNum = 0;
    private int sampleNum = 0;
    private boolean valsSet = false;
    private boolean 
    private JTextField enterP;
    private JTextField enterQ;
    private JTextField enterPopSize;
    private JTextField enterSampleNum;
    private JTextField enterNatSelecVal;
    private JButton natSelec;
    private JButton enter;
    private JButton noReplace;	
    private String sampleType = "";
    ArrayList<Integer> population = new ArrayList<Integer>();
    public Screen(){
        setLayout(null);
        setFocusable(true);

		enterP = new JTextField();
		enterP.setBounds(10, 135, 150, 30);
		add(enterP);

		enterQ = new JTextField();
		enterQ.setBounds(10, 195, 150, 30);
		add(enterQ);

        enterPopSize = new JTextField();
        enterPopSize.setBounds(10, 75, 150, 30);
		add(enterPopSize);

        enterSampleNum = new JTextField();
        enterSampleNum.setBounds(230, 75, 150, 30);
		add(enterSampleNum);

        enterNatSelecVal = new JTextField();
        enterNatSelecVal.setBounds(470, 75, 150, 30);
		add(enterNatSelecVal);

        enter = new JButton();
		enter.setBounds(10, 15, 200, 30);
		enter.setText("sampling with replacement");
        enter.addActionListener(this);
		add(enter);

        natSelec = new JButton();
		natSelec.setBounds(470, 15, 140, 30);
		natSelec.setText("natural selection");
        natSelec.addActionListener(this);
		add(natSelec);

        noReplace = new JButton();
		noReplace.setBounds(230, 15, 220, 30);
		noReplace.setText("sampling without replacement");
        noReplace.addActionListener(this);
		add(noReplace);
    }

    @Override
	public Dimension getPreferredSize(){
		return new Dimension(1100,600);
	}

    @Override
	public void paintComponent(Graphics g){
        setLayout(null);
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1100, 600);
        g.setColor(Color.BLACK);
        g.drawString("Population Size:", 10, 66);
        g.drawString("Frequency of the Dominant Allele:",10, 126);
        g.drawString("Frequency of the Recessive Allele:",10, 186);
        g.drawString("Number of individuals you want to sample:", 230, 66);
        g.drawString("Frequency of individuals you want to kill:",470, 66 );
        g.setColor(new Color(100,100,100));
        g.fillRect(473, 126, 90, 25);
        g.setColor(new Color(44, 99, 52));
        if(selecRec == true){
            g.fillRect(473, 126, 30, 25);
        } 
        if(selecDom == true){
            g.fillRect(503, 126, 30, 25);
        }
        if(selecHet == true){
            g.fillRect(533, 126, 30, 25);
        }
        g.setColor(Color.BLACK);
        g.drawString("Click on the group(s) you natural selection to affect", 470, 120);
        g.drawString("aa", 480,141);
        g.drawString("AA", 510,141);
        g.drawString("Aa", 540,141);

        
        
        if(valsSet == true){
            //1 = dom, 2= rec
            //1 =dom, 2= rec, 3=het
            if(sampleType == "replace"){
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*population.size());
                    int rand2 = (int)(Math.random()*population.size()); 
                    while(rand1==rand2){
                        rand2 =(int)(Math.random()*population.size());
                    }
                    if(population.get(rand1) == 1 && population.get(rand2)==1){
                        randDomNum++;
                    } else if(population.get(rand1)==2 && population.get(rand2)==2){
                        randRecNum++;
                    } else{
                        randHetNum++;
                    }
                }
            } else if(sampleType == "noReplace"){
                System.out.println(population);
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*population.size());
                    int allele1 = population.remove(rand1);
                    int rand2 = (int)(Math.random()*population.size());
                    int allele2 = population.remove(rand2);
                    System.out.print(population.size()+ " ");
                    if(allele1 == 1 && allele2==1){
                        randDomNum++;
                    } else if(allele1==2 && allele2==2){
                        randRecNum++;
                    } else{
                        randHetNum++;
                    }
                }
            }
            g.drawString("Ratio of Homozygous Dominant Individuals: " + Double.toString(domRat),500, 50);
            g.drawString("Ratio of Homozygous Recessive Individuals: " + Double.toString(recRat),500, 75);
            g.drawString("Ratio of Heterozygous Individuals: " + Double.toString(hetRat),500, 100);
            g.drawString("Predicted Number of Homozygous Dominant Individuals: " + Double.toString(domNum),500, 125);
            g.drawString("Predicted Number of Homozygous Recessive Individuals: " + Double.toString(recNum),500, 150);
            g.drawString("Predicted Number of Heterozygous Individuals: " + Double.toString(hetNum),500, 175);
            g.drawString("Sampled Number of Homozygous Dominant Individuals out of " + sampleNum + ": " + Integer.toString(randDomNum),500, 200);
            g.drawString("Sampled Number of Homozygous Recessive Individuals " + sampleNum + ": " +Integer.toString(randRecNum),500, 225);
            g.drawString("Sampled of Heterozygous Individuals " + sampleNum + ": " + Integer.toString(randHetNum),500, 250);
        }
    }

    public void reproduce(){
        
    }

    public void actionPerformed(ActionEvent e ){
        if(e.getSource() == enter){
            p = Double.parseDouble(enterP.getText());
            q =  Double.parseDouble(enterQ.getText());
            popSize =  Double.parseDouble(enterPopSize.getText());
            sampleNum = Integer.parseInt(enterSampleNum.getText());
            for(int i=0; i<popSize*p;i++){
                population.add(1);
            }
            for(int i=0; i<popSize*q;i++){
                population.add(2);
            }
            domRat = p*p;
            recRat = q*q;
            hetRat = 2*p*q;
            domNum = domRat*popSize;
            recNum = recRat*popSize;
            hetNum = hetRat*popSize;
            randRecNum=0;
            randDomNum=0;
            randHetNum=0;
            valsSet = true;
            sampleType = "replace";
        } else if(e.getSource()==noReplace){
            p = Double.parseDouble(enterP.getText());
            q =  Double.parseDouble(enterQ.getText());
            popSize =  Double.parseDouble(enterPopSize.getText());
            sampleNum = Integer.parseInt(enterSampleNum.getText());
            for(int i=0; i<popSize*p*2;i++){
                population.add(1);
            }
            for(int i=0; i<popSize*q*2;i++){
                population.add(2);
            }
            domRat = p*p;
            recRat = q*q;
            hetRat = 2*p*q;
            domNum = domRat*popSize;
            recNum = recRat*popSize;
            hetNum = hetRat*popSize;
            randRecNum = 0;
            randDomNum=0;
            randHetNum=0;
            valsSet = true;
            sampleType = "noReplace";
        } else if (e.getSource == enterNatSelecVal()){

        }
       repaint();
    }  
}
