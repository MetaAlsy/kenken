package Generator;

import Solver.Point;
import Solver.Solver;

import java.text.BreakIterator;
import java.util.*;

public class KenKenDirector {
    Random random = new Random();
    private KenKenBuilder builder;
    private Set<Point> points;

    public KenKenDirector(KenKenBuilder b){
        this.builder=b;
        this.points=new HashSet<>();
    }
    public Board createKenken(){
        builder.randomRows();
        builder.randomColumns();
        builder.reflection();
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
                                builder.addMinus(reg);
                            else
                                builder.addDiv(reg);
                        }else {
                            if(r)
                                builder.addSum(reg);
                            else
                                builder.addMul(reg);
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
    public static void main(String ... args){
        KenKenBuilder b = new KenKenBuilder(4);
        KenKenDirector dir = new KenKenDirector(b);
        Board board = dir.createKenken();
        System.out.println(board.toString());
        Solver s = new Solver(4,board.getCages());
        s.getSoluzione();
    }
}
