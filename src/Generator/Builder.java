package Generator;

import Operations.Operation;
import Solver.Point;

import java.util.List;

public interface Builder {
   void addSum(List<Point>p);
   void addMinus(List<Point>p);
   void addMul(List<Point>p);
   void addDiv(List<Point>p);
   void randomRows();
   void randomColumns();
   void reflection();
}
