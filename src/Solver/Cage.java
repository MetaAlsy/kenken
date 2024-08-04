package Solver;

import Operations.Operation;

import java.util.HashMap;

public class Cage {
    private HashMap<Point,Integer> points = new HashMap<>();
    private int target;
    private Operations.Operation operation;

    public Cage(HashMap<Point, Integer> points, int target, Operation operation) {
        this.points = points;
        this.target = target;
        this.operation = operation;
    }
    public void setTarget(int target) {
        this.target = target;
    }

    public void setOperation(Operations.Operation operation) {
        this.operation = operation;
    }

    public HashMap<Point, Integer> getPoints() {
        return points;
    }

    public int getTarget() {
        return target;
    }

    public Operation getOperation() {
        return operation;
    }
}
