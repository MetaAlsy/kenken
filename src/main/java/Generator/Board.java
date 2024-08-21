package Generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board extends Subject implements Serializable {
    private  int n;
    private int[][] board;
    private List<Cage> cages;
    private List<int[][]> soluzioni;
    private int index;

    public Board(int[][]b,List<Cage>c) {
        this.n = b.length;
        this.board=b;
        this.cages=c;
        this.soluzioni = new ArrayList<>();
        this.index = 0;
    }
    public Board(int n){
        this.n=n;
        this.board=new int[n][n];
        this.cages=new ArrayList<>();
        this.soluzioni= new ArrayList<>();
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
    public void addCage(Cage cage){
        cages.add(cage);
        notifyObservers();
    }

    public void setCages(List<Cage> cages) {
        this.cages = cages;
        notifyObservers();
    }
    public void inserisciValore(int m,int n, String v){
        if(v!=null && !v.isEmpty()){
            int val=Integer.parseInt(v);
            if(val<=this.n && val>0){
                board[m][n]=val;
                notifyObservers();
            }
        }
    }
    public void setBoard(int[][] board){
        this.board=board;
        notifyObservers();
    }
    public void addSoluzione(int[][] s){
        if(!containsSol(s))
            this.soluzioni.add(s);
    }
    public int getNumSoluzioni(){
        if(soluzioni!=null && !soluzioni.isEmpty())
            return soluzioni.size();
        return 0;
    }
    public boolean containsSol(int[][] s){
        for(int[][] sol : soluzioni){
            if(Arrays.deepEquals(sol,s))
                return true;
        }
        return false;
    }
    public void prossimaSol(){
        if(soluzioni!=null && !soluzioni.isEmpty() && index<soluzioni.size()-1){
            index++;
            board = soluzioni.get(index);
            notifyObservers();
        }
    }
    public void precedenteSol(){
        if(soluzioni!=null && !soluzioni.isEmpty() && index>0){
            index--;
            board = soluzioni.get(index);
            notifyObservers();
        }
    }
    public void primaSol(){
        if(soluzioni!=null && !soluzioni.isEmpty()){
            index = 0;
            board = soluzioni.get(index);
            notifyObservers();
        }
    }
    public int getIndex(){
        return index;
    }
    public void notifica(){
        notifyObservers();
    }
}
