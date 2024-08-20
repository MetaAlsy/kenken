package Operations;

import java.util.List;

public class Moltiplicazione implements Operation{
    private static final Moltiplicazione inst = new Moltiplicazione();
    private Moltiplicazione(){}

    public static Moltiplicazione getInstance() {
        return inst;
    }

    @Override
    public int calculate(List<Integer> numbers) {
        if(numbers.contains(0))
            throw new IllegalArgumentException("Numbers contiene 0 ");
        return numbers.stream().reduce(1, (a, b) -> a * b);
    }
    public String toString(){
        return "*";
    }
}
