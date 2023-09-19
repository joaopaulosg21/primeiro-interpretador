package aprendendo;

import java.util.HashMap;
import java.util.Map;

import aprendendo.utils.Utils;

public class App {
    public static void main( String[] args ) throws Exception {
        var tree = Utils.getFileContent("examples/combination.json");
        //System.out.println(tree.get("expression").get("kind").asText());
        Map<String,Object> variaveis = new HashMap<>();
        System.out.println(Interpreter.eval(tree.get("expression"),variaveis));
    }
}
