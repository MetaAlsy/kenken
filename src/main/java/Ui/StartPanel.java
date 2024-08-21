package Ui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private KenKenController controller;
    private int size = 4;

    public StartPanel(KenKenController controller){
        this.controller=controller;
        setLayout(new GridLayout(3,1));
        JLabel initialLable = new JLabel("KenKen Puzzle",JLabel.CENTER);

        ButtonGroup sizeGroup = new ButtonGroup();

        JRadioButton treButton = new JRadioButton("3x3");
        JRadioButton quatButton = new JRadioButton("4x4",true);
        JRadioButton cinqButton = new JRadioButton("5x5");
        JRadioButton seiButton = new JRadioButton("6x6");
        treButton.addActionListener(e->size=3);
        quatButton.addActionListener(e->size=4);
        cinqButton.addActionListener(e->size=5);
        seiButton.addActionListener(e->size=6);

        sizeGroup.add(treButton);
        sizeGroup.add(quatButton);
        sizeGroup.add(cinqButton);
        sizeGroup.add(seiButton);
        JPanel sizePanel = new JPanel(new GridLayout(2,1));
        sizePanel.add(new JLabel("Seleziona la dimensione",JLabel.CENTER));
        JPanel sizeButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizeButtonPanel.add(treButton);
        sizeButtonPanel.add(quatButton);
        sizeButtonPanel.add(cinqButton);
        sizeButtonPanel.add(seiButton);
        sizePanel.add(sizeButtonPanel);


        JButton startButtun = new JButton("Start");
        startButtun.setFont(new Font("Times New Roman",Font.BOLD,15));
        JPanel starPanel = new JPanel();
        starPanel.add(startButtun);
        startButtun.addActionListener(e->controller.start(size)
        );
        JButton creatButtun = new JButton("Crea personaliizato");
        creatButtun.setFont(new Font("Times New Roman",Font.BOLD,15));
        starPanel.add(creatButtun);
        creatButtun.addActionListener(e->{
            controller.showCreatScrean(size);
        });
        JButton repoButton = new JButton("Salvati");
        repoButton.addActionListener(e->{
            controller.showRepoScrean();
        });
        JPanel contPannel = new JPanel();
        contPannel.add(startButtun);
        contPannel.add(creatButtun);
        contPannel.add(repoButton);


        add(initialLable);
        add(sizePanel);
        add(contPannel);

    }


}
