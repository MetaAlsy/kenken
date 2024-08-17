package Generator;

import Operations.StandardOperationFactory;
import Solver.Point;
import Solver.Solver;

import java.util.*;

public class KenKenDirector {
    private Random random = new Random();
    private KenKenBuilder builder;
    private Set<Point> points;
    private int n;
    private int[][] board;
    private StandardOperationFactory standardOperationFactory;

    public KenKenDirector(KenKenBuilder b){
        this.builder=b;
        this.points=new HashSet<>();
        this.n=b.getN();
        this.board=new int[n][n];
        this.standardOperationFactory = new StandardOperationFactory();
        for(int i=0;i<n;i++){
            board[0][i]=i+1;
        }
        for(int i=1;i<n;i++){
            for(int j=0;j<n;j++){
                board[i][j]=board[i-1][(j+1)%n];
            }
        }
    }
    public Board createKenken(){
        randomRows();
        randomColumns();
        reflection();
        for(int i=0;i<builder.getN();i++){
            for(int j=0;j< builder.getN();j++){
                Point p = new Point(i,j);
                if(!points.contains(p)){
                    List<Point> reg = new ArrayList<>();
                    esplora(p,reg);
                    if(!reg.isEmpty()){
                        boolean r = random.nextBoolean();
                        if(reg.size()==2){
                            if(r)
                                addMinus(reg);
                            else
                                addDiv(reg);
                        }else {
                            if(r)
                                addSum(reg);
                            else
                                addMul(reg);
                        }
                    }
                }
            }
        }
        return builder.build();
    }

    private void esplora(Point p, List<Point> reg) {
        Stack<Point> stack = new Stack<>();
        stack.push(p);
        int n = random.nextInt(builder.getN())+1;
        while(!stack.isEmpty() && reg.size()< n){
            Point cand = stack.pop();
            if(points.contains(cand))
                continue;
            points.add(cand);
            reg.add(cand);
            List<Point> adiacenti = getAdiacenti(cand.getM(),cand.getN());
            for(Point p1:adiacenti){
                if(!points.contains(p1))
                    stack.push(p1);
            }
        }
    }

    private List<Point> getAdiacenti(int m, int n) {
        List<Point> ris = new ArrayList<>();
        if(m>0)
            ris.add(new Point(m-1,n));
        if(m<builder.getN()-1)
            ris.add(new Point(m+1,n));
        if(n>0)
            ris.add(new Point(m,n-1));
        if (n< builder.getN()-1)
            ris.add(new Point(m,n+1));
        return ris;
    }
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


    public void addSum(List<Point> points) {
        int s=0;
        for(Point p:points){
            s+=board[p.getM()][p.getN()];
        }
        builder.addCage(s,points, standardOperationFactory.createSum());
    }


    public void addMinus(List<Point> points) {
        List<Integer> l= new ArrayList<>();
        for(Point p:points)
            l.add(board[p.getM()][p.getN()]);
        l.sort(Comparator.reverseOrder());
        builder.addCage(l.stream().reduce(0, (a, b) -> Math.abs(a - b)),points, standardOperationFactory.createSott());
    }


    public void addMul(List<Point> points) {
        int m = 1;
        for(Point p:points){
            m = board[p.getM()][p.getN()]*m;
        }
        builder.addCage(m,points, standardOperationFactory.createMul());
    }


    public void addDiv(List<Point> points) {
        List<Integer> l= new ArrayList<>();
        for(Point p:points)
            l.add(board[p.getM()][p.getN()]);
        l.sort(Comparator.reverseOrder());
        builder.addCage(l.stream().skip(1).reduce(l.get(0), (a, b) -> a / b),points, standardOperationFactory.createDiv());
    }
    public static void main(String ... args){
        KenKenBuilder b = new KenKenBuilder(4);
        KenKenDirector dir = new KenKenDirector(b);
        Board board = dir.createKenken();
        System.out.println(board.toString());
        Solver s = new Solver(4,board.getCages());
        s.getSoluzione();
    }
}
