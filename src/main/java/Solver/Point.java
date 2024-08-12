package Solver;

import java.util.Objects;

public class Point {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return m == point.m && n == point.n;
    }

    @Override
    public int hashCode() {
        return Objects.hash(m, n);
    }
}
