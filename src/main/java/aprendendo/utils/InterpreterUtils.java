package aprendendo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;

import aprendendo.Interpreter;

public class InterpreterUtils {

    public static Object binaryOp(Object lhs, Object rhs, String op) {
        switch (op) {
            case "Add":
                try {
                    return Long.parseLong(lhs.toString()) + Long.parseLong(rhs.toString());
                } catch (Exception e) {
                    return lhs.toString() + rhs.toString();
                }
            case "Sub":
                try {
                    return Integer.parseInt(lhs.toString()) - Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Mul":
                try {
                    return Integer.parseInt(lhs.toString()) * Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Div":
                try {
                    return Integer.parseInt(lhs.toString()) / Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Rem":
                try {
                    return Integer.parseInt(lhs.toString()) % Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Eq":
                try {
                    return Integer.parseInt(lhs.toString()) == Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    return lhs.toString().equals(rhs.toString());
                }
            case "Neq":
                try {
                    return Integer.parseInt(lhs.toString()) != Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    return lhs.toString() != rhs.toString();
                }
            case "Lt":
                try {
                    return Integer.parseInt(lhs.toString()) < Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Gt":
                try {
                    return Integer.parseInt(lhs.toString()) > Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Lte":
                try {
                    return Integer.parseInt(lhs.toString()) <= Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Gte":
                try {
                    return Integer.parseInt(lhs.toString()) >= Integer.parseInt(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "And":
                try {
                    return Boolean.parseBoolean(lhs.toString()) && Boolean.parseBoolean(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            case "Or":
                try {
                    return Boolean.parseBoolean(lhs.toString()) || Boolean.parseBoolean(rhs.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static Function<Object[], Object> funcResolver(Object[] args, Map<String, Object> variables, JsonNode node) {
        long[] cache = new long[50];
        return new Function<Object[], Object>() {
            @Override
            public Object apply(Object[] arr) {
                int i = 0;
                Map<String, Object> localEnv = new HashMap<>();
                localEnv.putAll(variables);
                for (var value : arr) {
                    localEnv.put(node.get("parameters").get(i).get("text").asText(), value);
                    i++;
                }
                if (localEnv.size() == 2) {
                    try {
                        String param = localEnv.keySet().toArray()[1].toString();
                        int index = Integer.parseInt(localEnv.get(param).toString());
                        if (cache[index] != 0) {
                            return cache[index];
                        }
                        Object result = Interpreter.eval(node.get("value"), localEnv);
                        long value = Long.parseLong(result.toString());
                        cache[index] = value;
                        return result;
                    } catch (Exception e) {
                        Object result = Interpreter.eval(node.get("value"), localEnv);
                        return result;
                    }
                }
                Object result = Interpreter.eval(node.get("value"), localEnv);
                return result;
            }
        };
    }

}
