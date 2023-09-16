package aprendendo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
@SuppressWarnings("unchecked")
public class Interpreter {
    //private static final Map<String,Object> variaveis = new HashMap<>();
    private static final Map<String,Object> params = new HashMap<>();
    private static JsonNode jsonNode = null;
    public static Object eval(JsonNode node,Map<String,Object> variaveis){
        switch(node.get("kind").asText()) {
            case "Print": 
                return eval(node.get("value"),variaveis);
            case "Str":
                return node.get("value").toString().replaceAll("\"","");
            case "Binary":
                var lhs = eval(node.get("lhs"),variaveis); 
                var rhs = eval(node.get("rhs"),variaveis); 
                
                switch(node.get("op").asText()) {
                    case "Add":
                        try {
                            return Integer.parseInt(lhs.toString()) + Integer.parseInt(rhs.toString());
                        }catch(Exception e) {
                            return lhs.toString() + rhs.toString();
                        }
                    case "Sub":
                        try {
                            return Integer.parseInt(lhs.toString()) - Integer.parseInt(rhs.toString());
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                    case "Eq":
                        try {
                            return Integer.parseInt(lhs.toString()) == Integer.parseInt(rhs.toString());
                        }catch(Exception e) {
                            return lhs.toString().equals(rhs.toString());
                        }
                    case "Lt":
                        try {
                            return Integer.parseInt(lhs.toString()) < Integer.parseInt(rhs.toString());
                        }catch(Exception e) {
                            e.printStackTrace();
                        }
                }
                break;
            case "Int":
                return node.get("value");
            
            case "If":
                var test = eval(node.get("condition"),variaveis);
                
                if(test.toString().equals("true")) {
                    return eval(node.get("then"),variaveis);
                }else{
                    return eval(node.get("otherwise"),variaveis);
                }

            case "Bool":
                return node.get("value");

            case "Let":
                var name = node.get("name").get("text").asText();
                var variavel = eval(node.get("value"),variaveis);
                variaveis.put(name.toString(),variavel);
                return eval(node.get("next"),variaveis);

            case "Var":
                return variaveis.get(node.get("text").asText());
            
            case "Function":
                node.get("parameters").forEach(((item) -> {
                    params.put(item.get("text").asText(),null);
                }));
                jsonNode = node.get("value");
                return params;
                
            case "Call":
                Map<String,Object> param =(Map<String,Object>) eval(node.get("callee"),variaveis);
                
                int i = 0;
                for(var value : param.keySet()) {
                    param.put(value,eval(node.get("arguments").get(i),variaveis));
                    i++;
                }
                for(var value : param.keySet()) {
                    variaveis.put(value,param.get(value));
                }
                return eval(jsonNode,variaveis);       
        }

        return null;
    }

}
