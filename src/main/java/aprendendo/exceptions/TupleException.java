package aprendendo.exceptions;

public class TupleException extends RuntimeException{
    public TupleException() {
        super("Valor passado não é uma tupla",null,false,false);
    }
}
