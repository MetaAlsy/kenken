package Operations;

import java.util.Comparator;
import java.util.List;

public class Sottrazione implements Operation{
    private static final Sottrazione inst = new Sottrazione();
    private Sottrazione(){}
    public static Operation getInstance() {
        return inst;
    }

    @Override
    public int calculate(List<Integer> numbers) {
        numbers.sort(Comparator.reverseOrder());
        return numbers.stream().reduce(0, (a, b) -> Math.abs(a - b));
    }

    @Override
    public String toString() {
        return "-";
    }
}
