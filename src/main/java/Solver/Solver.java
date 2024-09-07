package Solver;

import Generator.Board;
import Generator.Cage;
import Generator.Point;

import java.util.*;

public class Solver extends Backtracking<Point,Integer> {
    private final int n;
    private final int[][] board;

    private List<int[][]> soluzioni = new ArrayList<>();
    private Board puzzle;
    private int numSol;
    public Solver(Board puzzle, int numSol){
        this.n=puzzle.getN();
        this.board = new int[n][n];
        this.puzzle = puzzle;
        this.numSol = numSol;
    }

    @Override
    protected boolean assegnabile(Point point, Integer v) {
        for(int i=0;i<n;i++){
            if(board[point.getM()][i]==v || board[i][point.getN()]==v)
                return false;
        }
//
        return true;
    }

    @Override
    protected void assegna(Point ps, Integer v) {
        board[ps.getM()][ps.getN()]=v;
    }

    @Override
    protected void deassegna(Point ps, Integer integer) {
        board[ps.getM()][ps.getN()]=0;
    }

    @Override
    protected void scriviSoluzione(Point point) {
        System.out.println("Soluzione trovata:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    protected List<Point> puntiDiScelta() {
        List<Point> ps = new ArrayList<>();
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                ps.add(new Point(i,j));
            }
        }
        return ps;
    }
    @Override
    protected Collection<Integer> scelte(Point point) {
        List<Integer> s = new ArrayList<>();
        for(int i=1;i<=n;i++)
            s.add(i);
        return s;
    }
    @Override
    protected boolean esisteSoluzione(Point p) {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 0) return false;
            }
        }
        if (Board.verificaCages(puzzle.getCages(), board)) return false;
        aggiungiSoluzione(board);
        numSol--;
        return true;
    }
    @Override
    protected boolean ultimaSoluzione( Point p ) {
        return numSol==0;
    }

    private void aggiungiSoluzione(int[][] board) {
        int[][] s = new int[n][n];
        for(int i=0;i<n;i++)
            s[i]=board[i].clone();
        soluzioni.add(s);
        puzzle.addSoluzione(s);
    }


    public void risolviKenken(){
        this.risolvi();
    }

}
