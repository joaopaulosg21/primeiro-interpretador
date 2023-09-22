package aprendendo.exceptions;

public class ParamsException extends RuntimeException{
    public ParamsException() {
        super("Quantidade de parametros e argumentos diferentes",null,false,false);
    }
}
