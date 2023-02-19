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
import javax.swing.SwingConstants;


public class Screen extends JPanel implements ActionListener, MouseListener{
    //ratio of dominant vs reccessive genes
    private double p;
    private double q;
    private double p2;
    private double q2;
    // ratio of homo recessive, homo dominint, and hetero individuals
    private double recRat;
    private double domRat;
    private double hetRat;
    private double recRat2;
    private double domRat2;
    private double hetRat2;
    //predicted number of reccessive, dominant, and hetero individuals
    private double recNum;
    private double domNum;
    private double hetNum;
    private double recNum2;
    private double domNum2;
    private double hetNum2;
    //total population size
    private int popSize;
    private int popSize2;
    //number of recessive, dominant, and hetero individuals when sampled
    private int randDomNum = 0;
    private int randRecNum = 0;
    private int randHetNum = 0;
    private int randDomNum2 = 0;
    private int randRecNum2 = 0;
    private int randHetNum2 = 0;
    //number inputted by the user that they want to sample
    private int sampleNum = 0;
    //x&y vals for the natural selection menu
    private int selec1X = 15;
    private int selec1Y = 333;
    //x&y vals for the population selection menu
    private int selec3X = 277;
    private int selec3Y = 70;
    // says whether or not the values have been set (they are set either by clicking natural selection or doing one of the sampling types)
    private boolean valsSet = false;
    private boolean valsSet2 = false;
    // says which of the types are selected for the natural selcetion category
    private boolean selecDom = false;
    private boolean selecRec = false;
    private boolean selecHet = false;
    private boolean selecDom2 = false;
    private boolean selecRec2 = false;
    private boolean selecHet2 = false;
    //says if we're using pop1 or pop2
    private boolean usePop1 = true;
    private boolean usePop2 = false;
    //total of each type in the individuals
    private int totDom;
    private int totRec;
    private int totHet;
    private int totDom2;
    private int totRec2;
    private int totHet2;
    //determines whether or not you just natural selected (for display purposes)
    private boolean showPart2 = false;
    private boolean showPart22 = false;
    private boolean showBadEntryMessage = false;
    private int numOfPops = 0;
    //makes sure that when you select from the menus it doesn't re-sample
    private JTextField enterP;
    private JTextField enterPopSize;
    private JTextField enterSampleNum;
    private JTextField enterNatSelecVal;
    private JTextField numFlow12;
    private JTextField numFlow21;
    //samples with replacement
    private JButton noReplace;  
    //is set when you click on either the replacement or non-replacement button
    private JButton replace;
    private JButton geneFlow;
    private JButton addPop;
    private JButton natSelec;
    private JButton deletePop1;
    private JButton deletePop2;
    //individuals is created during natural selection & filled with individuals based on the current information in population (1-dom, 2-rec, 3-het)
    ArrayList<Individual> individuals = new ArrayList<Individual>();
    ArrayList<Individual> individuals2 = new ArrayList<Individual>();
    public Screen(){
        setLayout(null);
        setFocusable(true);
        enterP = new JTextField();
        enterP.setFont(new Font("Arial", Font.PLAIN, 20));
        enterP.setHorizontalAlignment(SwingConstants.LEFT);
        enterP.setBounds(15, 115, 100, 30);
        enterP.setText("");
        this.add(enterP);


        enterPopSize = new JTextField();
        enterPopSize.setFont(new Font("Arial", Font.PLAIN, 20));
        enterPopSize.setHorizontalAlignment(SwingConstants.LEFT);
        enterPopSize.setBounds(15, 40, 100, 30);
        enterPopSize.setText("");
        this.add(enterPopSize);


        enterSampleNum = new JTextField();
        enterSampleNum.setFont(new Font("Arial", Font.PLAIN, 20));
        enterSampleNum.setHorizontalAlignment(SwingConstants.LEFT);
        enterSampleNum.setBounds(275, 420-250, 100, 30);
        enterSampleNum.setText("");
        this.add(enterSampleNum);


        enterNatSelecVal = new JTextField();
        enterNatSelecVal.setFont(new Font("Arial", Font.PLAIN, 20));
        enterNatSelecVal.setHorizontalAlignment(SwingConstants.LEFT);
        enterNatSelecVal.setBounds(15, 255, 100, 30);
        enterNatSelecVal.setText("");
        this.add(enterNatSelecVal);


        numFlow12 = new JTextField();
        numFlow12.setFont(new Font("Arial", Font.PLAIN, 20));
        numFlow12.setHorizontalAlignment(SwingConstants.LEFT);
        numFlow12.setBounds(15, 465, 100, 30);
        numFlow12.setText("");
        this.add(numFlow12);


        numFlow21 = new JTextField();
        numFlow21.setFont(new Font("Arial", Font.PLAIN, 20));
        numFlow21.setHorizontalAlignment(SwingConstants.LEFT);
        numFlow21.setBounds(15, 540, 100, 30);
        numFlow21.setText("");
        this.add(numFlow21);


        replace = new JButton();
        replace.setFont(new Font("Arial", Font.PLAIN, 15));
        replace.setHorizontalAlignment(SwingConstants.CENTER);
        replace.setBounds(275, 210, 220, 30);
        replace.setText("Sample with Replacement");
        this.add(replace);
        replace.addActionListener(this);


        geneFlow = new JButton();
        geneFlow.setFont(new Font("Arial", Font.PLAIN, 15));
        geneFlow.setHorizontalAlignment(SwingConstants.CENTER);
        geneFlow.setBounds(15, 580, 120, 30);
        geneFlow.setText("Gene Flow");
        this.add(geneFlow);
        geneFlow.addActionListener(this);

        deletePop1 = new JButton();
        deletePop1.setFont(new Font("Arial", Font.PLAIN, 15));
        deletePop1.setHorizontalAlignment(SwingConstants.CENTER);
        deletePop1.setBounds(545, 220, 120, 30);
        deletePop1.setText("Delete pop1");
        this.add(deletePop1);
        deletePop1.addActionListener(this);
        deletePop1.setVisible(false);

        deletePop2 = new JButton();
        deletePop2.setFont(new Font("Arial", Font.PLAIN, 15));
        deletePop2.setHorizontalAlignment(SwingConstants.CENTER);
        deletePop2.setBounds(890, 220, 120, 30);
        deletePop2.setText("Delete pop2");
        this.add(deletePop2);
        deletePop2.addActionListener(this);
        deletePop2.setVisible(false);

        natSelec = new JButton();
        natSelec.setFont(new Font("Arial", Font.PLAIN, 15));
        natSelec.setHorizontalAlignment(SwingConstants.CENTER);
        natSelec.setBounds(15, 375, 150, 30);
        natSelec.setText("Natural Selection");
        this.add(natSelec);
        natSelec.addActionListener(this);


        noReplace = new JButton();
        noReplace.setFont(new Font("Arial", Font.PLAIN, 15));
        noReplace.setHorizontalAlignment(SwingConstants.CENTER);
        noReplace.setBounds(275, 250, 240, 30);
        noReplace.setText("Sample without Replacement");
        this.add(noReplace);
        noReplace.addActionListener(this);


        addPop = new JButton();
        addPop.setFont(new Font("Arial", Font.PLAIN, 15));
        addPop.setHorizontalAlignment(SwingConstants.CENTER);
        addPop.setBounds(15, 155, 150, 30);
        addPop.setText("Add Population");
        this.add(addPop);
        addPop.addActionListener(this);




        addMouseListener(this);
    }


    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1300,650);
    }


    @Override
    public void paintComponent(Graphics g){
        setLayout(null);
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 1500, 650);
        g.setColor(Color.BLACK);
       
        g.drawString("Population Size:", 15, 30);
        g.drawString("Frequency of the Dominant Allele",15, 95);
        g.drawString("(0<x<1)",15, 110);
        g.drawString("Frequency of Natural Selection:", 15, 235);
        g.drawString("(0<x<1)",15, 250);
        g.drawString("Click on the group(s) you want",15, 310);
        g.drawString("natural selection to affect",15, 325);
        g.drawString("Frequency of individuals to transfer", 15,445);
        g.drawString("from pop1 to pop2 (0<x<1)", 15,460 );
        g.drawString("Frequency of individuals to transfer",15,520);
        g.drawString("from pop2 to pop1 (0<x<1)", 15,535);
        g.drawString("Click on the population(s) you", 277 ,35);
        g.drawString("you want to use",277, 50);
        g.drawString("Number of individuals", 277,395-250);
        g.drawString("you're sampling", 277,410-250);
       


        g.setColor(Color.RED);
        if(showBadEntryMessage){
            g.drawString("Please enter a number greater than 0 and less than 1", 5, 190);
        }
        g.setColor(Color.BLACK);
       
        // select menus
        g.setColor(new Color(100,100,100));
        g.fillRect(selec1X, selec1Y, 90, 25);
        g.setColor(new Color(62, 140, 73));
        if(selecRec == true){
            g.fillRect(selec1X, selec1Y, 30, 25);
        }
        if(selecDom == true){
            g.fillRect(selec1X+30, selec1Y, 30, 25);
        }
        if(selecHet == true){
            g.fillRect(selec1X+60, selec1Y, 30, 25);
        }
   
        g.setColor(Color.BLACK);
        g.drawString("aa", selec1X+5,selec1Y+16); //480,141
        g.drawString("AA", selec1X+35,selec1Y+16);
        g.drawString("Aa", selec1X+65,selec1Y+16);       
        //2nd one
        g.setColor(new Color(100,100,100));
        g.fillRect(selec3X, selec3Y, 200, 25);
        g.setColor(new Color(62, 140, 73));
        if(usePop1 == true){
            g.fillRect(selec3X, selec3Y, 100, 25);
        }
        if(usePop2 == true){
            g.fillRect(selec3X+100, selec3Y, 100, 25);
        }
        g.setColor(Color.BLACK);
        g.drawString("population 1", selec3X+5,selec3Y+16);//15, 267-25
        g.drawString("population 2", selec3X+115,selec3Y+16);
       
        // does the sampling
        if(valsSet == true){    
            //drawing on screen
            g.drawString("Popuation 1: ", 545, 25);
            g.drawString("Population Size: " + String.valueOf(popSize), 545, 50);
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(p),545, 75);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(q),545, 100);

            if(showPart2 == true){
                g.drawString("Predicted Number of Dominant Alleles: " + Double.toString((int)domNum),545, 125);
                g.drawString("Predicted Number of Recessive Alleles: " + Double.toString((int)recNum),545, 150);
                g.drawString("Sampled Number of Dominant Alleles out of " + individuals.size() + ": " + Integer.toString(randDomNum),545, 175);
                g.drawString("Sampled Number of Recessive Alleles out of " + individuals.size() + ": " +Integer.toString(randRecNum),545, 200);
            }
        }


        if(valsSet2 == true){    
            //drawing on screen
            g.drawString("Popuation 2: ", 890, 25);
            g.drawString("Population Size: " + String.valueOf(popSize2), 890, 50);
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(p2),890, 75);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(q2),890, 100);


            if(showPart22 == true){
                g.drawString("Predicted Number of Dominant Alleles: " + Double.toString((int)domNum2),890, 125);
                g.drawString("Predicted Number of Recessive Alleles: " + Double.toString((int)recNum2),890, 150);
                g.drawString("Sampled Number of Dominant Alleles out of " + individuals.size() + ": " + Integer.toString(randDomNum2),890, 175);
                g.drawString("Sampled Number of Recessive Alleles out of " + individuals.size() + ": " +Integer.toString(randRecNum2),890, 200);
            }
        }
           
    }



    public void calculateVals(){
        ArrayList<Individual> tempArray = new ArrayList<Individual>();
        tempArray = individuals;
       
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
    public void calculateVals2(){
        ArrayList<Individual> tempArray = new ArrayList<Individual>();
        tempArray = individuals2;
   
        popSize2 = tempArray.size();
        totDom2 = 0;
        totRec2 = 0;
        totHet2 = 0;
        for(int i=0;i<tempArray.size();i++){
            if(tempArray.get(i).getType()==1){
                totDom2++;
            } else if (tempArray.get(i).getType()==2){
                totRec2++;
            } else{
                totHet2++;
            }
        }
        domRat2 = (1.0*totDom2)/popSize2;
        recRat2 = (1.0*totRec2)/popSize2;
        hetRat2 = (1.0*totHet2)/popSize2;
        p2 = (2.0*totDom2+totHet2)/(popSize2*2);
        q2 = (2.0*totRec2+totHet2)/(popSize2*2);
    }


    public void actionPerformed(ActionEvent e ){
        showBadEntryMessage = false;
        if(e.getSource()==noReplace){
            if (usePop1){
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //predicting
                domNum = domRat*sampleNum;
                recNum = recRat*sampleNum;
                hetNum = hetRat*sampleNum;
                //prepping for the sampling in paint component
                randRecNum=0;
                randDomNum=0;
                randHetNum=0;
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
                showPart2 = true;
            }
            if (usePop2){
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //predicting
                domNum2 = domRat2*sampleNum;
                recNum2 = recRat2*sampleNum;
                hetNum2 = hetRat2*sampleNum;
                //prepping for the sampling in paint component
                randRecNum2=0;
                randDomNum2=0;
                randHetNum2=0;
                ArrayList<Individual> tempArray = new ArrayList<Individual>();
                for(int i=0;i<individuals2.size();i++){
                    tempArray.add(individuals2.get(i));
                }                
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*tempArray.size());
                    Individual indiv = tempArray.remove(rand1);
                    if(indiv.get1() == 1 && indiv.get2()==1){
                        randDomNum2++;
                    } else if(indiv.get1()==2 && indiv.get2()==2){
                        randRecNum2++;
                    } else{
                        randHetNum2++;
                    }
                }
                showPart22 = true;
            }
           
        }  else if (e.getSource() == replace){
            if (usePop1){
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //predicting
                domNum = domRat*sampleNum;
                recNum = recRat*sampleNum;
                hetNum = hetRat*sampleNum;
                //prepping for the sampling in paint component
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
                showPart2=true;
            }
            if (usePop2){
                sampleNum = Integer.parseInt(enterSampleNum.getText());
                //predicting
                domNum2 = domRat2*sampleNum;
                recNum2 = recRat2*sampleNum;
                hetNum2 = hetRat2*sampleNum;
                //prepping for the sampling in paint component
                randDomNum2=0;
                randRecNum2=0;
                randHetNum2=0;
                for(int i=0; i<sampleNum; i++){
                    int rand1 = (int)(Math.random()*individuals2.size());
                    if(individuals2.get(rand1).get1() == 1 && individuals2.get(rand1).get2()==1){
                        randDomNum2++;
                    } else if(individuals2.get(rand1).get1()==2 && individuals2.get(rand1).get2()==2){
                        randRecNum2++;
                    } else{
                        randHetNum2++;
                    }
                }
                // replacement type from natural selection
                showPart22=true;
            }
        } else if (e.getSource() == natSelec){
            if (usePop1){
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
                individuals = new ArrayList<Individual>();


                for(int i=0; i<totDom; i++){
                    amt1+=2;
                    individuals.add(new Individual(1,1));
                }
                for(int i=0; i<totHet; i++){
                    amt1++;
                    amt2++;
                    individuals.add(new Individual(1,2));
                }
                for(int i=0;i<totRec;i++){
                    amt2+=2;
                    individuals.add(new Individual(2,2));
                }
               
                p = amt1/(popSize*2);
                q = amt2/(popSize*2);          
                randRecNum=0;
                randDomNum=0;
                randHetNum=0;
                valsSet = true;
                showPart2 = false;
            }
            if (usePop2){
               //determining the total number of each type of individual
               totDom2 = (int)(popSize2*domRat2);
               totHet2 = (int)(popSize2*hetRat2);
               totRec2 = (int)(popSize2*recRat2);
               double selecRat = Double.parseDouble(enterNatSelecVal.getText());
               if(selecDom2){
                   totDom2 = totDom2 - (int)(totDom2*selecRat);
                }
               if (selecHet2){
                   totHet2 = totHet2 - (int)(totHet2*selecRat);
               }
               if(selecRec2){
                   totRec2 = totRec2 - (int)(totRec2*selecRat);
               }
               popSize2 = totDom2 + totHet2+ totRec2;




               domRat2 = 1.0*totDom2/popSize2;
               recRat2 = 1.0*totRec2/popSize2;
               hetRat2 = 1.0*totHet2/popSize2;
               domNum2 = 0;
               recNum2 = 0;
               hetNum2 = 0;
               double amt1 = 0;
               double amt2 = 0;
               individuals2 = new ArrayList<Individual>();


               for(int i=0; i<totDom2; i++){
                   amt1+=2;
                   individuals2.add(new Individual(1,1));
               }
               for(int i=0; i<totHet2; i++){
                   amt1++;
                   amt2++;
                   individuals2.add(new Individual(1,2));
               }
               for(int i=0;i<totRec2;i++){
                   amt2+=2;
                   individuals2.add(new Individual(2,2));
               }
               
               p2 = amt1/(popSize2*2);
               q2 = amt2/(popSize2*2);          
               randRecNum2=0;
               randDomNum2=0;
               randHetNum2=0;
               valsSet2 = true;
               showPart22 = false;
            }
           
           
        } else if (e.getSource()==geneFlow){
        }
        else if (e.getSource() == addPop){
            if(numOfPops==0){
                individuals = new ArrayList<Individual>();
                p = Double.parseDouble(enterP.getText());
                q = (1- Double.parseDouble(enterP.getText()));
                if(p<0||p>1){
                    showBadEntryMessage = true;
                } else{
                    popSize =  Integer.parseInt(enterPopSize.getText());
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
                    numOfPops++;
                    deletePop1.setVisible(true);
                }
            } else if (numOfPops==1){
                individuals2 = new ArrayList<Individual>();
                p2 = Double.parseDouble(enterP.getText());
                q2 =  (1-Double.parseDouble(enterP.getText()));
                if(p<0||p>1){
                    showBadEntryMessage = true;
                } else{
                    popSize2 =  Integer.parseInt(enterPopSize.getText());
                    //harvey weinburg equation
                    domRat2 = p2*p2;
                    recRat2 = q2*q2;
                    hetRat2 = 2*p2*q2;
                    totDom2 = (int)(domRat2*popSize2);
                    totRec2 = (int)(recRat2 * popSize2);
                    totHet2 = (int)(hetRat2 * popSize2);
                    //creating population of alleles
                    for(int i=0; i<totDom2;i++){
                        individuals2.add(new Individual(1,1));
                    }
                    for(int i=0; i<totHet2;i++){
                        individuals2.add(new Individual(1,2));
                    }
                    for(int i=0; i<totRec2;i++){
                        individuals2.add(new Individual(2,2));
                    }
                    valsSet2 = true;
                    showPart2 = false;
                    numOfPops++;
                    addPop.setVisible(false);
                    deletePop2.setVisible(true);
                }
            } else{
                addPop.setVisible(false);
            }
        } else if (e.getSource()==deletePop1){
            if(numOfPops==1){
                for(int i=0;i<individuals.size();i++){
                    individuals.remove(i);
                }
                deletePop1.setVisible(false);
                numOfPops--;
                valsSet = false;
                addPop.setVisible(true);
            } else if (numOfPops==2){
                for(int i=0;i<individuals2.size();i++){
                    individuals.add(individuals2.get(i));
                }
                for(int i=0;i<individuals2.size();i++){
                    individuals2.remove(i);
                }
                numOfPops--;
                deletePop2.setVisible(false);
                valsSet2 = false;
                popSize = popSize2;
                domNum = domNum2;
                recNum = recNum2;
                p=p2;
                q=q2;
                addPop.setVisible(true);
            }
        } else if (e.getSource()==deletePop2){
            for(int i=0;i<individuals2.size();i++){
                individuals2.remove(i);
            }
            deletePop2.setVisible(false);
            numOfPops--;
            valsSet2 = false;
            addPop.setVisible(true);
        }
       repaint();
    }  


    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e){
        if(e.getY()>=selec1Y&&e.getY()<=selec1Y+30){
            if(e.getX()>=selec1X&&e.getX()<=selec1X+30){
                if(selecRec){
                    selecRec =false;
                } else {
                    selecRec = true;
                }


                if(selecRec2){
                    selecRec2 =false;
                } else {
                    selecRec2 = true;
                }
            } else if (e.getX()>=selec1X+30&&e.getX()<=selec1X+60){
                if(selecDom){
                    selecDom = false;
                }else {
                    selecDom = true;
                }


                if(selecDom2){
                    selecDom2 = false;
                }else {
                    selecDom2 = true;
                }
            } else if (e.getX()>=selec1X+60 && e.getX()<=selec1X+90){
                if(selecHet){
                    selecHet = false;
                } else{
                    selecHet = true;
                }


                if(selecHet2){
                    selecHet2 = false;
                } else{
                    selecHet2 = true;
                }
            }
        }
        if(e.getY()>=selec3Y&&e.getY()<=selec3Y+30){
            if(e.getX()>=selec3X&&e.getX()<=selec3X+100){
                if(usePop1){
                    usePop1 =false;
                } else {
                    usePop1 = true;
                }
            } else if (e.getX()>=selec3X+100&&e.getX()<=selec3X+200){
                if(usePop2){
                    usePop2 = false;
                }else {
                    usePop2 = true;
                }
            }
        }
        repaint();
    }


/*
    public void reproduce(){
        if(selecGeneFlow==true&&usePop1==true&&usePop2==true){
            int numTransfer1 = (int)(Double.parseDouble(numFlow12.getText())*individuals.size());
            int numTransfer2 = (int)(Double.parseDouble(numFlow21.getText())*individuals2.size());
            System.out.println(numTransfer1);
            System.out.println(numTransfer2);
            while(numTransfer1>0&&numTransfer2>0){
                int randoNumber = (int)(Math.random()*individuals.size());
                int randoNumber2 = (int)(Math.random()*individuals2.size());
                individuals2.add(individuals.remove(randoNumber));
                individuals.add(individuals2.remove(randoNumber2));
                numTransfer1--;
                numTransfer2--;
            }
            if(numTransfer1>0){
                for(int i=0;i<numTransfer1;i++){
                    int randoNumber = (int)(Math.random()*individuals.size());
                    individuals2.add(individuals.remove(randoNumber));
                }
            } else if (numTransfer2>0){
                for(int i=0;i<numTransfer2;i++){
                    int randoNumber = (int)(Math.random()*individuals2.size());
                    individuals.add(individuals2.remove(randoNumber));
                }
            }
            System.out.println(individuals.size());
            System.out.println(individuals2.size());
        }
        if (usePop1){
            startSpot = 0;
            ArrayList<Individual> tempArray;
            tempArray = new ArrayList<Individual>();
            for(int i=0;i<individuals.size();i++){
                tempArray.add(individuals.get(i));
            }
            if(tempArray.size()%2==1){
                startSpot=1;
            }
           
            for(int i=startSpot; i<tempArray.size();i+=2){
                if(tempArray.size()>1){
                    int rand1 = (int)(Math.random()*tempArray.size());
                    int rand2 = (int)(Math.random()*(tempArray.size()-1));
                    while(rand1==rand2){
                        rand1 = (int)(Math.random()*tempArray.size());
                    }
                    individuals.add(new Individual(tempArray.get(rand1).returnRandAl(),tempArray.get(rand2).returnRandAl()));
                   
                    tempArray.remove(rand1);
                    tempArray.remove(rand2);
                    i-=2;
                }
            }
            calculateVals();
        }
        if (usePop2){
            startSpot = 0;
            ArrayList<Individual> tempArray;
            tempArray = new ArrayList<Individual>();
            for(int i=0;i<individuals2.size();i++){
                tempArray.add(individuals2.get(i));
            }
            if(individuals2.size()%2==1){
                startSpot=1;
            }
           
            for(int i=startSpot; i<tempArray.size();i+=2){
                if(tempArray.size()>1){
                    int rand1 = (int)(Math.random()*tempArray.size());
                    int rand2 = (int)(Math.random()*(tempArray.size()-1));
                    while(rand1==rand2){
                        rand1 = (int)(Math.random()*tempArray.size());
                    }
                    individuals2.add(new Individual(tempArray.get(rand1).returnRandAl(),tempArray.get(rand2).returnRandAl()));
                   
                    tempArray.remove(rand1);
                    tempArray.remove(rand2);
                    i-=2;
                }
            }
            calculateVals2();
        }
        repaint();
    }
    */
}
