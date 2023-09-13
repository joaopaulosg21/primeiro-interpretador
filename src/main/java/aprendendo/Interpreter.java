package aprendendo;

import com.fasterxml.jackson.databind.JsonNode;

public class Interpreter {

    public static Object eval(JsonNode node){
        
        switch(node.get("kind").asText()) {
            case "Print": 
                return eval(node.get("value"));
            case "Str":
                return node.get("value");
            case "Binary":
                var lhs = eval(node.get("lhs"));
                var rhs = eval(node.get("rhs"));
                
                switch(node.get("op").asText()) {
                    case "Add":
                        return Integer.parseInt(lhs.toString()) + Integer.parseInt(rhs.toString());
                }
                break;
            case "Int":
                return node.get("value");
        }

        return null;
    }

}
