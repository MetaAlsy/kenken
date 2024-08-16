package Operations;

public class OperationFactory {

    public static Operation createOperation(String tipo){
        switch(tipo.toLowerCase()) {
            case "somma":
                return Somma.getInstance();
            case "moltiplicazione" :
                return Multiplicazione.getInstance();
            case "sottrazione" :
                return Sottrazione.getInstance();
            case "divisione" :
                return Divisione.getInstance();
            default:
                throw new IllegalArgumentException("Operazione non risconosciuta: "+tipo);
        }
    }
}
