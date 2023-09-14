package aprendendo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class Interpreter {
    private static final Map<String,Object> variaveis = new HashMap<>();

    public static Object eval(JsonNode node){
        switch(node.get("kind").asText()) {
            case "Print": 
                return eval(node.get("value"));
            case "Str":
                return node.get("value").toString().replaceAll("\"","");
            case "Binary":
                var lhs = eval(node.get("lhs"));
                var rhs = eval(node.get("rhs"));
                
                switch(node.get("op").asText()) {
                    case "Add":
                        try {
                            return Integer.parseInt(lhs.toString()) + Integer.parseInt(rhs.toString());
                        }catch(Exception e) {
                            return lhs.toString() + rhs.toString();
                        }
                }
                break;
            case "Int":
                return node.get("value");
            
            case "If":
                var test = eval(node.get("condition"));
                
                if(test.toString().equals("true")) {
                    return eval(node.get("then"));
                }
                return eval(node.get("otherwise"));

            case "Bool":
                return node.get("value");

            case "Let":
                var name = node.get("name").get("text");
                var variavel = eval(node.get("value"));
                variaveis.put(name.toString(),variavel);
                return eval(node.get("next"));

            case "Var":
                return variaveis.get(node.get("text").toString());
            }

        return null;
    }

}
