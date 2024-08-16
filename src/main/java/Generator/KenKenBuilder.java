package Generator;

import Operations.*;
import Solver.Cage;
import Solver.Point;

import java.util.*;
import java.util.stream.Collectors;

public class KenKenBuilder implements Builder{

    private List<Cage> cages = new ArrayList<>();
    private int[][] board;
    private int n;
    private int point;

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
    public void addCage(int target, List<Point> points,Operation opration){
        cages.add(new Cage(points,target,opration));
        point+=points.size();
    }

    public Board build(){
        if(n*n ==point)
            return new Board(new int[n][n],cages);
        else
            throw new IllegalArgumentException("Non tutti i punti appartengono alle reggioni");
    }
    public int getN(){
        return this.n;
    }

    public static void main(String ... args){
        new KenKenBuilder(4);
    }
}
