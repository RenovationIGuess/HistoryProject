package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import history.HistoricalEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JSON {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    //Absolute path của folder chứa các file json ("/src/json")
    public static final String PREFIX_URL = FileSystems.getDefault().getPath("./").normalize().toAbsolutePath().toString() + "\\src\\json";

    /**
     * Viết đối tượng ra lưu trữ dưới dạng JSON
     * @param filename
     * @param entity
     */
    public static void writeJSON(String filename, HistoricalEntity entity) {
        try {
            System.out.println(PREFIX_URL + filename);
            MAPPER.writeValue(new File(PREFIX_URL + filename), entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chuyển đổi đối tượng thành String JSON
     * @param entity
     * @return String
     */
    public static String toJSON(HistoricalEntity entity){
        String result = null;
        try {
            result = MAPPER.writeValueAsString(entity);
        } catch (IOException exception){
            result = null;
            exception.printStackTrace();
        } finally {
            return result;
        }
    }
}