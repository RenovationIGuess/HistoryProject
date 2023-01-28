package history.historicalfigure;

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

public class HistoricalFigures {
    public static EntityCollection<HistoricalFigure> collection = new EntityCollection<>();
    public final static String DIR_NAME = "\\HistoricalFigure";

    /**
     * Tải dữ liệu dạng JSON từ folder chuyển thành đối tượng lưu vào collection
     */
    public static void loadJSON() {
        try {
            System.out.println("Loading all JSON files from " + JSON.PREFIX_URL + DIR_NAME + "...");

            Stream<Path> paths = Files.list(Paths.get(JSON.PREFIX_URL + DIR_NAME));

            List<HistoricalFigure> figures = paths.map(path -> {
                try {
                    HistoricalFigure figure = JSON.MAPPER.readValue(path.toFile(), new TypeReference<>() {});
                    return figure;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());

            collection.setData(figures);
            collection.sortById();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
