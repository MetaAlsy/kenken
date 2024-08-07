package Generator;

import Solver.Cage;
import Solver.Point;

import java.util.List;

public class Board {
    private final int n;
    private int[][] board;
    private List<Cage> cages;

    public Board(int[][]b,List<Cage>c) {
        this.n = b.length;
        this.board=b;
        this.cages=c;
    }

    public int[][] getBoard() {
        return board;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder(256);
        StringBuilder sb2 = new StringBuilder(256);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                Point p= new Point(i,j);
                sb2.append(board[i][j]+" ");
                for(Cage c: cages){
                   if(c.getPoints().contains(p))
                       sb.append(c.getTarget()+" ");
                }
            }
            sb.append("\n");
            sb2.append("\n");
        }
        return sb.toString()+"\n"+sb2.toString();
    }

    public List<Cage> getCages() {
        return cages;
    }

    public void setCages(List<Cage> cages) {
        this.cages = cages;
    }
}
