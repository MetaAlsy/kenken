package repository;

import Generator.Board;
import Operations.Multiplicazione;
import Operations.Somma;
import Solver.Cage;
import Solver.Point;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardConnectionTest {
    @Test
    void uplodTest(){
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
        Board board = new Board(4);
        board.setCages(cages);
        BoardConnection b = new BoardConnection();
        b.saveBoard(board,"Prova");
    }
    @Test
    void testGet(){
        BoardConnection b = new BoardConnection();
        Board bo =b.getBoard(1);
        System.out.println(bo);
    }

}