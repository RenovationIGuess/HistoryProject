package history.historicsite;

import com.fasterxml.jackson.core.type.TypeReference;
import history.EntityCollection;
import history.event.Event;
import json.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HistoricSites {
    public static EntityCollection<HistoricSite> collection = new EntityCollection<>();
    public final static String DIR_NAME = "\\HistoricSite";

    /**
     * Tải dữ liệu dạng JSON từ folder chuyển thành đối tượng lưu vào collection
     */
    public static void loadJSON() {
        try {
            Stream<Path> paths = Files.list(Paths.get(JSON.PREFIX_URL + DIR_NAME));

            List<HistoricSite> sites = paths.map(path -> {
                try {
                    HistoricSite site = JSON.MAPPER.readValue(path.toFile(), new TypeReference<>() {});
                    return site;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());

            collection.setData(sites);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
