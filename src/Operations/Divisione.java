package Operations;

import java.util.Comparator;
import java.util.List;

public class Divisione implements Operation{

    @Override
    public int calculate(List<Integer> numbers) {
        return numbers.stream().sorted(Comparator.reverseOrder()).skip(1).reduce(numbers.get(0), (a, b) -> a / b);
    }
}
