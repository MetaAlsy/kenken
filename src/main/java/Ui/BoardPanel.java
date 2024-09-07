package Ui;

import Generator.Board;
import Generator.Observer;
import Generator.Subject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class BoardPanel extends JPanel implements Observer  {
    private KenKenController controller;
    private JPanel[][] boardGUI;
    private JPanel boardPanel;
    private int size;
    private JLabel countLabel;

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
        risolviButtun.addActionListener(a->{
            String max = JOptionPane.showInputDialog(this,"Inserisci il numero massimo di soluzioni: ","Numero soluzioni",JOptionPane.QUESTION_MESSAGE);
            if(max!=null && !max.isEmpty()){
                int m = Integer.parseInt(max);
                if(m>0)
                    controller.solvePuzzle(m);
                else
                    JOptionPane.showMessageDialog(this,"Numero deve essere maggiore di zero");
            }
            else
                JOptionPane.showMessageDialog(this,"Devi inserire il numero di soluzioni");
        });
        BasicArrowButton nButton = new BasicArrowButton(BasicArrowButton.EAST);
        nButton.addActionListener(e->controller.showNextSol());
        BasicArrowButton pButton = new BasicArrowButton(BasicArrowButton.WEST);
        pButton.addActionListener(e->controller.showPriviusSol());
        JButton salvaButtun = new JButton("Salva");
        salvaButtun.addActionListener(e->{
            popUp();});
        countLabel = new JLabel();
        countLabel.setHorizontalAlignment(SwingConstants.CENTER);
        controlPannel.add(salvaButtun);
        controlPannel.add(checkButtun);
        controlPannel.add(returnButtun);
        controlPannel.add(risolviButtun);
        controlPannel.add(pButton);
        controlPannel.add(countLabel);
        controlPannel.add(nButton);
        add(controlPannel,BorderLayout.SOUTH);
    }

    private void popUp() {
        JDialog finestra = new JDialog();
        finestra.setTitle("Salva puzzle");
        finestra.setLocation(250,250);
        finestra.setSize(270,270);
        finestra.setLayout(new FlowLayout());
        JTextField textField = new JTextField(20);
        finestra.add(textField);
        JButton okButtun = new JButton("Ok");
        finestra.add(okButtun);
        okButtun.addActionListener(e->{
            String name = textField.getText();
            if(!name.isEmpty()){
                controller.save(name);
                finestra.dispose();
            }
        });
        finestra.setModal(true);
        finestra.setVisible(true);
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
    public void updateBoard(Board b,boolean visible){
        int[][] board = b.getBoard();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++) {
                int v = board[i][j];
                JTextField textField= new JTextField();
                if(visible)
                    textField.setText((v== 0 ? "" : String.valueOf(v)));
                textField.setHorizontalAlignment(SwingConstants.CENTER);
                textField.setBounds(0,0,boardGUI[i][j].getWidth(),boardGUI[i][j].getHeight());
                //textField.setPreferredSize(new Dimension(50,50));
                textField.setFont(new Font("Times New Roman",Font.BOLD,15));
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
            }
        }
        BoardUtils.paintCages(b.getCages(),boardGUI);
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    public void reset() {
        countLabel.setText("");
        boardPanel.removeAll();
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    private void updateCount(int n){
        if(n==0)
            countLabel.setText(String.valueOf(""));
        else
            countLabel.setText(String.valueOf(n));
    }

    @Override
    public void update(Subject subject) {
        if(subject instanceof Board b){
            if(b.getNumSoluzioni()>0){
                updateCount(b.getIndex()+1);
            }else{
                updateCount(0);
            }
            updateBoard(b,true);
        }
    }
}
