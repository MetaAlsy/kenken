package Ui;

import javax.swing.*;
import java.awt.*;

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
