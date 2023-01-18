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
    //determines whether you are using the entered values (true), or the values from natural selection (false)
    private boolean useEnVals = true;
    //determines whether you are using the entered values (true), or the values from natural selection (false) & you sampled not just selected on of them
    private boolean showType1 = true;
    //determines whether or not you just natural selected (for display purposes)
    private boolean justNatSelected = false;
    //makes sure that when you select from the menus it doesn't re-sample
    private boolean justScreenPressed = false;
    private JTextField enterP;
    private JTextField enterQ;
    private JTextField enterPopSize;
    private JTextField enterSampleNum;
    private JTextField enterNatSelecVal;
    private JButton natSelec;
    //samples with replacement
    private JButton enter;
    //samples without replacement
    private JButton noReplace;	
    //is set when you click on either the replacement or non-replacement button
    private String sampleType = "";
    //added to when you use entered values & filled with alleles (1- dominant, 2-recessive)
    ArrayList<Integer> population = new ArrayList<Integer>();
    //individuals is created during natural selection & filled with individuals based on the current information in population (1-dom, 2-rec, 3-het)
    ArrayList<Integer> individuals = new ArrayList<Integer>();
    // population2 is filled with alleles during  natural selection
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
            if(justScreenPressed == false){
                //1 = dom, 2= rec
                //1 =dom, 2= rec, 3=het
                // replacement type from entered values
                if(sampleType == "replace"){
                    if(showType1){
                        randDomNum=0;
                        randRecNum=0;
                        randHetNum=0;
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
                    // replacement type from natural selection
                    } else{
                        randDomNum=0;
                        randRecNum=0;
                        randHetNum=0;
                        for(int i=0;i<sampleNum;i++){
                            int rand1 = (int)(Math.random()*population2.size());
                            int rand2 = (int)(Math.random()*population2.size()); 
                            while(rand1==rand2){
                                rand2 =(int)(Math.random()*population2.size());
                            }
                            if(population2.get(rand1) == 1 && population2.get(rand2)==1){
                                randDomNum++;
                            } else if(population2.get(rand1)==2 && population2.get(rand2)==2){
                                randRecNum++;
                            } else{
                                randHetNum++;
                            }
                        }
                    }
                //no replacement from entered values
                } else if(sampleType == "noReplace"){
                    if(showType1){
                        randDomNum=0;
                        randRecNum=0;
                        randHetNum=0;
                        ArrayList<Integer> tempPop = population;
                        for(int i=0; i<sampleNum; i++){
                            int rand1 = (int)(Math.random()*tempPop.size());
                            int allele1 = tempPop.remove(rand1);
                            int rand2 = (int)(Math.random()*tempPop.size());
                            int allele2 = tempPop.remove(rand2);
                            if(allele1 == 1 && allele2==1){
                                randDomNum++;
                            } else if(allele1==2 && allele2==2){
                                randRecNum++;
                            } else{
                                randHetNum++;
                            }
                        }
                    //no replacement from natural selection
                    } else{
                        
                        randDomNum=0;
                        randRecNum=0;
                        randHetNum=0;
                        ArrayList<Integer> tempPop = population2;
                        System.out.println(tempPop + " " + tempPop.size()+" " + sampleNum);
                        for(int i=0; i<sampleNum; i++){
                            int rand1 = (int)(Math.random()*tempPop.size());
                            int allele1 = tempPop.remove(rand1);
                            int rand2 = (int)(Math.random()*tempPop.size());
                            int allele2 = tempPop.remove(rand2);
                            if(allele1 == 1 && allele2==1){
                                randDomNum++;
                            } else if(allele1==2 && allele2==2){
                                randRecNum++;
                            } else{
                                randHetNum++;
                            }
                        }
                    }
                }

            }
            
            //drawing on screen
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(p),10, 0+300);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(q),10, 25+300);
            g.drawString("Ratio of Homozygous Dominant Individuals: " + Double.toString(domRat),10, 50+300);
            g.drawString("Ratio of Homozygous Recessive Individuals: " + Double.toString(recRat),10, 75+300);
            g.drawString("Ratio of Heterozygous Individuals: " + Double.toString(hetRat),10, 100+300);

            if(justNatSelected == false){
                g.drawString("Predicted Number of Homozygous Dominant Individuals: " + Double.toString((int)domNum),10, 125+300);
                g.drawString("Predicted Number of Homozygous Recessive Individuals: " + Double.toString((int)recNum),10, 150+300);
                g.drawString("Predicted Number of Heterozygous Individuals: " + Double.toString((int)hetNum),10, 175+300);
                g.drawString("Sampled Number of Homozygous Dominant Individuals out of " + sampleNum + ": " + Integer.toString(randDomNum),10, 200+300);
                g.drawString("Sampled Number of Homozygous Recessive Individuals " + sampleNum + ": " +Integer.toString(randRecNum),10, 225+300);
                g.drawString("Sampled of Heterozygous Individuals " + sampleNum + ": " + Integer.toString(randHetNum),10, 250+300);
            }
            
        }
    }

    public void actionPerformed(ActionEvent e ){
        justScreenPressed = false;
        if(e.getSource() == enter){
            if(useEnVals){
                p = Double.parseDouble(enterP.getText());
                q =  Double.parseDouble(enterQ.getText());
                popSize =  Double.parseDouble(enterPopSize.getText());
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //creating population of alleles
                for(int i=0; i<popSize*p;i++){
                    population.add(1);
                }
                for(int i=0; i<popSize*q;i++){
                    population.add(2);
                }
                //harvey weinburg equation
                domRat = p*p;
                recRat = q*q;
                hetRat = 2*p*q;
                //predicting
                domNum = domRat*sampleNum;
                recNum = recRat*sampleNum;
                hetNum = hetRat*sampleNum;
                //prepping for the sampling in paint component
                randRecNum=0;
                randDomNum=0;
                randHetNum=0;
                //so that sampling will happen
                valsSet = true;
                sampleType = "replace";
                justNatSelected = false;
                showType1 = true;
            } else{
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //predicting
                hetNum = sampleNum*hetRat;
                recNum = sampleNum*recRat;
                domNum = sampleNum*domRat;
                justNatSelected = false;
                sampleType = "replace";
                valsSet = true; 
                showType1 = false;
            }
        } else if(e.getSource()==noReplace){
            if(useEnVals){
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
                domNum = domRat*sampleNum;
                recNum = recRat*sampleNum;
                hetNum = hetRat*sampleNum;
                randRecNum = 0;
                randDomNum=0;
                randHetNum=0;
                valsSet = true;
                sampleType = "noReplace";
                justNatSelected = false;
                showType1=true;
            }else{
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                hetNum = sampleNum*hetRat;
                recNum =sampleNum*recRat;
                domNum = sampleNum*domRat;
                justNatSelected = false;
                sampleType = "noReplace";
                valsSet = true; 
                showType1 = false;
            }
        } else if (e.getSource() == natSelec){
            //determining the total number of each type of individual
            int totDom = (int)(popSize*domRat);
            int totHet = (int)(popSize*hetRat);
            int totRec = (int)(popSize*recRat);
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
            recRat = 1.0*totHet/popSize;
            hetRat = 1.0*totRec/popSize;
            domNum = 0;
            recNum = 0;
            hetNum = 0;
            double amt1 = 0;
            double amt2 = 0;

            for(int i=0; i<totDom; i++){
                amt1+=2;
                population2.add(1);
                population2.add(1);
            } 
            for(int i=0; i<totHet; i++){
                amt1++;
                amt2++;
                population2.add(1);
                population2.add(2);
            }
            for(int i=0;i<totRec;i++){
                amt2+=2;
                population2.add(2);
                population2.add(2);
            }
            
            p = amt1/(popSize*2);
            q = amt2/(popSize*2);           
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
