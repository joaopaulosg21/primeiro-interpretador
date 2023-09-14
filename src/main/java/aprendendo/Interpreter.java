package aprendendo;

import com.fasterxml.jackson.databind.JsonNode;

public class Interpreter {

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
                    var t = eval(node.get("then"));
                    return t;
                }
                return eval(node.get("otherwise"));
            case "Bool":
                return node.get("value");
        }

        return null;
    }

}
