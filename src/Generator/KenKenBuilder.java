package Generator;

import Operations.Divisione;
import Operations.Multiplicazione;
import Operations.Somma;
import Operations.Sottrazione;
import Solver.Cage;
import Solver.Point;

import java.util.*;
import java.util.stream.Collectors;

public class KenKenBuilder implements Builder{
    private Random random = new Random();
    private List<Cage> cages = new ArrayList<>();
    private int[][] board;
    private int n;

    public KenKenBuilder(int n){
        this.n=n;
        this.board=new int[n][n];
        for(int i=0;i<n;i++){
            board[0][i]=i+1;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j]=board[i-1][(j+1)%n];
            }
        }
//        randomRows();
//        randomColumns();
//        reflection();
    }
//    @Override
//    public void board(int n) {
//        int[][] board = new int[n][n];
//        for(int i=0;i<n;i++){
//            board[0][i]=i+1;
//        }
//        for(int i=1;i<n;i++){
//            for(int j=0;j<n;j++){
//                board[i][j]=board[i-1][(j+1)%n];
//            }
//        }
//
//        randomRows(board);
//        randomColumns(board);
//        reflection(board);
//        System.out.println("Soluzione trovata:");
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
    public void randomRows(){
        for(int i=n-1;i>0;i--){
            int r = random.nextInt(i+1);
            int[] t = board[i];
            board[i]=board[r];
            board[r]=t;
        }
    }
    public void randomColumns(){
        for(int j=n-1;j>0;j-- ){
            int r = random.nextInt(j+1);
            int[] t = board[j];
            board[j]=board[r];
            board[r]=t;
        }
    }
    public void reflection(){
        for(int i=0;i<n/2;i++){
            int [] t= board[i];
            board[i]=board[n-1-i];
            board[n-1-i]=t;
        }
    }

    @Override
    public void addSum(List<Point> points) {
        int s=0;
        for(Point p:points){
            s+=board[p.getM()][p.getN()];
        }
        cages.add(new Cage(points,s,new Somma()));
    }

    @Override
    public void addMinus(List<Point> points) {
        List<Integer> l= new ArrayList<>();
        for(Point p:points)
            l.add(board[p.getM()][p.getN()]);
        cages.add(new Cage(points,l.stream().sorted().reduce(0, (a, b) -> Math.abs(a - b)),new Sottrazione()));
    }

    @Override
    public void addMul(List<Point> points) {
        int m = 1;
        for(Point p:points){
            m = board[p.getM()][p.getN()]*m;
        }
        cages.add(new Cage(points,m,new Multiplicazione()));
    }

    @Override
    public void addDiv(List<Point> points) {
        List<Integer> l= new ArrayList<>();
        for(Point p:points)
            l.add(board[p.getM()][p.getN()]);
        cages.add(new Cage(points,l.stream().sorted(Comparator.reverseOrder()).skip(1).reduce(l.get(0), (a, b) -> a / b),new Divisione()));
    }
    public Board build(){
        return new Board(board,cages);
    }
    public int getN(){
        return this.n;
    }

    public static void main(String ... args){
        new KenKenBuilder(4);
    }
}
