package Generator;

import Solver.Cage;
import Solver.Point;

import java.util.*;

public class KenKenBuilder implements Builder{
    private Random random = new Random();
    private List<Cage> cages = new ArrayList<>();
    private int n;

    @Override
    public int[][] createBoard(int n) {
        this.n=n;
        int[][] board = new int[n][n];
        for(int i=0;i<n;i++){
            board[0][i]=i+1;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j]=board[i-1][(j+1)%n];
            }
        }

        randomRows(board);
        randomColumns(board);
        reflection(board);
        System.out.println("Soluzione trovata:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        return board;
    }



    private void randomRows(int[][] board){
        for(int i=n-1;i>0;i--){
            int r = random.nextInt(i+1);
            int[] t = board[i];
            board[i]=board[r];
            board[r]=t;
        }
    }
    public void randomColumns(int[][] board){
        for(int j=n-1;j>0;j-- ){
            int r = random.nextInt(j+1);
            int[] t = board[j];
            board[j]=board[r];
            board[r]=t;
        }
    }
    public void reflection(int[][] board){
        for(int i=0;i<n/2;i++){
            int [] t= board[i];
            board[i]=board[n-1-i];
            board[n-1-i]=t;
        }
    }
    public void init(){
        Set<Point> punti = new HashSet<>();
        //inizializzazione alla KMeans
        while(punti.size()<n){
            punti.add(new Point(random.nextInt(n), random.nextInt(n)));
        }
        for(Point p:punti){
            HashMap<Point,Integer> map = new HashMap<>();
            map.put(p,null);
            cages.add(new Cage(map,0,null));
        }
    }

    public static void main(String ... args){
        new KenKenBuilder().createBoard(3);
    }
}
