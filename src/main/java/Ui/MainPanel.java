package Ui;

import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Solver.Point;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;


    public MainPanel(){
        cardLayout = new CardLayout();
        setLayout(cardLayout);

    }
    public void addPanel(String name,JPanel panel){
        add(panel,name);
    }
    public void showPanel(String name){
        cardLayout.show(this,name);
    }
}
