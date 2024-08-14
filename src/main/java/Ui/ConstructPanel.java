package Ui;

import Operations.Divisione;
import Operations.Multiplicazione;
import Operations.Somma;
import Operations.Sottrazione;
import Solver.Cage;
import Solver.Point;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConstructPanel extends JPanel {
    private KenKenController controller;
    private JPanel[][] boardGUI;
    private JPanel constructPanel;
    private int size;
    private List<Point> points, visitati;

    public ConstructPanel(KenKenController controller){
        this.controller= controller;
        this.points = new ArrayList<>();
        this.visitati = new ArrayList<>();
        setLayout(new BorderLayout());
        constructPanel = new JPanel();
        add(constructPanel,BorderLayout.CENTER);
        JPanel controlPanel = new JPanel(new GridLayout(5,1,0,10));
        JButton createButton = new JButton("Crea reggione");
        createButton.addActionListener(e->{
            popUp();
        });
        JButton createPuzzle = new JButton("Crea puzzle");
        JButton returnButton = new JButton("Torna nel menu");
        returnButton.addActionListener(e->controller.returnToMenu());
        createPuzzle.addActionListener(e -> controller.inizia());
        controlPanel.add(createButton);
        controlPanel.add(createPuzzle);
        controlPanel.add(returnButton);

        add(controlPanel,BorderLayout.EAST);
    }
    public void initBoard(int size){
        this.size = size;
        constructPanel.setLayout(new GridLayout(size,size));
        boardGUI = new JPanel[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                boardGUI[i][j] = new JPanel(null);
                boardGUI[i][j].setPreferredSize(new Dimension(50,50));
                boardGUI[i][j].setBorder(new MatteBorder(1,1,1,1, Color.DARK_GRAY));
                int m = i;
                int n = j;
                boardGUI[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        if(points.contains(new Point(m,n))){
                            points.remove(new Point(m,n));
                            boardGUI[m][n].setBackground(null);
                        } else if (!visitati.contains(new Point(m,n))) {
                            points.add(new Point(m,n));
                            boardGUI[m][n].setBackground(Color.CYAN);
                        }
                    }
                });

                constructPanel.add(boardGUI[i][j]);
            }
        }
    }
    private void popUp() {
        JDialog finestra = new JDialog();
        finestra.setTitle("Scegli parametri");
        finestra.setLocation(250,250);
        finestra.setSize(270,270);
        finestra.setLayout(new FlowLayout());

        JRadioButton b1 = new JRadioButton("Somma",true);
        JRadioButton b2 = new JRadioButton("Sottrazione");
        JRadioButton b3 = new JRadioButton("Moltiplicazione");
        JRadioButton b4 = new JRadioButton("Divisione");

        ButtonGroup group = new ButtonGroup();
        group.add(b1);
        group.add(b2);
        group.add(b3);
        group.add(b4);

        finestra.add(b1);
        finestra.add(b2);
        finestra.add(b3);
        finestra.add(b4);

        finestra.add(new JLabel("Inserisci il target:"));
        JTextField numero = new JTextField(10);
        finestra.add(numero);

        JButton okButtun = new JButton("Ok");
        finestra.add(okButtun);
        okButtun.addActionListener(e->{
            if(!numero.getText().isEmpty()){
                List<Point> punt = new ArrayList<>(points);
                visitati.addAll(points);
                points.clear();
                if(b1.isSelected())
                    controller.createCage(new Cage(punt,Integer.valueOf(numero.getText()),new Somma()));
                if(b2.isSelected())
                    controller.createCage(new Cage(punt,Integer.valueOf(numero.getText()),new Sottrazione()));
                if(b3.isSelected())
                    controller.createCage(new Cage(punt,Integer.valueOf(numero.getText()),new Multiplicazione()));
                if(b4.isSelected())
                    controller.createCage(new Cage(punt,Integer.valueOf(numero.getText()),new Divisione()));
                BoardUtils.paintCage(controller.getCages(),boardGUI);
                finestra.dispose();
            }
        });
        finestra.setModal(true);
        finestra.setVisible(true);
    }
    public void reset() {
        constructPanel.removeAll();
        constructPanel.revalidate();
        constructPanel.repaint();
    }

}
