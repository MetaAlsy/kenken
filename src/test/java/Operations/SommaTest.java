package Operations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SommaTest {
    @Test
    void test(){
        Somma s = new Somma();
        List<Integer> l = Arrays.asList(3,3,3);
        assertEquals(9,s.calculate(l));
    }

}