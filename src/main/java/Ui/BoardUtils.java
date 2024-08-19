package Ui;

import Solver.Cage;
import Solver.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class BoardUtils {
    private static final int NUM_COLORI = 10;
    private static final Color[] COLORI = gereaColori(NUM_COLORI);

    private static Color[] gereaColori(int x) {
        Color[] colori = new Color[x];
        Random random = new Random();
        for(int i=0;i<x;i++){
            colori[i]=Color.getHSBColor(random.nextFloat(), 0.6f, 0.8f);
        }
        return colori;
    }
    private static Color getColore(Cage c){
        return COLORI[Math.abs(c.hashCode())%NUM_COLORI];
    }

    public static void paintCages(List<Cage>cages, JPanel[][] boardGUI) {
        for(Cage c:cages){
            paintCage(c,boardGUI);
        }
    }
    public static void paintCage(Cage cage, JPanel[][] boardGUI) {
        Color color = getColore(cage);
        addTarget(boardGUI[cage.getPoints().get(0).getM()][cage.getPoints().get(0).getN()], (cage.getTarget()) + cage.getOperation().toString());
        for (Solver.Point p : cage.getPoints()) {
            boardGUI[p.getM()][p.getN()].setBackground(color);
            int top = 3, left = 3, bottom = 3, right = 3;
            if (cage.getPoints().contains(new Solver.Point(p.getM() - 1, p.getN())))
                top = 1;
            if (cage.getPoints().contains(new Solver.Point(p.getM() + 1, p.getN())))
                bottom = 1;
            if (cage.getPoints().contains(new Solver.Point(p.getM(), p.getN() - 1)))
                left = 1;
            if (cage.getPoints().contains(new Point(p.getM(), p.getN() + 1)))
                right = 1;
            boardGUI[p.getM()][p.getN()].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
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
