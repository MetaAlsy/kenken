package Operations;

import java.util.List;

public class Multiplicazione implements Operation{

    @Override
    public int calculate(List<Integer> numbers) {
        return numbers.stream().reduce(1, (a, b) -> a * b);
    }
}
