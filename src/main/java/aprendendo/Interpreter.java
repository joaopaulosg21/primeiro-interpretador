package aprendendo;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
public class Interpreter {
    public static Object eval(JsonNode node,Map<String,Object> variaveis){
        switch(node.get("kind").asText()) {
            case "Print": 
                return eval(node.get("value"),variaveis);
            case "Str":
                return node.get("value").toString().replaceAll("\"","");
            case "Binary":
                var lhs = eval(node.get("lhs"),variaveis); 
                var rhs = eval(node.get("rhs"),variaveis); 
                
                return binaryOp(lhs,rhs,node.get("op").asText());
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
                return new Callable() {
                    public Object call(Object[] arr) {
                        int i = 0;
                        Map<String,Object> localEnv = new HashMap<>();
                        localEnv.putAll(variaveis);
                        for(var value : arr) {
                            localEnv.put(node.get("parameters").get(i).get("text").asText(),value);
                            i++;
                        }
                        var result = eval(node.get("value"), localEnv);
                        return result;
                    }
                };
            case "Call":
                int size = node.get("arguments").size();
                Object[] arr = new Object[size];
                for(int i=0;i < size;i++) {
                    arr[i] = eval(node.get("arguments").get(i), variaveis);
                }
                Callable callable = (Callable) eval(node.get("callee"), variaveis);
                return callable.call(arr);
        }

        return null;
    }

    public static Object binaryOp(Object lhs, Object rhs, String op) {
         switch(op) {
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
        return null;
    }
}
