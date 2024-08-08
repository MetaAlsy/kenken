package Operations;

import java.util.List;

public class Sottrazione implements Operation{
    @Override
    public int calculate(List<Integer> numbers) {
        return numbers.stream().sorted().reduce(0, (a, b) -> Math.abs(a - b));
    }

    @Override
    public String toString() {
        return "-";
    }
}
