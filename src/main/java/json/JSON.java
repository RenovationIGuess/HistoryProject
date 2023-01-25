package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import history.HistoricalEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JSON {
    /* Object để thực hiện các thao tác đọc viết JSON */
    public static final ObjectMapper MAPPER = new ObjectMapper();

    /* Absolute path của folder chứa các file json ("/src/json") */
    public static final String PREFIX_URL = FileSystems
            .getDefault().getPath("./").normalize()
            .toAbsolutePath() + "\\src\\json";

    /**
     * Viết đối tượng ra lưu trữ dưới dạng JSON
     * @param filename Tên file để lưu trữ JSON (extension: .json)
     * @param entity Đối tượng cần lưu trữ
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
     * @param entity Đối tượng cần chuyển sang JSON
     * @return String JSON đã biến đổi từ đối tượng
     */
    public static String toJSON(HistoricalEntity entity){
        try {
            String result = MAPPER.writeValueAsString(entity);
            return result;
        } catch (IOException exception){
            exception.printStackTrace();
            return null;
        }
    }
}