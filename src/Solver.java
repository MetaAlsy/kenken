import java.util.*;
import java.util.stream.Collectors;

public class Solver extends Backtracking<Solver.Point,Integer>{
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
        Cage c = findCage(ps);
        c.points.put(ps,v);
    }

    @Override
    protected void deassegna(Point ps, Integer integer) {
        board[ps.getM()][ps.getN()]=0;
        Cage c = findCage(ps);
        c.points.put(ps,null);
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
            List<Integer> nums = new ArrayList<>(c.points.values()).stream().filter(Objects::nonNull).collect(Collectors.toList());
            int ris = 0;
            if(nums.size()>1){
                 switch (c.operation) {
                     case PLUS : ris=nums.stream().sorted().reduce(0, Integer::sum);
                     break;
                     case MINUS : ris=nums.stream().sorted().reduce(0, (a, b) -> a - b);
                     break;
                     case MUL : ris=nums.stream().reduce(1, (a, b) -> a * b);
                     break;
                     case DIV : ris =nums.stream().sorted().skip(1).reduce(nums.get(0), (a, b) -> a / b);
                     break;
                };
            } else {
                ris = nums.get(0);
            }
            all = (ris == c.target);
        }
        return all;
    }
    private Cage findCage(Point p){
        for(Cage c:cages){
            for(Point pc : c.points.keySet()){
                if(pc.getN()==p.getN() && pc.getM()==p.getM())
                    return c;
            }
        }
        return null;
    }
    static class Point{
        private final int m;
        private final int n;
        public Point(int x,int y){
            this.m=x;
            this.n=y;
        }
        public int getM(){
            return this.m;
        }
        public int getN(){
            return this.n;
        }
    }
    static class Cage{
        private HashMap<Point,Integer> points = new HashMap<>();
        private int target;
        private Operation operation;
    }
    enum Operation{
        PLUS,MINUS,MUL,DIV
    }
    public static void main(String ... args){
        Cage c1 = new Cage();
        c1.operation=Operation.PLUS;
        c1.target=5;
        Point p = new Point(0,0);
        Point p1 = new Point(1,0);
        Point p2 = new Point(1,1);
        c1.points.put(p,null);c1.points.put(p1,null);c1.points.put(p2,null);

        Cage c2 = new Cage();
        c2.operation=Operation.PLUS;
        c2.target=5;
        p = new Point(2,0);
        p1 = new Point(2,1);
        c2.points.put(p,null);c2.points.put(p1,null);

        Cage c3 = new Cage();
        c3.operation=Operation.PLUS;
        c3.target=5;
        p = new Point(0,2);
        p1 = new Point(1,2);
        c3.points.put(p,null);c3.points.put(p1,null);

        Cage c4 = new Cage();
        c4.operation=Operation.PLUS;
        c4.target=2;
        p = new Point(0,1);
        c4.points.put(p,null);

        Cage c5 = new Cage();
        c5.operation=Operation.PLUS;
        c5.target=1;
        p = new Point(2,2);
        c5.points.put(p,null);

        ArrayList<Cage> cages = new ArrayList<>();
        cages.add(c1);
        cages.add(c2);
        cages.add(c3);
        cages.add(c4);
        cages.add(c5);

        new Solver(3,cages).risolvi();

    }
}
