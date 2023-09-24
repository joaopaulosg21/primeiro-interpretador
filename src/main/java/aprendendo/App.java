package aprendendo;

import java.nio.file.Path;
import java.nio.file.Paths;

import aprendendo.interpreter.ExecuteInterpreter;

public class App {
    public static void main(String[] args) throws Exception {
        Path path = Paths.get("/var/rinha/source.rinha.json");
        ExecuteInterpreter execute = new ExecuteInterpreter(path);
        execute.executeJson();    
    }
}
