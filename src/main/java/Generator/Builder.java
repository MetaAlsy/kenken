package Generator;

import Operations.Operation;
import Solver.Point;

import java.util.List;

public interface Builder {
   void addCage(int target, List<Point> points, Operation operation);
}
