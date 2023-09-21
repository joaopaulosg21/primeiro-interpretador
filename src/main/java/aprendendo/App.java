package aprendendo;

import java.nio.file.Paths;

import aprendendo.interpreter.ExecuteInterpreter;
import aprendendo.utils.FileUtils;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length > 0 && args.length == 1) {
            ExecuteInterpreter execute = new ExecuteInterpreter(Paths.get(args[0]));
            switch(FileUtils.fileType(execute.getFilePath())) {
                case "json" -> execute.executeJson();
                case "rinha" -> execute.executeOther();
            }
        }else{
            throw new RuntimeException("Args invalidos");
        }        
    }
}
