package helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import history.HistoricalEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JsonHelper {
    /* Object để thực hiện các thao tác đọc viết JSON */
    public static final ObjectMapper MAPPER = new ObjectMapper();

    /* Absolute path của folder chứa các file json ("/src/json") */
    public static final String PREFIX_URL = FileSystems
            .getDefault().getPath("./").normalize()
            .toAbsolutePath() + "\\src\\json";

    /**
     * Viết đối tượng ra lưu trữ dưới dạng JSON
     * @param filename Tên file để lưu trữ JSON (extension: .json)
     * @param object Đối tượng cần chuyển sang file JSON lưu trữ
     */
    public static void writeJSON(String filename, Object object) {
        try {
            System.out.println("Saved successfully into " + PREFIX_URL + filename);
            MAPPER.writeValue(new File(PREFIX_URL + filename), object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Chuyển đổi đối tượng thành String JSON
     * @param entity Đối tượng cần chuyển sang JSON
     * @return String JSON đã biến đổi từ đối tượng
     */
    public static String stringify(HistoricalEntity entity){
        try {
            return MAPPER.writeValueAsString(entity);
        } catch (IOException exception){
            exception.printStackTrace();
            return null;
        }
    }
}