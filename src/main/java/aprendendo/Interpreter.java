package aprendendo;

import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import aprendendo.utils.InterpreterUtils;

@SuppressWarnings("unchecked")
public class Interpreter {
    public static Object eval(JsonNode node, Map<String, Object> variables) {
        Object[] args = null;
        switch (node.get("kind").asText()) {
            case "Print":
                return eval(node.get("value"), variables);
            case "Str":
                return node.get("value").toString().replaceAll("\"", "");
            case "Binary":
                var lhs = eval(node.get("lhs"), variables);
                var rhs = eval(node.get("rhs"), variables);

                return InterpreterUtils.binaryOp(lhs, rhs, node.get("op").asText());

            case "Int":
                return node.get("value");

            case "If":
                var test = eval(node.get("condition"), variables);

                if (test.toString().equals("true")) {
                    return eval(node.get("then"), variables);
                } else {
                    return eval(node.get("otherwise"), variables);
                }

            case "Bool":
                return node.get("value");

            case "Let":
                var name = node.get("name").get("text").asText();
                var variavel = eval(node.get("value"), variables);
                variables.put(name.toString(), variavel);
                return eval(node.get("next"), variables);

            case "Var":
                return variables.get(node.get("text").asText());

            case "Function":
                return InterpreterUtils.funcResolver(args, variables, node);

            case "Call":
                int size = node.get("arguments").size();
                args = new Object[size];
                for (int i = 0; i < size; i++) {
                    args[i] = eval(node.get("arguments").get(i), variables);
                }
                Function<Object[], Object> function = (Function<Object[], Object>) eval(node.get("callee"), variables);
                return function.apply(args);
        }

        return null;
    }
}
