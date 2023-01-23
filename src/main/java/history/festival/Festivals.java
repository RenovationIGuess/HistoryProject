package history.festival;

import com.fasterxml.jackson.core.type.TypeReference;
import history.EntityCollection;
import json.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Festivals {
    public static EntityCollection<Festival> collection = new EntityCollection<>();
    public final static String DIR_NAME = "\\Festival";

    /**
     * Tải dữ liệu dạng JSON từ folder chuyển thành đối tượng lưu vào collection
     */
    public static void loadJSON(){
        try {
            Stream<Path> paths = Files.list(Paths.get(JSON.PREFIX_URL + DIR_NAME));

            List<Festival> festivals = paths.map(path -> {
                try {
                    Festival festival = JSON.MAPPER.readValue(path.toFile(), new TypeReference<>() {});
                    return festival;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).toList();

            collection.setData(festivals);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
