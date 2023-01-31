package history.event;

import com.fasterxml.jackson.core.type.TypeReference;
import history.EntityCollection;
import json.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Events {
    public static EntityCollection<Event> collection = new EntityCollection<>();
    public final static String DIR_NAME = "\\Event";

    /**
     * Tải dữ liệu dạng JSON từ folder chuyển thành đối tượng lưu vào collection
     */
    public static void loadJSON() {
        try {
            Stream<Path> paths = Files.list(Paths.get(JSON.PREFIX_URL + DIR_NAME));

            List<Event> events = paths.map(path -> {
                try {
                    Event event = JSON.MAPPER.readValue(path.toFile(), new TypeReference<>() {});
                    return event;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());

            collection.setData(events);

            collection.sortById();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
