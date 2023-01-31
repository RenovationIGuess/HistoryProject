package history.era;

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

public class Eras {
    public static EntityCollection<Era> collection = new EntityCollection<>();

    public final static String DIR_NAME = "\\Era";
    public static void loadJSON(){
        try {
            Stream<Path> paths = Files.list(Paths.get(JSON.PREFIX_URL + DIR_NAME ));

            List<Era> eras = paths.map(path -> {
                try {
                    Era era = JSON.MAPPER.readValue(path.toFile(), new TypeReference<>(){});
                    return era;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }).collect(Collectors.toList());
            collection.setData(eras);
            collection.sortById();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
