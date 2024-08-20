package Operations;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OperationTest {
    private  StandardOperationFactory factory = new StandardOperationFactory();
    @Test
    void testSomma(){
        Somma s = factory.createSum();
        List<Integer> l = Arrays.asList(3,3,3);
        assertEquals(9,s.calculate(l));
    }
    @Test
    void testMoltiplicazione(){
        Moltiplicazione mul = factory.createMul();
        List<Integer> l = Arrays.asList(4,2,5);
        assertEquals(40,mul.calculate(l));
    }
    @Test
    void testSottrazione(){
        Operation sott = factory.createSott();
        List<Integer> l = Arrays.asList(4,2,5);
        assertEquals(1,sott.calculate(l));
    }
    @Test
    void testDivisione(){
        Divisione div = factory.createDiv();
        List<Integer> l = Arrays.asList(2,40,4);
        assertEquals(5,div.calculate(l));
    }
    @Test
    void moltiplicazionePerZero(){
        Operation mul = factory.createMul();
        List<Integer> l = Arrays.asList(3,3,0);
        Throwable exception = assertThrows(IllegalArgumentException.class,()-> mul.calculate(l));
        assertEquals("Numbers contiene 0 ",exception.getMessage());
    }
    @Test
    void divisionePerZero(){
        Divisione div = factory.createDiv();
        List<Integer> l = Arrays.asList(3,40,0);
        Throwable exception = assertThrows(IllegalArgumentException.class,()-> div.calculate(l));
        assertEquals("Numbers contiene 0 ",exception.getMessage());
    }

}