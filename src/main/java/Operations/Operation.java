package Operations;

import java.io.Serializable;
import java.util.List;

public interface Operation extends Serializable {
    int calculate(List<Integer> numbers);
}
