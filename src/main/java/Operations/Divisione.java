package Operations;

import java.util.Comparator;
import java.util.List;

public class Divisione implements Operation{
    private static final Divisione inst = new Divisione();
    private Divisione(){}

    public static Operation getInstance() {
        return inst;
    }

    @Override
    public int calculate(List<Integer> numbers) {
        numbers.sort(Comparator.reverseOrder());
        return numbers.stream().skip(1).reduce(numbers.get(0), (a, b) -> a / b);
    }
    public String toString(){
        return "/";
    }
}
