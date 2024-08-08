package Ui;

import Generator.Board;
import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Solver.Cage;
import Solver.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public class KenKenView extends JFrame {
    private int size;
    private CardLayout cardLayout;
    private JPanel mainPanel,boardPanel;
    private KenKenDirector director;
    private JTextField[][] boardGUI;
    private Board b;
    public KenKenView(int size) {
        this.size = size;
        this.boardGUI = new JTextField[size][size];
        setTitle("KenKen");
        setSize(500, 500);
        setLocation(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        createStart();
        createBoard();
        add(mainPanel);

    }

    private void createBoard() {
        JPanel panel = new JPanel(new BorderLayout());
        boardPanel = new JPanel(new GridLayout(size,size));
        panel.add(boardPanel, BorderLayout.CENTER);

        JPanel controllPannel = new JPanel();
        JButton checkButtun = new JButton("Check");
        checkButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        JButton returnButtun = new JButton("Torna al menu");
        returnButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"StartScrean");
            }
        });

        controllPannel.add(checkButtun);
        controllPannel.add(returnButtun);

        panel.add(controllPannel,BorderLayout.SOUTH);
        mainPanel.add(panel,"BoardScrean");
    }

    private void createStart() {
        JPanel startPanel = new JPanel(new GridLayout(5,1));


        JLabel initialLable = new JLabel("KenKen Puzzle",JLabel.CENTER);

        ButtonGroup sizeGroup = new ButtonGroup();

        JRadioButton small = new JRadioButton("3x3");
        JRadioButton medium = new JRadioButton("6x6",true);
        JRadioButton big = new JRadioButton("9x9");
        sizeGroup.add(small);
        sizeGroup.add(medium);
        sizeGroup.add(big);
        JPanel sizePanel = new JPanel(new GridLayout(2,1));
        sizePanel.add(new JLabel("Seleziona la dimensione",JLabel.CENTER));
        JPanel sizeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizeButtonPanel.add(small);
        sizeButtonPanel.add(medium);
        sizeButtonPanel.add(big);
        sizePanel.add(sizeButtonPanel);

        JRadioButton somma = new JRadioButton("Somma",true);
        JRadioButton sottrazioen = new JRadioButton("Sottrazioen",true);
        JRadioButton multiplicazione = new JRadioButton("Multiplicazione",false);
        JRadioButton divisione = new JRadioButton("Divisione",false);

        JPanel opPanel = new JPanel();
        opPanel.add(somma);
        opPanel.add(sottrazioen);
        opPanel.add(multiplicazione);
        opPanel.add(divisione);

        JButton startButtun = new JButton("Start");
        startButtun.setFont(new Font("Times New Roman",Font.BOLD,15));
        JPanel starPanel = new JPanel();
        starPanel.add(startButtun);
        startButtun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KenKenBuilder build = new KenKenBuilder(size);
                director = new KenKenDirector(build);
                b=director.createKenken();
                cardLayout.show(mainPanel,"BoardScrean");
                boardGUI = new JTextField[size][size];
                for(int i=0;i<size;i++){
                    for(int j=0;j<size;j++){
                        boardGUI[i][j]=new JTextField();
                        boardGUI[i][j].setHorizontalAlignment(JTextField.CENTER);
                        boardPanel.add(boardGUI[i][j]);
                    }
                }
                for(Cage c:b.getCages()){
                    Random random = new Random();
                    Color color = Color.getHSBColor(random.nextFloat(),0.6f,0.8f);
                    for(Point p:c.getPoints()){
                        boardGUI[p.getM()][p.getN()].setBackground(color);
                    }
                }
                boardPanel.revalidate();
                boardPanel.repaint();
            }
        });
        startPanel.add(initialLable);

        startPanel.add(sizePanel);
        startPanel.add(new JLabel("Seleziona operazioni",JLabel.CENTER));
        startPanel.add(opPanel);
        startPanel.add(starPanel);
        mainPanel.add(startPanel,"StartScrean");
    }


    public static void main(String... args){
        KenKenView k = new KenKenView(4);
        k.setVisible(true);
    }
}
