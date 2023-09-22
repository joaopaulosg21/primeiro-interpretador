package aprendendo.exceptions;

public class ConditionalException extends RuntimeException{
    public ConditionalException() {
        super("Valor passado para um if deve ser boolean",null,false,false);
    }
}
