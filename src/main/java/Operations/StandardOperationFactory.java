package Operations;

public class StandardOperationFactory implements OperationFactory {
    @Override
    public Somma createSum() {
        return Somma.getInstance();
    }

    @Override
    public Moltiplicazione createMul() {
        return Moltiplicazione.getInstance();
    }

    @Override
    public Sottrazione createSott() {
        return Sottrazione.getInstance();
    }

    @Override
    public Divisione createDiv() {
        return Divisione.getInstance();
    }
}
