package aprendendo;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import aprendendo.utils.FileUtils;

public class App {
    public static void main(String[] args) throws Exception {
        if(args.length > 0 && args.length == 1) {
            Path path = Paths.get(args[0]);
            path.normalize();
            if(FileUtils.isValidFile(path)) {
                execute(path);
            }
        }else{
            throw new RuntimeException("Args invalidos");
        }        
    }

    private static void execute(Path filePath) {
        var tree = FileUtils.getFileContent(filePath.toString());
        Map<String, Object> variables = new HashMap<>();

        //long inicio = System.currentTimeMillis();
        System.out.println(Interpreter.eval(tree.get("expression"), variables));
        //long fim = System.currentTimeMillis();
        //double tempo = (fim - inicio)/1000d;
        //System.out.println(String.format("Interpretador rodou em : %.2f segundos",tempo));
    }
}
