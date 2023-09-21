package aprendendo.interpreter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import aprendendo.utils.FileUtils;

public class ExecuteInterpreter {
    
    private Path filePath;

    public ExecuteInterpreter(Path filePath) {
        this.filePath = filePath;
        filePath.normalize();
    }

    public Path getFilePath() {
        return filePath;
    }
    

    public void executeJson() {
        var tree = FileUtils.getFileContent(filePath.toString());
        Map<String, Object> variables = new HashMap<>();

        //long inicio = System.currentTimeMillis();
        System.out.println(Interpreter.eval(tree.get("expression"), variables));
        //long fim = System.currentTimeMillis();
        //double tempo = (fim - inicio)/1000d;
        //System.out.println(String.format("Interpretador rodou em : %.2f segundos",tempo));
    }

    public void executeOther() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process p = runtime.exec("./rinha " + filePath.toString());
            String content = new String(p.getInputStream().readAllBytes());
            var tree = FileUtils.toJsonNode(content);
            Map<String, Object> variables = new HashMap<>();
            System.out.println(Interpreter.eval(tree.get("expression"), variables));
        }catch(IOException e) {
            e.printStackTrace();
        }
    }
}
