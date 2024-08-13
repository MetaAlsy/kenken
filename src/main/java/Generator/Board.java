package Generator;

import Solver.Cage;
import Solver.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {
    private  int n;
    private int[][] board;
    private List<Cage> cages;

    public Board(int[][]b,List<Cage>c) {
        this.n = b.length;
        this.board=b;
        this.cages=c;
    }
    public Board(int n){
        this.n=n;
        this.board=new int[n][n];
        this.cages=new ArrayList<>();
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
    public boolean esisteSoluzione(){
        for (Cage c : cages) {
            List<Integer> nums = new ArrayList<>();
            for(Point punt:c.getPoints()){
                if(board[punt.getM()][punt.getN()]!=0)
                    nums.add(board[punt.getM()][punt.getN()]);
            }
            if(nums.size()!=c.getPoints().size())
                return false;
            int ris = 0;
            if(nums.size()>1){
                ris=c.getOperation().calculate(nums);
            }
            else {
                ris = nums.get(0);
            }
            if(ris != c.getTarget())
                return false;
        }
        return true;
    }
    public int getN(){
        return this.n;
    }
    public List<Cage> getCages() {
        return cages;
    }

    public void setCages(List<Cage> cages) {
        this.cages = cages;
    }
}
