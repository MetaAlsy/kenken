package Operations;

import java.util.List;

public class Multiplicazione implements Operation{
    private static final Multiplicazione inst = new Multiplicazione();
    private Multiplicazione(){}

    public static Operation getInstance() {
        return inst;
    }

    @Override
    public int calculate(List<Integer> numbers) {
        return numbers.stream().reduce(1, (a, b) -> a * b);
    }
    public String toString(){
        return "*";
    }
}
