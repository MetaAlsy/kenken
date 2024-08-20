package Solver;

import Generator.Board;
import Generator.KenKenBuilder;
import Generator.KenKenDirector;
import Operations.StandardOperationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {
    private StandardOperationFactory standardOperationFactory = new StandardOperationFactory();
    private Board board ;
    private Solver solver ;

    @BeforeEach
    void setUp(){
        KenKenBuilder builder = new KenKenBuilder(4);//per questioni di velocita altrimenti impiega troppo tempo
        Point p = new Point(1,2);
        Point p1 = new Point(2,2);
        Point p2 = new Point(1,3);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c1 = new Cage(points,4, standardOperationFactory.createMul());
        builder.addCage(c1);

        p = new Point(1,0);
        p1 = new Point(1,1);
        p2 = new Point(2,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c2 = new Cage(points,10,standardOperationFactory.createSum());
        builder.addCage(c2);

        p = new Point(0,0);
        p1 = new Point(0,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c3 = new Cage(points,8,standardOperationFactory.createMul());
        builder.addCage(c3);

        p = new Point(2,0);
        points = new ArrayList<>();
        points.add(p);
        Cage c4 = new Cage(points,1,standardOperationFactory.createSum());
        builder.addCage(c4);

        p = new Point(0,2);
        p1 = new Point(0,3);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c5 = new Cage(points,4,standardOperationFactory.createSum());
        builder.addCage(c5);

        p = new Point(3,0);
        p1 = new Point(3,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c6 = new Cage(points,3,standardOperationFactory.createSum());
        builder.addCage(c6);

        p = new Point(3,2);
        p1 = new Point(3,3);
        p2 = new Point(2,3);
        points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c7 = new Cage(points,11,standardOperationFactory.createSum());
        builder.addCage(c7);

        board = builder.build();
        solver = new Solver(board);
        solver.risolvi();
    }
    @Test
    void kenKenTest(){

        int[][] soluzione ={
                {4,2,3,1},
                {3,4,1,2},
                {1,3,2,4},
                {2,1,4,3}
        };
        board.primaSol();
        assertArrayEquals(soluzione,board.getBoard());
    }
    @Test
    void esisteSoluzione(){
        assertTrue(board.getNumSoluzioni()>=1, "Deve trovare una soluzione");
    }
    @Test
    void kenken6x6(){
        KenKenBuilder builder = new KenKenBuilder(6);
        List<Point> points1 = Arrays.asList(new Point(0, 0));
        Cage c1 = new Cage(points1, 2, standardOperationFactory.createSum());
        builder.addCage(c1);

        List<Point> points2 = Arrays.asList(new Point(0, 1), new Point(1, 1));
        Cage c2 = new Cage(points2, 3, standardOperationFactory.createSott());
        builder.addCage(c2);

        List<Point> points3 = Arrays.asList(new Point(0, 2), new Point(0, 3));
        Cage c3 = new Cage(points3, 2, standardOperationFactory.createSott());
        builder.addCage(c3);

        List<Point> points4 = Arrays.asList(new Point(0, 4), new Point(1, 4));
        Cage c4 = new Cage(points4, 3, standardOperationFactory.createSum());
        builder.addCage(c4);

        List<Point> points5 = Arrays.asList(new Point(0, 5), new Point(1, 5));
        Cage c5 = new Cage(points5, 30, standardOperationFactory.createMul());
        builder.addCage(c5);

        List<Point> points6 = Arrays.asList(new Point(1, 0), new Point(2, 0), new Point(3, 0));
        Cage c6 = new Cage(points6, 9, standardOperationFactory.createSum());
        builder.addCage(c6);

        List<Point> points7 = Arrays.asList(new Point(1, 2), new Point(2, 2),new Point(2,1));
        Cage c7 = new Cage(points7, 24, standardOperationFactory.createMul());
        builder.addCage(c7);

        List<Point> points8 = Arrays.asList(new Point(1, 3), new Point(2, 3));
        Cage c8 = new Cage(points8, 1, standardOperationFactory.createSott());
        builder.addCage(c8);

        List<Point> points9 = Arrays.asList(new Point(2, 4), new Point(2, 5));
        Cage c9 = new Cage(points9, 2, standardOperationFactory.createSott());
        builder.addCage(c9);

        List<Point> points10 = Arrays.asList(new Point(3, 1), new Point(3, 2));
        Cage c10 = new Cage(points10, 2, standardOperationFactory.createDiv());
        builder.addCage(c10);

        List<Point> points11 = Arrays.asList(new Point(3, 3), new Point(4, 3), new Point(4, 2));
        Cage c11 = new Cage(points11, 4, standardOperationFactory.createMul());
        builder.addCage(c11);

        List<Point> points12 = Arrays.asList(new Point(3, 4), new Point(3, 5));
        Cage c12 = new Cage(points12, 5, standardOperationFactory.createSum());
        builder.addCage(c12);

        List<Point> points13 = Arrays.asList(new Point(4, 0), new Point(4, 1));
        Cage c13 = new Cage(points13, 2, standardOperationFactory.createSott());
        builder.addCage(c13);

        List<Point> points14 = Arrays.asList(new Point(4, 4));
        Cage c14 = new Cage(points14, 5, standardOperationFactory.createSum());
        builder.addCage(c14);

        List<Point> points15 = Arrays.asList( new Point(4, 5),new Point(5, 5),new Point(5, 4));
        Cage c15 = new Cage(points15, 8, standardOperationFactory.createSum());
        builder.addCage(c15);

        List<Point> points16 = Arrays.asList(new Point(5, 0), new Point(5, 1));
        Cage c16 = new Cage(points16, 1, standardOperationFactory.createSott());
        builder.addCage(c16);

        List<Point> points17 = Arrays.asList(new Point(5, 2), new Point(5, 3));
        Cage c17 = new Cage(points17, 3, standardOperationFactory.createSott());
        builder.addCage(c17);

        board = builder.build();
        solver = new Solver(board);
        Instant start = Instant.now();
        solver.risolvi();
        Instant finish = Instant.now();
        Duration d = Duration.between(start,finish);
        board.primaSol();
        assertTrue(board.esisteSoluzione(),"Soluzione trovata in soli: "+d.toMinutes() +"minuti");

    }
    @Test
    void testCasuale(){
        KenKenBuilder b = new KenKenBuilder(4);
        KenKenDirector dir = new KenKenDirector(b);
        board = dir.createKenken();
        System.out.println(board.toString());
        solver = new Solver(board);
        solver.risolvi();
        board.primaSol();
        assertTrue(board.esisteSoluzione());
    }

}