package Operations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultiplicazioneTest {
    @Test
    void test(){
        Multiplicazione mul = new Multiplicazione();
        List<Integer> l = Arrays.asList(2,3,4,5);
        assertEquals(120,mul.calculate(l));
    }

}