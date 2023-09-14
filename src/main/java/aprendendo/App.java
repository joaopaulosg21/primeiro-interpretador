package aprendendo;

import aprendendo.utils.Utils;

public class App {
    public static void main( String[] args ) throws Exception {
        var tree = Utils.getFileContent("examples/print.json");
        //System.out.println(tree.get("expression").get("kind").asText());
        System.out.println(Interpreter.eval(tree.get("expression")));;
    }
}
