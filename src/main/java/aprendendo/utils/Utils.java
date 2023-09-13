package aprendendo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static Map<String,Object> strToJson(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content,new TypeReference<Map<String,Object>>() {});
        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonNode getFileContent(String filename) {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));

            while(br.ready()) sb.append(br.readLine());
            br.close();

            return toJsonNode(sb.toString());
        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JsonNode toJsonNode(String content) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(content);
        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
