package aprendendo;

import java.util.HashMap;
import java.util.Map;

import aprendendo.utils.FileUtils;

public class App {
    public static void main(String[] args) throws Exception {
        var tree = FileUtils.getFileContent("examples/fib.json");
        Map<String, Object> variables = new HashMap<>();
        System.out.println(Interpreter.eval(tree.get("expression"), variables));
    }
}
