package Ui;

import Solver.Cage;
import Solver.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class BoardUtils {
    public static void paintCage(List<Cage>cages,JPanel[][] boardGUI) {
        for(Cage c:cages){
            Random random = new Random();
            Color color = Color.getHSBColor(random.nextFloat(),0.6f,0.8f);
            addTarget(boardGUI[c.getPoints().get(0).getM()][c.getPoints().get(0).getN()],(c.getTarget())+c.getOperation().toString());
            for(Solver.Point p:c.getPoints()){
                boardGUI[p.getM()][p.getN()].setBackground(color);
                int top = 3,left=3,bottom=3,right=3;
                if(c.getPoints().contains(new Solver.Point(p.getM()-1,p.getN())))
                    top=1;
                if(c.getPoints().contains(new Solver.Point(p.getM()+1,p.getN())))
                    bottom=1;
                if(c.getPoints().contains(new Solver.Point(p.getM(),p.getN()-1)))
                    left=1;
                if(c.getPoints().contains(new Point(p.getM(),p.getN()+1)))
                    right=1;
                boardGUI[p.getM()][p.getN()].setBorder(BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK));
            }
        }
    }
    public static void addTarget(JPanel jPanel, String inp) {
        JLabel l = new JLabel(inp);
        l.setBounds(5,0,30,18);
        l.setFont(new Font("Arial",Font.BOLD,18));

        jPanel.add(l);
        jPanel.revalidate();
        jPanel.repaint();
    }
}
