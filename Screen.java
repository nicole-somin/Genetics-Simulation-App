import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
/* 
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;*/

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.SwingConstants;
/*import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;*/
import javax.swing.ImageIcon;

public class Screen extends JPanel implements ActionListener, MouseListener{
    //ratio of dominant vs reccessive genes
    private double p;
    private double q;
    private double p2;
    private double q2;
    //predicted number of reccessive, dominant, and hetero individuals
    private int recNum;
    private int domNum;
    private int recNum2;
    private int domNum2;
    //total population size
    private int popSize;
    private int popSize2;
    //number of recessive, dominant, and hetero individuals when sampled
    private int randDomNum = 0;
    private int randRecNum = 0;
    private int randDomNum2 = 0;
    private int randRecNum2 = 0;
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
    private boolean drawRect1 = false;
    private boolean drawRect2 = false;
    private boolean graphView = true;
    //total of each type in the individuals
    private int totDom;
    private int totRec;
    private int totDom2;
    private int totRec2;
    private int generation;
    //determines whether or not you just natural selected (for display purposes)
    private boolean showPart2 = false;
    private boolean showPart22 = false;
    private boolean showBadEntryMessage = false;
    private boolean showNotEnoughPopsMessage = false;
    private boolean addToArray = true;
    private boolean wrongDataNull = false;
    private boolean wrongDataSize = false;
    private boolean wrongDataNullNatSelec = false;
    private boolean wrongDataSizeNatSelec = false;
    private boolean entryNullGeneFlow = false;
    private boolean showBadEntryMessageGeneFlow = false;
    private boolean wrongDataNullPop = false;
    private boolean wrongDataSizePop = false;
    private boolean wrongDataNullP = false;
    private boolean noPopSelec = false;
    private boolean noTypeSelec = false;
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
    private JButton switchView;
    //individuals is created during natural selection & filled with integers based on the current information in population (1-dom, 2-rec)
    ArrayList<Integer> individuals = new ArrayList<Integer>();
    ArrayList<Integer> individuals2 = new ArrayList<Integer>();
    //graph arrays
    int[] domArray = new int[46];
    int[] recArray = new int[46];
    int[] domArray2 = new int[46];
    int[] recArray2 = new int[46];

    ImageIcon img;

    public Screen(){
        setLayout(null);
        setFocusable(true);
        enterP = new JTextField();
        enterP.setFont(new Font("Arial", Font.PLAIN, 15));
        enterP.setHorizontalAlignment(SwingConstants.LEFT);
        enterP.setBounds(15, 115, 100, 30);
        enterP.setText("");
        this.add(enterP);


        enterPopSize = new JTextField();
        enterPopSize.setFont(new Font("Arial", Font.PLAIN, 15));
        enterPopSize.setHorizontalAlignment(SwingConstants.LEFT);
        enterPopSize.setBounds(15, 40, 100, 30);
        enterPopSize.setText("");
        this.add(enterPopSize);


        enterSampleNum = new JTextField();
        enterSampleNum.setFont(new Font("Arial", Font.PLAIN, 15));
        enterSampleNum.setHorizontalAlignment(SwingConstants.LEFT);
        enterSampleNum.setBounds(275, 420-250, 100, 30);
        enterSampleNum.setText("");
        this.add(enterSampleNum);


        enterNatSelecVal = new JTextField();
        enterNatSelecVal.setFont(new Font("Arial", Font.PLAIN, 15));
        enterNatSelecVal.setHorizontalAlignment(SwingConstants.LEFT);
        enterNatSelecVal.setBounds(15, 255, 100, 30);
        enterNatSelecVal.setText("");
        this.add(enterNatSelecVal);


        numFlow12 = new JTextField();
        numFlow12.setFont(new Font("Arial", Font.PLAIN, 15));
        numFlow12.setHorizontalAlignment(SwingConstants.LEFT);
        numFlow12.setBounds(15, 465, 100, 30);
        numFlow12.setText("");
        this.add(numFlow12);


        numFlow21 = new JTextField();
        numFlow21.setFont(new Font("Arial", Font.PLAIN, 15));
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
        deletePop1.setBounds(545, 195, 120, 30);
        deletePop1.setText("Delete pop1");
        this.add(deletePop1);
        deletePop1.addActionListener(this);
        deletePop1.setVisible(false);

        switchView = new JButton();
        switchView.setFont(new Font("Arial", Font.PLAIN, 15));
        switchView.setHorizontalAlignment(SwingConstants.CENTER);
        switchView.setBounds(275, 290, 200, 30);
        switchView.setText("Switch To Color View");
        this.add(switchView);
        switchView.addActionListener(this);
        switchView.setVisible(false);

        deletePop2 = new JButton();
        deletePop2.setFont(new Font("Arial", Font.PLAIN, 15));
        deletePop2.setHorizontalAlignment(SwingConstants.CENTER);
        deletePop2.setBounds(890, 195, 120, 30);
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
        img = new ImageIcon(this.getClass().getResource("imageCon.png"));
    }


    @Override
    public Dimension getPreferredSize(){
        return new Dimension(1300,650);
    }


    @Override
    public void paintComponent(Graphics g){
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
        if (wrongDataNull){
            g.drawString("Please enter a number here",277,115);
        }
        if (wrongDataSize){
            g.drawString("Please enter a number greater than",277,115);
            g.drawString("0 and less than the population",277,130);
        }
        if (wrongDataNullNatSelec){
            g.drawString("Please enter a number here",15,415);
        }
        if (wrongDataSizeNatSelec){
            g.drawString("Please enter a number greater than",15,415);
            g.drawString("or equal to 0 and less than 1",15,430);
        }
        if (wrongDataNullPop){
            g.drawString("Please enter a number here",15,200);
        }
        if (wrongDataSizePop){
            g.drawString("Please enter a number greater than 1", 15, 200);
        }
        if(showBadEntryMessage){
            g.drawString("Please enter a number greater than", 5, 200);
            g.drawString("or equal to 0 and less than 1", 15, 215);
        } 
        if(showNotEnoughPopsMessage){
            g.drawString("You must have two populations",5,625);
            g.drawString("to do gene flow",18,637);
        }
        if(showBadEntryMessageGeneFlow){
            g.drawString("Please enter a number greater than",5,625);
            g.drawString("or equal to 0 and less than 1",15,637);
        } 
        if(entryNullGeneFlow){
           g.drawString("Please enter a number here",15,630);
        }
        
        if(wrongDataNullP){
            g.drawString("Please enter a number here", 15, 200);
        }

        if(noPopSelec){
            g.drawString("Please select a population",277,120);
        }
        
        if(noTypeSelec){
            g.drawString("Please enter a type to kill", 15, 415);
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
            deletePop1.setBounds(545, 195, 120, 30);
            g.drawString("Popuation 1: ", 545, 25);
            g.drawString("Population Size: " + String.valueOf(popSize), 545, 50);
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(Math.floor(p*1000)/1000),545, 75);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(Math.floor(q*1000)/1000),545, 100);
            g.drawString("Predicted Number of AA by Hardy Weinberg: " + Integer.toString((int)(p*p*popSize*1.0)), 545, 125);
            g.drawString("Predicted Number of Aa by Hardy Weinberg: " + Integer.toString((int)(p*q*popSize*2.0)), 545, 150);
            g.drawString("Predicted Number of aa by Hardy Weinberg: " + Integer.toString((int)(q*q*popSize*1.0)), 545, 175);

            if(showPart2 == true){
                deletePop1.setBounds(545, 295, 120, 30);
                g.drawString("Predicted Number of Dominant Alleles: " + Integer.toString((int)domNum),545, 125+75);
                g.drawString("Predicted Number of Recessive Alleles: " + Integer.toString((int)recNum),545, 150+75);
                g.drawString("Sampled Number of Dominant Alleles out of " + individuals.size() + ": " + Integer.toString(randDomNum),545, 175+75);
                g.drawString("Sampled Number of Recessive Alleles out of " + individuals.size() + ": " +Integer.toString(randRecNum),545, 200+75);
            }
        }


        if(valsSet2 == true){    
            deletePop2.setBounds(890, 195, 120, 30);
            //drawing on screen
            g.drawString("Popuation 2: ", 890, 25);
            g.drawString("Population Size: " + String.valueOf(popSize2), 890, 50);
            g.drawString("Ratio of Dominant Alleles: " + Double.toString(Math.floor(p2*1000)/1000),890, 75);
            g.drawString("Ratio of Recessive Alleles: " + Double.toString(Math.floor(q2*1000)/1000),890, 100);
            g.drawString("Predicted Number of AA by Hardy Weinberg: " + Integer.toString((int)(p2*p2*popSize2*1.0)), 890, 125);
            g.drawString("Predicted Number of Aa by Hardy Weinberg: " + Integer.toString((int)(p2*q2*popSize2*2.0)), 890, 150);
            g.drawString("Predicted Number of aa by Hardy Weinberg: " + Integer.toString((int)(q2*q2*popSize2*1.0)), 890, 175);


            if(showPart22 == true){
                deletePop2.setBounds(890, 295, 120, 30);
                g.drawString("Predicted Number of Dominant Alleles: " + Integer.toString((int)domNum2),890, 125+75);
                g.drawString("Predicted Number of Recessive Alleles: " + Integer.toString((int)recNum2),890, 150+75);
                g.drawString("Sampled Number of Dominant Alleles out of " + individuals2.size() + ": " + Integer.toString(randDomNum2),890, 175+75);
                g.drawString("Sampled Number of Recessive Alleles out of " + individuals2.size() + ": " +Integer.toString(randRecNum2),890, 200+75);
            }
        }
        if (graphView){
            g.setColor(Color.black);
            //g.drawImage(graphImage,330,367,null);
            //axis 
            g.drawLine(330,355,330,575);
            g.drawLine(330,575,1250,575);
            
            g.drawLine(330,355,340,365);
            g.drawLine(320,365,330,355);
            g.drawLine(1250,575,1240,585);
            g.drawLine(1240,565,1250,575);
            
            int xLine = 330;
            int yLine = 375;
            for(int i=0; i<11;i++){
                g.setColor(new Color(0,0,0,75));
                g.drawLine(330, yLine, 1230, yLine);
                if (i%5 == 0){
                    g.drawString(1-(double)i/10 + "",310,yLine+3);
                }
                yLine+=20;
            }
            for(int i=0; i<46; i++){
                g.drawLine(xLine, 575, xLine, 375);
                if (i%5 == 0){
                    g.drawString(i + "",xLine-6,585);
                }
                xLine+=20;
            }
            g.setColor(Color.black);


            g.drawString("Generations",745,615);

            if(drawRect1){
                //key
                g.setColor(Color.black);
                g.drawString("population 1:", 1150-115,300+20);
                g.drawString("dominant allele: ",1150-115,320+20); 
                g.setColor(new Color(32, 58, 89));
                g.fillRect(1240-103,313+20,8,8);
                g.setColor(Color.black);
                g.drawString("recessive allele: ",1150-115,340+20); 
                g.setColor(new Color(207, 57, 50));
                g.fillRect(1240-103,333+20,8,8);
                //graph
                g.setColor(Color.black);
                int yVal1 = (int)(200-(p*100*2)+372);
                int yVal2 = (int)(200-(q*100*2)+372);
                if(generation<46){
                    if(addToArray==true){
                        domArray[generation] = yVal1;
                        recArray[generation] = yVal2;
                    }
                    for(int i=0; i<=generation; i++){
                        if(domArray[i]!=0){
                            g.setColor(new Color(32, 58, 89));
                            g.fillOval(i*20+328, domArray[i], 5,5);
                        }
                        if(recArray[i]!=0){
                            g.setColor(new Color(207, 57, 50));                
                            g.fillOval(i*20+328, recArray[i], 5,5);
                        }
                    }
                } else {
                    for(int i=0; i<46; i++){
                        if(domArray[i]!=0){
                            g.setColor(new Color(32, 58, 89));
                            g.fillOval(i*20+328, domArray[i], 5,5);
                        }
                        if(recArray[i]!=0){
                            g.setColor(new Color(207, 57, 50));                
                            g.fillOval(i*20+328, recArray[i], 5,5);
                        }
                    }
                }
            } if(drawRect2){
                g.setColor(Color.black);
                g.drawString("population 2", 1150,320);
                g.drawString("dominant allele: ",1150,340); 
                g.setColor(new Color(176, 92, 237));
                g.fillRect(1252,333,8,8);
                g.setColor(Color.black);
                g.drawString("recessive allele: ",1150,360); 
                g.setColor(new Color(141, 194, 124));
                g.fillRect(1252,353,8,8);
                //graph
                g.setColor(Color.black);
                int yVal1 = (int)(200-(p2*100*2)+372);
                int yVal2 = (int)(200-(q2*100*2)+372);
                if(generation<46){
                    if(addToArray==true){
                        domArray2[generation] = yVal1;
                        recArray2[generation] = yVal2;
                    }                   
                    for(int i=0; i<=generation; i++){
                        if(domArray2[i]!=0){
                            g.setColor(new Color(176, 92, 237));
                            g.fillOval(i*20+328, domArray2[i], 5,5);
                        }
                        if(recArray2[i]!=0){
                            g.setColor(new Color(141, 194, 124));             
                            g.fillOval(i*20+328, recArray2[i], 5,5);
                        }
                    }
                } else {
                    for(int i=0; i<46; i++){
                        if(domArray2[i]!=0){
                            g.setColor(new Color(176, 92, 237));
                            g.fillOval(i*20+328, domArray2[i], 5,5);
                        }
                        if(recArray2[i]!=0){
                            g.setColor(new Color(141, 194, 124));        
                            g.fillOval(i*20+328, recArray2[i], 5,5);
                        }
                    }
                }
            }
        }
        if(graphView==false){  
            if(drawRect1){
                g.setColor(Color.black);
                g.drawString("dominant allele: ",415,380); 
                g.setColor(new Color(32, 58, 89));
                g.fillRect(517,372,8,8);
                g.setColor(Color.black);
                g.drawString("recessive allele: ",415,400); 
                g.setColor(new Color(207, 57, 50));
                g.fillRect(517,392,8,8);
                
                int domPixelsTall = (int)(p*200);
                int recPixelsTall = 200-domPixelsTall;
                g.setColor(new Color(32, 58, 89));
                g.fillRect(550,400,200,domPixelsTall);
                g.setColor(new Color(207, 57, 50));
                g.fillRect(550,400+domPixelsTall,200,recPixelsTall);
                
            }

            if(drawRect2){
                int domPixelsTall = (int)(p2*200);
                int recPixelsTall = 200-domPixelsTall;
                g.setColor(new Color(32, 58, 89));
                g.fillRect(900,400,200,domPixelsTall);
                g.setColor(new Color(207, 57, 50));
                g.fillRect(900,400+domPixelsTall,200,recPixelsTall);          
            }
        }

        if (graphView){
            //g.setFont(new Font("Arial",Font.PLAIN, 18));
            /*Graphics2D g2d = (Graphics2D)g;
            AffineTransform defaultAt = g2d.getTransform();
            AffineTransform at = new AffineTransform();
            at.rotate(-Math.PI/2);
            g2d.setTransform(at);
            g.setColor(Color.black);
            //g2d.drawString("Allele Frequency", -785, 440);
            g2d.drawString("Allele Frequency", -525, 300);
            //System.out.println("test");
            
            AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(1);
            g2d.setTransform(at2);         
            g2d.setTransform(defaultAt);*/
            img.paintIcon(this, g, 280, 425); 
        }

    }

    public void calculateVals(){
        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        tempArray = individuals;
       
        popSize = tempArray.size();
        totDom = 0;
        totRec = 0;
        for(int i=0;i<tempArray.size();i++){
            if(tempArray.get(i)==1){
                totDom++;
            } else if (tempArray.get(i)==2){
                totRec++;
            }
        }
        p = (1.0*totDom)/popSize;
        q = (1.0*totRec)/popSize;
    }
    public void calculateVals2(){
        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        tempArray = individuals2;
       
        popSize2 = tempArray.size();
        totDom2 = 0;
        totRec2 = 0;
        for(int i=0;i<tempArray.size();i++){
            if(tempArray.get(i)==1){
                totDom2++;
            } else if (tempArray.get(i)==2){
                totRec2++;
            }
        }
        p2 = (1.0*totDom2)/popSize2;
        q2 = (1.0*totRec2)/popSize2;
    }
    
    public void actionPerformed(ActionEvent e ){
        showBadEntryMessage = false;
        showNotEnoughPopsMessage = false;
        wrongDataNull = false;
        wrongDataSize = false;
        showBadEntryMessageGeneFlow = false;
        entryNullGeneFlow = false;
        wrongDataSizeNatSelec = false;
        wrongDataNullNatSelec = false;
        wrongDataNullP = false;
        wrongDataNullPop = false;
        wrongDataSizePop = false;
        noPopSelec = false;
        noTypeSelec = false;
        if(e.getSource()==noReplace){
            addToArray = true;
            if (usePop1){
                if (enterSampleNum.getText().equals("")){
                    System.out.println("null");
                    wrongDataNull = true;
                }
                else if (Integer.parseInt(enterSampleNum.getText()) < 0 || Integer.parseInt(enterSampleNum.getText()) > popSize){
                    wrongDataSize = true;
                }
                else{
                    sampleNum = Integer.parseInt(enterSampleNum.getText());
                    domNum = (int)(p*sampleNum);
                    recNum = (int)(q*sampleNum);
                    randRecNum = 0;
                    randDomNum = 0;
                    ArrayList<Integer> tempArray = new ArrayList<Integer>();
                    for(int i=0;i<individuals.size();i++){
                        tempArray.add(individuals.get(i));
                    }        
                    individuals.clear();     
                    for(int i=0; i<sampleNum; i++){
                        int rand1 = (int)(Math.random()*tempArray.size());
                        int num = tempArray.get(rand1);
                        tempArray.remove(rand1);
                        individuals.add(num);
                        if(num == 1){
                            randDomNum++;
                        } else if(num==2){
                            randRecNum++;
                        }
                    }
                    showPart2 = true;
                    calculateVals();
                    generation++;
                }
            }
            if (usePop2){
                if (enterSampleNum.getText().equals("")){
                    wrongDataNull = true;
                }
                else if (Integer.parseInt(enterSampleNum.getText()) < 0 || Integer.parseInt(enterSampleNum.getText()) > popSize2){
                    wrongDataSize = true;
                }
                else{
                    sampleNum = Integer.parseInt(enterSampleNum.getText());
                    //predicting
                    domNum2 = (int)(p2*sampleNum);
                    recNum2 = (int)(q2*sampleNum);
                    //prepping for the sampling in paint component
                    randRecNum2=0;
                    randDomNum2=0;
                    ArrayList<Integer> tempArray = new ArrayList<Integer>();
                    for(int i=0;i<individuals2.size();i++){
                        tempArray.add(individuals2.get(i));
                    } 
                    individuals2.clear();          
                    for(int i=0; i<sampleNum; i++){
                        int rand1 = (int)(Math.random()*tempArray.size());
                        int num = tempArray.get(rand1);
                        individuals2.add(tempArray.remove(rand1));
                        if(num == 1){
                            randDomNum2++;
                        } else if(num==2){
                            randRecNum2++;
                        }
                    }
                    showPart22 = true;
                    calculateVals2();
                    generation++;
                }
            }
            if (usePop1 == false && usePop2 == false){
                noPopSelec = true;
            }
           
        }  else if (e.getSource() == replace){
            addToArray = true;
            if (usePop1){
                if (enterSampleNum.getText().equals("")){
                    wrongDataNull = true;
                }
                else if (Integer.parseInt(enterSampleNum.getText()) < 0 || Integer.parseInt(enterSampleNum.getText()) > popSize){
                    wrongDataSize = true;
                }
                else{
                    sampleNum = Integer.parseInt(enterSampleNum.getText());
                    //predicting
                    domNum = (int)(p*sampleNum);
                    recNum = (int)(q*sampleNum);
                    //prepping for the sampling in paint component
                    randDomNum=0;
                    randRecNum=0;
                    for(int i=0; i<sampleNum; i++){
                        int rand1 = (int)(Math.random()*individuals.size());
                        int num = individuals.get(rand1);
                        if(num == 1){
                            randDomNum++;
                        } else if(num==2){
                            randRecNum++;
                        }
                    }
                    individuals.clear();
                    for(int i=0; i<randDomNum;i++){
                        individuals.add(1);
                    }
                    for(int i=0; i<randRecNum;i++){
                        individuals.add(2);
                    }
                    // replacement type from natural selection
                    showPart2=true;
                    calculateVals();
                    generation++;
                }
            }
            if (usePop2){
                if (enterSampleNum.getText().equals("")){
                    wrongDataNull = true;
                }
                else if (Integer.parseInt(enterSampleNum.getText()) < 0 || Integer.parseInt(enterSampleNum.getText()) > popSize2){
                    wrongDataSize = true;
                }
                else{
                    sampleNum = Integer.parseInt(enterSampleNum.getText());
                    //predicting
                    domNum2 = (int)(p2*sampleNum);
                    recNum2 = (int)(q2*sampleNum);
                    //prepping for the sampling in paint component
                    randDomNum2=0;
                    randRecNum2=0;
                    for(int i=0; i<sampleNum; i++){
                        int rand1 = (int)(Math.random()*individuals2.size());
                        int num = individuals2.get(rand1);
                        if(num == 1){
                            randDomNum2++;
                        } else if(num==2){
                            randRecNum2++;
                        }
                    }
                    individuals2.clear();
                    for(int i=0; i<randDomNum2;i++){
                        individuals2.add(1);
                    }
                    for(int i=0; i<randRecNum2;i++){
                        individuals2.add(2);
                    }
                    calculateVals2();
                    // replacement type from natural selection
                    showPart22=true;
                    generation++;
                }
                
            }
            if (usePop1 == false && usePop2 == false){
                noPopSelec = true;
            }
        } else if (e.getSource() == natSelec){
            if (enterNatSelecVal.getText().equals("")){
                wrongDataNullNatSelec = true;
            }
            else if (Double.parseDouble(enterNatSelecVal.getText()) < 0 || Double.parseDouble(enterNatSelecVal.getText()) > 1){
                wrongDataSizeNatSelec = true;
            }
            else{
                double selecRat = Double.parseDouble(enterNatSelecVal.getText());
                
                if (usePop1){
                    //determining the total number of each type of individual
                    totDom = 0;
                    totRec = 0;
                    for(int i=0; i<individuals.size();i++){
                        if(individuals.get(i).equals(1)){
                            totDom++;
                        } else if (individuals.get(i).equals(2)){
                            totRec++;
                        }
                    }
                    
                    if(selecDom){
                        totDom = totDom - (int)(totDom*selecRat);
                    }
                    if(selecRec){
                        totRec = totRec - (int)(totRec*selecRat);
                    }
                    if (selecHet){
                        double selecNum = selecRat/2;
                        totDom = totDom-(int)(totDom*selecNum);
                        totRec = totRec-(int)(totRec*selecNum);
                    }
                    if (selecDom == false && selecRec == false && selecHet == false){
                        noTypeSelec = true;
                    }
                    popSize = totDom + totRec;


                    p = 1.0*totDom/popSize;
                    q = 1.0*totRec/popSize;
                    individuals.clear();


                    for(int i=0; i<totDom; i++){
                        individuals.add(1);
                    }
        
                    for(int i=0;i<totRec;i++){
                        individuals.add(2);
                    }
                        
                    randRecNum=0;
                    randDomNum=0;
                    valsSet = true;
                    showPart2 = false;
                }
                if (usePop2){
                //determining the total number of each type of individual
                totDom2 = 0;
                totRec2 = 0;
                    for(int i=0; i<individuals2.size();i++){
                        if(individuals2.get(i).equals(1)){
                            totDom2++;
                        } else if (individuals2.get(i).equals(2)){
                            totRec2++;
                        }
                    }
                    if(selecDom2){
                        totDom2 = totDom2 - (int)(totDom2*selecRat);
                    }
                    if(selecRec2){
                        totRec2 = totRec2 - (int)(totRec2*selecRat);
                    }
                    if (selecHet2){
                        double selecNum = selecRat/2;
                        totDom2 = totDom2 - (int)(totDom * selecNum);
                        totRec2 = totRec2 - (int)(totRec * selecNum);
                    }
                    if (selecDom2 == false && selecRec2 == false && selecHet2 == false){
                        noTypeSelec = true;
                    }
                    popSize2 = totDom2 + totRec2;

                    p2 = 1.0*totDom2/popSize2;
                    q2 = 1.0*totRec2/popSize2;
                    individuals2.clear();


                    for(int i=0; i<totDom2; i++){
                        individuals2.add(1);
                    }
        
                    for(int i=0;i<totRec2;i++){
                        individuals2.add(2);
                    }
                        
                    randRecNum2=0;
                    randDomNum2=0;
                    valsSet2 = true;
                    showPart22 = false;
                }
                addToArray = false;
                if(usePop1==false && usePop2==false){
                    noPopSelec = true;
                }
            }
            
        } else if (e.getSource()==geneFlow){
            addToArray = false;
            if(numOfPops==2){
                if(!numFlow12.getText().equals("") && !numFlow21.getText().equals("")){
                    if(Double.parseDouble(numFlow12.getText())>=0&&Double.parseDouble(numFlow12.getText())<1&&Double.parseDouble(numFlow21.getText())>=0&&Double.parseDouble(numFlow21.getText())<1){
                        int numTransfer1 = (int)(Double.parseDouble(numFlow12.getText())*individuals.size());
                        int numTransfer2 = (int)(Double.parseDouble(numFlow21.getText())*individuals2.size());
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
                        calculateVals();
                        calculateVals2();
                    } else{
                        showBadEntryMessageGeneFlow = true;
                    }   
                } else{
                    entryNullGeneFlow = true;
                }              
            } else{
                showNotEnoughPopsMessage = true;
            }
        }
        else if (e.getSource() == addPop){
            if (enterPopSize.getText().equals("")){
                wrongDataNullPop = true;
            }
            else if (Integer.parseInt(enterPopSize.getText()) < 1){
                wrongDataSizePop = true;
            }
            else{
                if(numOfPops==0){
                    if(enterP.getText().equals("")){
                        wrongDataNullP = true;
                    } else if(p<0||p>1){
                        showBadEntryMessage = true;
                    } else{
                        individuals.clear();
                        p = Double.parseDouble(enterP.getText());
                        q = (1-Double.parseDouble(enterP.getText()));
                        drawRect1 = true;
                        popSize =  Integer.parseInt(enterPopSize.getText());
                        totDom = (int)(p*popSize);
                        totRec = (int)(q*popSize);
                        //creating population of alleles
                        for(int i=0; i<totDom;i++){
                            individuals.add(1);
                        }
                        for(int i=0; i<totRec;i++){
                            individuals.add(2);
                        }
                        //so that sampling will happen
                        valsSet = true;
                        showPart2 = false;
                        numOfPops++;
                        deletePop1.setVisible(true);
                    }
                    switchView.setVisible(true);
                    generation = 0;
                } else if (numOfPops==1){
                    if(enterP.getText().equals("")){
                        wrongDataNullP = true;
                    } else if(p<0||p>1){
                        showBadEntryMessage = true;
                    }  else{
                        individuals2.clear();
                        p2 = Double.parseDouble(enterP.getText());
                        q2 =  (1-Double.parseDouble(enterP.getText()));
                        drawRect2 = true;
                        popSize2 =  Integer.parseInt(enterPopSize.getText());
                        //hardy weinburg equation
                        totDom2 = (int)(p2*popSize2);
                        totRec2 = (int)(q2 * popSize2);
                        //creating population of alleles
                        for(int i=0; i<totDom2;i++){
                            individuals2.add(1);
                        }
                        for(int i=0; i<totRec2;i++){
                            individuals2.add(2);
                        }
                        valsSet2 = true;
                        showPart22 = false;
                        showPart2 = false;
                        numOfPops++;
                        addPop.setVisible(false);
                        deletePop2.setVisible(true);
                    }
                } else{
                    addPop.setVisible(false);
                }
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
                drawRect1 = false;
                switchView.setVisible(false);
                generation = 0;
                domArray = new int[15];
                recArray = new int[15];
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
                drawRect2=false;
                domArray = new int[15];
                recArray = new int[15];
            }
        } else if (e.getSource()==deletePop2){
            for(int i=0;i<individuals2.size();i++){
                individuals2.remove(i);
            }
            deletePop2.setVisible(false);
            numOfPops--;
            valsSet2 = false;
            addPop.setVisible(true);
            drawRect2 = false;
            domArray2 = new int[15];
            recArray2 = new int[15];
        } else if (e.getSource()==switchView){
            if(graphView){
                graphView = false;
                switchView.setText("Switch to Graph View");
            } else {
                graphView = true;
                switchView.setText("Switch to Color View");
            }
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
}
