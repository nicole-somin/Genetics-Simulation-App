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
    //ratio of dominant vs reccessive genes
    private double p;
    private double q; 
    // ratio of homo recessive, homo dominint, and hetero individuals
    private double recRat;
    private double domRat;
    private double hetRat;
    //predicted number of reccessive, dominant, and hetero individuals
    private double recNum;
    private double domNum;
    private double hetNum;
    //total population size
    private double popSize;
    //number of recessive, dominant, and hetero individuals when sampled
    private int randDomNum = 0;
    private int randRecNum = 0;
    private int randHetNum = 0;
    //number inputted by the user that they want to sample
    private int sampleNum = 0;
    // says whether or not the values have been set (they are set either by clicking natural selection or doing one of the sampling types)
    private boolean valsSet = false;
    // says which of the types are selected for the natural selcetion category
    private boolean selecDom = false;
    private boolean selecRec = false;
    private boolean selecHet = false;
    //total of each type in the individuals
    private int totDom;
    private int totRec;
    private int totHet;
    //determines whether you are using the entered values (true), or the values from natural selection (false)
    private boolean useEnVals = true;
    //determines whether or not you just natural selected (for display purposes)
    private boolean showPart2 = false;
    private int startSpot=0;
    //makes sure that when you select from the menus it doesn't re-sample
    private JTextField enterP;
    private JTextField enterQ;
    private JTextField enterPopSize;
    private JTextField enterSampleNum;
    private JTextField enterNatSelecVal;
    private JButton natSelec;
    //samples with replacement
    private JButton enter;
    //entters
    private JButton noReplace;	
    //is set when you click on either the replacement or non-replacement button
    private JButton replace;
    private JButton reproduce;
    //individuals is created during natural selection & filled with individuals based on the current information in population (1-dom, 2-rec, 3-het)
    ArrayList<Individual> individuals = new ArrayList<Individual>();
    // individuals2 is created with individuals during  natural selection
    ArrayList<Individual> individuals2 = new ArrayList<Individual>();
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

        replace = new JButton();
		replace.setBounds(10, 15, 200, 30);
		replace.setText("sampling with replacement");
        replace.addActionListener(this);
		add(replace);

        enter = new JButton();
		enter.setBounds(750, 15, 200, 30);
		enter.setText("enter");
        enter.addActionListener(this);
		add(enter);

        reproduce = new JButton();
		reproduce.setBounds(750, 75, 200, 30);
		reproduce.setText("reproduce");
        reproduce.addActionListener(this);
		add(reproduce);

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
        g.drawString("Number of individuals you're sampling:", 230, 66);
        g.drawString("Frequency of individuals you want to kill:",470, 66 );
        // select menus
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

        // does the sampling
        if(valsSet == true){     
            //drawing on screen
            g.drawString("Population Size: " + String.valueOf(popSize), 10, 0+300);
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(p),10, 0+325);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(q),10, 25+325);
            g.drawString("Ratio of Homozygous Dominant Individuals: " + Double.toString(domRat),10, 50+325);
            g.drawString("Ratio of Homozygous Recessive Individuals: " + Double.toString(recRat),10, 75+325);
            g.drawString("Ratio of Heterozygous Individuals: " + Double.toString(hetRat),10, 100+325);

            if(showPart2 == true){
                g.drawString("Predicted Number of Homozygous Dominant Individuals: " + Double.toString((int)domNum),10, 125+325);
                g.drawString("Predicted Number of Homozygous Recessive Individuals: " + Double.toString((int)recNum),10, 150+325);
                g.drawString("Predicted Number of Heterozygous Individuals: " + Double.toString((int)hetNum),10, 175+325);
                g.drawString("Sampled Number of Homozygous Dominant Individuals out of " + sampleNum + ": " + Integer.toString(randDomNum),10, 200+325);
                g.drawString("Sampled Number of Homozygous Recessive Individuals " + sampleNum + ": " +Integer.toString(randRecNum),10, 225+325);
                g.drawString("Sampled of Heterozygous Individuals " + sampleNum + ": " + Integer.toString(randHetNum),10, 250+325);
            }
            
        }
    }

    public void reproduce(){
        ArrayList<Individual> tempArray;
        if(useEnVals==true){
            tempArray = new ArrayList<Individual>();
            for(int i=0;i<individuals.size();i++){
                tempArray.add(individuals.get(i));
            }
            if(individuals.size()%2==1){
                startSpot=1;
            }
        } else{
            tempArray = new ArrayList<Individual>();
            for(int i=0;i<individuals2.size();i++){
                tempArray.add(individuals2.get(i));
            }
            if(individuals2.size()%2==1){
                startSpot=1;
            }
        }
        for(int i=startSpot; i<tempArray.size();i+=2){
            if(tempArray.size()>1){
                int rand1 = (int)(Math.random()*tempArray.size());
                int rand2 = (int)(Math.random()*(tempArray.size()-1));
                while(rand1==rand2){
                    rand1 = (int)(Math.random()*tempArray.size());
                }
                if(useEnVals){
                    individuals.add(new Individual(tempArray.get(rand1).returnRandAl(),tempArray.get(rand2).returnRandAl()));
                } else{
                    individuals2.add(new Individual(tempArray.get(rand1).returnRandAl(),tempArray.get(rand2).returnRandAl()));
                }
                tempArray.remove(rand1);
                tempArray.remove(rand2);
                i-=2;
                System.out.println(tempArray.size());
            }
        }
        calculateVals();
        repaint();
    }

    public void calculateVals(){
        ArrayList<Individual> tempArray = new ArrayList<Individual>();
        if(useEnVals){
            tempArray = individuals;
        } else{
            tempArray = individuals2;
        }
        popSize = tempArray.size();
        totDom = 0;
        totRec = 0;
        totHet = 0;
        for(int i=0;i<tempArray.size();i++){
            if(tempArray.get(i).getType()==1){
                totDom++;
            } else if (tempArray.get(i).getType()==2){
                totRec++;
            } else{
                totHet++;
            }
        }
        domRat = (1.0*totDom)/popSize;
        recRat = (1.0*totRec)/popSize;
        hetRat = (1.0*totHet)/popSize;
        p = (2.0*totDom+totHet)/(popSize*2);
        q = (2.0*totRec+totHet)/(popSize*2);
    }

    public void actionPerformed(ActionEvent e ){
        if(e.getSource() == enter){
            if(useEnVals){
                p = Double.parseDouble(enterP.getText());
                q =  Double.parseDouble(enterQ.getText());
                popSize =  Double.parseDouble(enterPopSize.getText());
                //harvey weinburg equation
                domRat = p*p;
                recRat = q*q;
                hetRat = 2*p*q;
                totDom = (int)(domRat*popSize);
                totRec = (int)(recRat * popSize);
                totHet = (int)(hetRat * popSize);
                //creating population of alleles
                for(int i=0; i<totDom;i++){
                    individuals.add(new Individual(1,1));
                }
                for(int i=0; i<totHet;i++){
                    individuals.add(new Individual(1,2));
                }
                for(int i=0; i<totRec;i++){
                    individuals.add(new Individual(2,2));
                }
                //so that sampling will happen
                valsSet = true;
                showPart2 = false;
            } else{
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //predicting
                valsSet = false; 
                showPart2 = false;
            }
        } else if(e.getSource()==noReplace){
            sampleNum = Integer.parseInt(enterSampleNum.getText());
            //predicting
            domNum = domRat*sampleNum;
            recNum = recRat*sampleNum;
            hetNum = hetRat*sampleNum;
            //prepping for the sampling in paint component
            randRecNum=0;
            randDomNum=0;
            randHetNum=0;
            if(useEnVals){
                ArrayList<Individual> tempArray = new ArrayList<Individual>();
                for(int i=0;i<individuals.size();i++){
                    tempArray.add(individuals.get(i));
                }                
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*tempArray.size());
                    Individual indiv = tempArray.remove(rand1);
                    if(indiv.get1() == 1 && indiv.get2()==1){
                        randDomNum++;
                    } else if(indiv.get1()==2 && indiv.get2()==2){
                        randRecNum++;
                    } else{
                        randHetNum++;
                    }
                }
            //no replacement from natural selection
            } else{
                ArrayList<Individual> tempArray = new ArrayList<Individual>();
                for(int i=0;i<individuals2.size();i++){
                    tempArray.add(individuals2.get(i));
                }                
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*tempArray.size());
                    Individual indiv = tempArray.remove(rand1);
                    if(indiv.get1() == 1 && indiv.get2()==1){
                        randDomNum++;
                    } else if(indiv.get1()==2 && indiv.get2()==2){
                        randRecNum++;
                    } else{
                        randHetNum++;
                    }
                }
            }
            showPart2 = true;
        }  else if (e.getSource() == replace){
            sampleNum = Integer.parseInt(enterSampleNum.getText());
            //predicting
            domNum = domRat*sampleNum;
            recNum = recRat*sampleNum;
            hetNum = hetRat*sampleNum;
            //prepping for the sampling in paint component
            randRecNum=0;
            randDomNum=0;
            randHetNum=0;
            if(useEnVals){
                randDomNum=0;
                randRecNum=0;
                randHetNum=0;
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*individuals.size());
                    if(individuals.get(rand1).get1() == 1 && individuals.get(rand1).get2()==1){
                        randDomNum++;
                    } else if(individuals.get(rand1).get1()==2 && individuals.get(rand1).get2()==2){
                        randRecNum++;
                    } else{
                        randHetNum++;
                    }
                }
            // replacement type from natural selection
            } else{
                randDomNum=0;
                randRecNum=0;
                randHetNum=0;
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*individuals2.size());
                    if(individuals2.get(rand1).get1() == 1 && individuals2.get(rand1).get2()==1){
                        randDomNum++;
                    } else if(individuals2.get(rand1).get1()==2 && individuals2.get(rand1).get2()==2){
                        randRecNum++;
                    } else{
                        randHetNum++;
                    }
                }
            }
            showPart2=true;
        } else if (e.getSource() == natSelec){
            //determining the total number of each type of individual
            totDom = (int)(popSize*domRat);
            totHet = (int)(popSize*hetRat);
            totRec = (int)(popSize*recRat);
            double selecRat = Double.parseDouble(enterNatSelecVal.getText());
            if(selecDom){
                totDom = totDom - (int)(totDom*selecRat);
            } 
            if (selecHet){
                totHet = totHet - (int)(totHet*selecRat);
            }
            if(selecRec){
                totRec = totRec - (int)(totRec*selecRat);
            }
            popSize = totDom + totHet+ totRec;

            domRat = 1.0*totDom/popSize;
            recRat = 1.0*totRec/popSize;
            hetRat = 1.0*totHet/popSize;
            domNum = 0;
            recNum = 0;
            hetNum = 0;
            double amt1 = 0;
            double amt2 = 0;

            for(int i=0; i<totDom; i++){
                amt1+=2;
                individuals2.add(new Individual(1,1));
            } 
            for(int i=0; i<totHet; i++){
                amt1++;
                amt2++;
                individuals2.add(new Individual(1,2));
            }
            for(int i=0;i<totRec;i++){
                amt2+=2;
                individuals2.add(new Individual(2,2));
            }
            
            p = amt1/(popSize*2);
            q = amt2/(popSize*2);           
            randRecNum=0;
            randDomNum=0;
            randHetNum=0;
            valsSet = true;
            showPart2 = false;
            
        } else if (e.getSource()==reproduce){
            reproduce();
        }
       repaint();
    }  

	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e){
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
        repaint();
    }
}
