package Solver;

import Operations.Multiplicazione;
import Operations.Somma;

import java.util.*;
import java.util.stream.Collectors;

public class Solver extends Backtracking<Point,Integer> {
    private final int n;
    private final int[][] board;
    private final List<Cage> cages;
    public Solver(int n, List<Cage> cages){
        this.n=n;
        this.board = new int[n][n];
        this.cages = cages;
    }

    @Override
    protected boolean assegnabile(Point point, Integer v) {
        for(int i=0;i<n;i++){
            if(/*board[point.getM()][point.getN()]!=0 || */board[point.getM()][i]==v || board[i][point.getN()]==v)
                return false;
        }
//        Cage c = findCage(point);
//        if(c.points.containsValue(v))
//            return false;
//        List<Integer> nums = new ArrayList<>(c.points.values()).stream().filter(Objects::nonNull).collect(Collectors.toList());
//        nums.add(v);
//        int ris = 0;
//        if(nums.size()>1){
//             switch (c.operation) {
//                 case PLUS : ris=nums.stream().sorted().reduce(0, Integer::sum);
//                 break;
//                 case MINUS : ris=nums.stream().sorted().reduce(0, (a, b) -> a - b);
//                 break;
//                 case MUL : ris=nums.stream().reduce(1, (a, b) -> a * b);
//                 break;
//                 case DIV : ris =nums.stream().sorted().skip(1).reduce(nums.get(0), (a, b) -> a / b);
//                 break;
//            };
//        } else {
//            ris = nums.get(0);
//        }
//        switch (c.operation){
//            case PLUS, MUL: return ris<=c.target;
//
//            case MINUS, DIV: return ris>=c.target;
//
//            default:
//                throw new IllegalStateException("Unexpected value: " + c.operation);
//        }
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
//        for (int row = 0; row < n; row++) {
//            for (int col = 0; col < n; col++) {
//                if (board[row][col] == 0) return false;
//            }
//        }
//        Cage c = findCage(p);
//        List<Integer> nums = new ArrayList<>(c.points.values()).stream().filter(Objects::nonNull).collect(Collectors.toList());
//        int ris = 0;
//        if(nums.size()>1){
//             switch (c.operation) {
//                 case PLUS : ris=nums.stream().sorted().reduce(0, Integer::sum);
//                 break;
//                 case MINUS : ris=nums.stream().sorted().reduce(0, (a, b) -> a - b);
//                 break;
//                 case MUL : ris=nums.stream().reduce(1, (a, b) -> a * b);
//                 break;
//                 case DIV : ris =nums.stream().sorted().skip(1).reduce(nums.get(0), (a, b) -> a / b);
//                 break;
//            };
//        } else {
//            ris = nums.get(0);
//        }
//        return ris == c.target;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (board[row][col] == 0) return false;
            }
        }
        boolean all = true;
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
    public void getSoluzione(){
        this.risolvi();
    }
//    private Cage findCage(Point p){
//        for(Cage c:cages){
//            for(Point pc : c.getPoints().keySet()){
//                if(pc.equals(p))
//                    return c;
//            }
//        }
//        return null;
//    }
    public static void main(String ... args){
//        Cage c1 = new Cage();//
//        c1.operation=Operations.Operation.MUL;
//        c1.target=4;
//        Point p = new Point(2,2);
//        Point p1 = new Point(3,2);
//        Point p2 = new Point(3,3);
//        c1.points.put(p,null);c1.points.put(p1,null);c1.points.put(p2,null);
//
//        Cage c2 = new Cage();//
//        c2.operation=Operations.Operation.DIV;
//        c2.target=2;
//        p = new Point(0,0);
//        p1 = new Point(0,1);
//        c2.points.put(p,null);c2.points.put(p1,null);
//
//        Cage c3 = new Cage();//
//        c3.operation=Operations.Operation.PLUS;
//        c3.target=7;
//        p = new Point(0,2);
//        p1 = new Point(1,2);
//        c3.points.put(p,null);c3.points.put(p1,null);
//
//        Cage c4 = new Cage();//
//        c4.operation=Operations.Operation.PLUS;
//        c4.target=4;
//        p = new Point(0,3);
//        c4.points.put(p,null);
//
//        Cage c5 = new Cage();//
//        c5.operation=Operations.Operation.MINUS;
//        c5.target=1;
//        p = new Point(1,0);
//        p1 = new Point(2,0);
//        c5.points.put(p,null);c5.points.put(p1,null);
//
//        Cage c6 = new Cage();//
//        c6.operation=Operations.Operation.MINUS;
//        c6.target=3;
//        p = new Point(1,1);
//        p1 = new Point(2,1);
//        c6.points.put(p,null);c6.points.put(p1,null);
//
//        Cage c7 = new Cage();//
//        c7.operation=Operations.Operation.MINUS;
//        c7.target=1;
//        p = new Point(3,0);
//        p1 = new Point(3,1);
//        c7.points.put(p,null);c7.points.put(p1,null);
//
//        Cage c8 = new Cage();//
//        c8.operation=Operations.Operation.MINUS;
//        c8.target=2;
//        p = new Point(1,3);
//        p1 = new Point(2,3);
//        c8.points.put(p,null);c8.points.put(p1,null);
//
//        ArrayList<Cage> cages = new ArrayList<>();
//        cages.add(c1);
//        cages.add(c2);
//        cages.add(c3);
//        cages.add(c4);
//        cages.add(c5);
//        cages.add(c6);
//        cages.add(c7);
//        cages.add(c8);
//n2

        Point p = new Point(1,2);
        Point p1 = new Point(2,2);
        Point p2 = new Point(1,3);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c1 = new Cage(points,4,new Multiplicazione());



        p = new Point(1,0);
        p1 = new Point(1,1);
        p2 = new Point(2,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c2 = new Cage(points,10,new Somma());//



        p = new Point(0,0);
        p1 = new Point(0,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c3 = new Cage(points,8,new Multiplicazione());//



        p = new Point(2,0);
        points = new ArrayList<>();
        points.add(p);
        Cage c4 = new Cage(points,1,new Somma());//

        p = new Point(0,2);
        p1 = new Point(0,3);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c5 = new Cage(points,4,new Somma());//



        p = new Point(3,0);
        p1 = new Point(3,1);
        points = new ArrayList<>();
        points.add(p);points.add(p1);
        Cage c6 = new Cage(points,3,new Somma());//



        p = new Point(3,2);
        p1 = new Point(3,3);
        p2 = new Point(2,3);
        points = new ArrayList<>();
        points.add(p);points.add(p1);points.add(p2);
        Cage c7 = new Cage(points,11,new Somma());//

//        Cage c8 = new Cage();//
//        c8.operation=Operations.Operation.MINUS;
//        c8.target=2;
//        p = new Point(1,3);
//        p1 = new Point(2,3);
//        c8.points.put(p,null);c8.points.put(p1,null);

        ArrayList<Cage> cages = new ArrayList<>();
        cages.add(c1);
        cages.add(c2);
        cages.add(c3);
        cages.add(c4);
        cages.add(c5);
        cages.add(c6);
        cages.add(c7);
        //cages.add(c8);



        new Solver(4,cages).risolvi();

    }
}
