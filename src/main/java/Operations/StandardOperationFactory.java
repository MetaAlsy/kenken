package Operations;

public class StandardOperationFactory implements OperationFactory {
    @Override
    public Operation createSum() {
        return Somma.getInstance();
    }

    @Override
    public Operation createMul() {
        return Multiplicazione.getInstance();
    }

    @Override
    public Operation createSott() {
        return Sottrazione.getInstance();
    }

    @Override
    public Operation createDiv() {
        return Divisione.getInstance();
    }
}
