package aprendendo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import aprendendo.utils.FileUtils;

public class AppTest {

    private final Map<String, Object> variables = new HashMap<>();

    @Test
    public void addTest() {
        JsonNode tree = FileUtils.getFileContent("examples/add.json");
        var result = Interpreter.eval(tree.get("expression"), variables);

        Assert.assertEquals("1 + 2 = " + 3, result);
    }

    @Test
    public void addSimpleTest() {
        JsonNode tree = FileUtils.getFileContent("examples/add-simple.json");
        Object result = Interpreter.eval(tree.get("expression"), variables);

        Assert.assertEquals(3, Integer.parseInt(result.toString()));
    }

    @Test
    public void combinationTest() {
        JsonNode tree = FileUtils.getFileContent("examples/combination.json");
        var result = Interpreter.eval(tree.get("expression"), variables);

        Assert.assertEquals(45, Integer.parseInt(result.toString()));
    }

    @Test
    public void fibTest() {
        JsonNode tree = FileUtils.getFileContent("examples/fib.json");
        var result = Interpreter.eval(tree.get("expression"), variables);

        Assert.assertEquals(12586269025L, Long.parseLong(result.toString()));
    }

    @Test
    public void sumTest() {
        JsonNode tree = FileUtils.getFileContent("examples/sum.json");
        var result = Interpreter.eval(tree.get("expression"), variables);

        Assert.assertEquals(15, Long.parseLong(result.toString()));
    }
}
