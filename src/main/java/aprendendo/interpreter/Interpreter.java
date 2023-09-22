package aprendendo.interpreter;

import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import aprendendo.exceptions.ConditionalException;
import aprendendo.exceptions.ParamsException;
import aprendendo.exceptions.TupleException;
import aprendendo.types.Tuple;
import aprendendo.utils.InterpreterUtils;

@SuppressWarnings("unchecked")
public class Interpreter {
    private static int paramSize = 0;
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

                if(!(test instanceof Boolean)) throw new ConditionalException();

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
                paramSize = node.get("parameters").size();
                return InterpreterUtils.funcResolver(args, variables, node);

            case "Call":
                int size = node.get("arguments").size();

                if(size != paramSize) throw new ParamsException();
                args = new Object[size];
                for (int i = 0; i < size; i++) {
                    args[i] = eval(node.get("arguments").get(i), variables);
                }
                Function<Object[], Object> function = (Function<Object[], Object>) eval(node.get("callee"), variables);
                return function.apply(args);
            case "Tuple":
                var first = eval(node.get("first"), variables);
                var second = eval(node.get("second"), variables);

                Tuple tuple = new Tuple(first, second);

                return tuple;
            case "First":
                var firstValue = eval(node.get("value"), variables);

                if (firstValue instanceof Tuple) {
                    Tuple tupleValue = (Tuple) firstValue;
                    return tupleValue.getFirst();
                } else {
                    throw new TupleException();
                }
            case "Second":
                var secondValue = eval(node.get("value"), variables);

                if (secondValue instanceof Tuple) {
                    Tuple tupleValue = (Tuple) secondValue;
                    return tupleValue.getSecond();
                } else {
                    throw new TupleException();
                }
        }

        return null;
    }
}
