import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Screen extends JPanel implements ActionListener, MouseListener{
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
    private double domAllNum = 0;
    private double recAllNum = 0;
    private boolean valsSet = false;
    private boolean selecDom = false;
    private boolean selecRec = false;
    private boolean selecHet = false;
    private boolean useEnVals = true;
    private boolean justNatSelected = false;
    private boolean justScreenPressed = false;
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
    ArrayList<Integer> individuals = new ArrayList<Integer>();
    ArrayList<Integer> population2 = new ArrayList<Integer>();
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

        addMouseListener(this);
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
        g.setColor(new Color(62, 140, 73));
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

        g.setColor(new Color(100,100,100));
        g.fillRect(10, 250, 150, 25);
        g.setColor(new Color(62, 140, 73));
        if(useEnVals){
            g.fillRect(10, 250, 55,25);
        } else {
            g.fillRect(65, 250, 95,25);
        }
        g.setColor(Color.BLACK);
        g.drawString ("Select whether you want to sample with entered values or the new values after natural selection", 10, 245);
        g.drawString("entered", 16, 265);
        g.drawString("natural selection", 69, 265);
        
        if(valsSet == true){
            if(justScreenPressed == false){
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
                    for(int i=0; i<sampleNum; i++){
                        int rand1 = (int)(Math.random()*population.size());
                        int allele1 = population.remove(rand1);
                        int rand2 = (int)(Math.random()*population.size());
                        int allele2 = population.remove(rand2);
                        if(allele1 == 1 && allele2==1){
                            randDomNum++;
                        } else if(allele1==2 && allele2==2){
                            randRecNum++;
                        } else{
                            randHetNum++;
                        }
                    }
                }

                /*for(int i=0; i<popSize; i++){
                    int rand1 = (int)(Math.random()*population.size());
                    int rand2 = (int)(Math.random()*population.size()); 
                    while(rand1==rand2){
                        rand2 =(int)(Math.random()*population.size());
                    }
                    if(population.get(rand1) == 1 && population.get(rand2)==1){
                        individuals.add(1);
                    } else if(population.get(rand1)==2 && population.get(rand2)==2){
                        individuals.add(2);
                    } else{
                        individuals.add(3);
                    }
                }*/
            }
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(domAllNum),10, 0+300);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(recAllNum),10, 25+300);
            g.drawString("Ratio of Homozygous Dominant Individuals: " + Double.toString(domRat),10, 50+300);
            g.drawString("Ratio of Homozygous Recessive Individuals: " + Double.toString(recRat),10, 75+300);
            g.drawString("Ratio of Heterozygous Individuals: " + Double.toString(hetRat),10, 100+300);
            g.drawString("Predicted Number of Homozygous Dominant Individuals: " + Double.toString(domNum),10, 125+300);
            g.drawString("Predicted Number of Homozygous Recessive Individuals: " + Double.toString(recNum),10, 150+300);
            g.drawString("Predicted Number of Heterozygous Individuals: " + Double.toString(hetNum),10, 175+300);

            if(justNatSelected == false){
                g.drawString("Sampled Number of Homozygous Dominant Individuals out of " + sampleNum + ": " + Integer.toString(randDomNum),10, 200+300);
                g.drawString("Sampled Number of Homozygous Recessive Individuals " + sampleNum + ": " +Integer.toString(randRecNum),10, 225+300);
                g.drawString("Sampled of Heterozygous Individuals " + sampleNum + ": " + Integer.toString(randHetNum),10, 250+300);
            }
        }
    }

    public void reproduce(){
        
    }

    public void actionPerformed(ActionEvent e ){
        justScreenPressed = false;
        if(e.getSource() == enter){
            p = Double.parseDouble(enterP.getText());
            q =  Double.parseDouble(enterQ.getText());
            domAllNum = p;
            recAllNum = q;
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
            justNatSelected = false;
        } else if(e.getSource()==noReplace){
            p = Double.parseDouble(enterP.getText());
            q =  Double.parseDouble(enterQ.getText());
            domAllNum = p;
            recAllNum = q;
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
            justNatSelected = false;
        } else if (e.getSource() == natSelec){
            for(int i=0;i<domNum; i++){
                individuals.add(1);
            }
            for(int i=0;i<recNum; i++){
                individuals.add(2);
            }
            for(int i=0;i<hetNum; i++){
                individuals.add(3);
            }
            double selecRat = Double.parseDouble(enterNatSelecVal.getText());
            if(selecDom){
                int selecNum = (int)(selecRat*domNum);
                for(int i=0; i<individuals.size(); i++){
                    if(individuals.get(i).equals(1)&&selecNum>0){
                        individuals.remove(i);
                        i--;
                        selecNum--;
                    }
                }
            } 
            if (selecRec){
                int selecNum = (int)(selecRat*recNum);
                for(int i=0; i<individuals.size(); i++){
                    if(individuals.get(i).equals(2)&&selecNum>0){
                        individuals.remove(i);
                        i--;
                        selecNum--;
                    }
                }
            } 
            if (selecHet){
                int selecNum = (int)(selecRat*hetNum);
                for(int i=0; i<individuals.size(); i++){
                    if(individuals.get(i).equals(3)){
                        if(selecNum>0){
                            individuals.remove(i);
                            i--;
                            selecNum--;
                        }
                    }
                }
            }
            domNum = 0;
            recNum = 0;
            hetNum = 0;
            for(int i=0;i<individuals.size();i++){
                if(individuals.get(i).equals(1)){
                    population2.add(1);
                    population2.add(1);
                    domNum++;
                } else if(individuals.get(i).equals(2)){
                    population2.add(2);
                    population2.add(2);
                    recNum++;
                } else if(individuals.get(i).equals(3)){
                    population2.add(1);
                    population2.add(2);
                    hetNum++;
                }
            }
            double amt1 = 0;
            double amt2 = 0;
            for(int i=0; i<population2.size(); i++){
                if(population2.get(i).equals(1)){
                    amt1++;
                    
                } else{
                    amt2++;
                }
            }
            double p = amt1/population2.size();
            double q = amt2/population2.size();
            domAllNum = p;
            recAllNum = q;
            System.out.println(population2.size());
            domRat = 1.0*domNum/individuals.size();
            recRat = 1.0*recNum/individuals.size();
            hetRat = 1.0*hetNum/individuals.size();
            individuals = new ArrayList<Integer>();
            randRecNum=0;
            randDomNum=0;
            randHetNum=0;
            valsSet = true;
            justNatSelected = true;
        }
       repaint();
    }  

	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e){
       // System.out.println("hi")
        if(e.getY()>=126&&e.getY()<=151){
            if(e.getX()>=470&&e.getX()<=500){
                if(selecRec){
                    selecRec =false;
                } else {
                    selecRec = true;
                }
            } else if (e.getX()>=500&&e.getX()<=530){
                if(selecDom){
                    selecDom = false;
                }else {
                    selecDom = true;
                }
            } else if (e.getX()>=530 && e.getX()<=560){
                if(selecHet){
                    selecHet = false;
                } else{
                    selecHet = true;
                }
            }
        }

        if(e.getY()>=250&&e.getY()<=275){
            if(e.getX()>=10&&e.getX()<=65){
                useEnVals = true;
            } else if (e.getX()>=65&&e.getX()<=160){
                useEnVals = false;
            } 
        }
        justScreenPressed = true;
        repaint();
    }
}
