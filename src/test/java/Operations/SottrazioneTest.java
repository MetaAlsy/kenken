package Operations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SottrazioneTest {
    @Test
    void test(){
        Sottrazione s = new Sottrazione();
        List<Integer> l = Arrays.asList(2,40,12,6);
        assertEquals(20,s.calculate(l));
    }

}