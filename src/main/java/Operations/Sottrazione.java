package Operations;

import java.util.Comparator;
import java.util.List;

public class Sottrazione implements Operation{
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
