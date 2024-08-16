package Operations;

import java.util.List;

public class Somma implements Operation{
    private static final Somma inst = new Somma();
    private Somma(){}

    public static Operation getInstance() {
        return inst;
    }

    @Override
    public int calculate(List<Integer> numbers) {
        return numbers.stream().reduce(0, Integer::sum);
    }

    @Override
    public String toString() {
        return "+";
    }
}
