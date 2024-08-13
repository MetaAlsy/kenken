package Ui;

import Generator.Board;
import Solver.Cage;
import Solver.Point;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Random;

public class BoardPanel extends JPanel {
    KenKenController controller;
    private JPanel[][] boardGUI;
    private JPanel boardPanel;
    private int size;

    public BoardPanel(KenKenController controller){
        this.controller = controller;
        setLayout(new BorderLayout());
        boardPanel = new JPanel();
        add(boardPanel,BorderLayout.CENTER);

        JPanel controlPannel = new JPanel();
        JButton checkButtun = new JButton("Check");
        checkButtun.addActionListener(e->controller.checkSoluzione());

        JButton returnButtun = new JButton("Torna al menu");
        returnButtun.addActionListener(e->controller.returnToMenu());

        JButton risolviButtun = new JButton("Risolvi");
        risolviButtun.addActionListener(a->controller.solvePuzzle());

        controlPannel.add(checkButtun);
        controlPannel.add(returnButtun);
        controlPannel.add(risolviButtun);
        add(controlPannel,BorderLayout.SOUTH);
    }
    public void initBoard(Board board){

        this.size = board.getN();
        boardPanel.setLayout(new GridLayout(size,size));
        boardGUI = new JPanel[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                boardGUI[i][j]=new JPanel(null);
                boardGUI[i][j].setPreferredSize(new Dimension(50,50));
                boardGUI[i][j].setBorder(new MatteBorder(1,1,1,1, Color.DARK_GRAY));
                boardPanel.add(boardGUI[i][j]);
            }
        }
    }
    public void updateBoard(Board board){

        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                int v = board.getBoard()[i][j];
                JTextField textField= new JTextField((v== 0 ? "" : String.valueOf(v)));
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBounds(0,0,50,50);
                textField.setOpaque(false);
                textField.setBorder(null);
                int m=i;
                int n=j;
                textField.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        controller.inserisciValore(m,n,textField.getText());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        controller.inserisciValore(m,n,textField.getText());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        controller.inserisciValore(m,n,textField.getText());
                    }
                });
                boardGUI[i][j].removeAll();
                boardGUI[i][j].add(textField,BorderLayout.CENTER);
                boardPanel.add(boardGUI[i][j]);
            }
        }
        BoardUtils.paintCage(controller.getCages(),boardGUI);
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void reset() {
        boardPanel.removeAll();
        boardPanel.revalidate();
        boardPanel.repaint();
        initBoard(controller.getBoard());
    }
}
