package Solver;

import Generator.Board;
import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Operations.Multiplicazione;
import Operations.Somma;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    @Test
    void kenKenTest(){
        Point p = new Point(1,2);
        Point p1 = new Point(2,2);
        Point p2 = new Point(1,3);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c1 = new Cage(points,4,new Multiplicazione());



        p = new Point(1,0);
        p1 = new Point(1,1);
        p2 = new Point(2,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c2 = new Cage(points,10,new Somma());//



        p = new Point(0,0);
        p1 = new Point(0,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c3 = new Cage(points,8,new Multiplicazione());//



        p = new Point(2,0);
        points = new ArrayList<>();
        points.add(p);
        Cage c4 = new Cage(points,1,new Somma());//

        p = new Point(0,2);
        p1 = new Point(0,3);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c5 = new Cage(points,4,new Somma());//



        p = new Point(3,0);
        p1 = new Point(3,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c6 = new Cage(points,3,new Somma());//



        p = new Point(3,2);
        p1 = new Point(3,3);
        p2 = new Point(2,3);
        points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c7 = new Cage(points,11,new Somma());

        ArrayList<Cage> cages = new ArrayList<>();
        cages.add(c1);
        cages.add(c2);
        cages.add(c3);
        cages.add(c4);
        cages.add(c5);
        cages.add(c6);
        cages.add(c7);
        Solver s = new Solver(4,cages);
        int[][] soluzione ={
                {4,2,3,1},
                {3,4,1,2},
                {1,3,2,4},
                {2,1,4,3}
        } ;
        assertArrayEquals(soluzione,s.getSoluzione());
    }
    @Test
    void test2(){
        KenKenBuilder b = new KenKenBuilder(4);
        KenKenDirector dir = new KenKenDirector(b);
        Board board = dir.createKenken();
        System.out.println(board.toString());
        Solver s = new Solver(4,board.getCages());
        assertArrayEquals(board.getBoard(), s.getSoluzione());
    }

}