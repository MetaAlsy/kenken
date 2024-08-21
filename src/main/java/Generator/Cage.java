package Generator;

import Operations.Operation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cage implements Serializable {
    private List<Point> points = new ArrayList<Point>();
    private int target;
    private Operations.Operation operation;

    public Cage(List<Point> points, int target, Operation operation) {
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

    public List<Point> getPoints() {
        return points;
    }

    public int getTarget() {
        return target;
    }

    public Operation getOperation() {
        return operation;
    }
}
