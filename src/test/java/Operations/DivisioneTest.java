package Operations;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class DivisioneTest {
    @Test
    void test(){
        Divisione div = new Divisione();
        List<Integer> l = Arrays.asList(2,6,36);
        assertEquals(3,div.calculate(l));
    }


}