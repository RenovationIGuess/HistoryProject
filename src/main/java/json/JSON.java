package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import history.HistoricalEntity;

import java.io.File;
import java.io.IOException;

public class JSON {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    //Cần chỉnh sửa khi clone project
    public static final String PREFIX_URL = "E:\\OOP_history_prj\\src\\json";

    public static void writeJSON(String filename, HistoricalEntity entity) throws IOException {
        MAPPER.writeValue(new File(PREFIX_URL + filename), entity);
    }
}
