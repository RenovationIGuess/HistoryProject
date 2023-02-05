package history.era;

import com.fasterxml.jackson.core.type.TypeReference;
import helper.JsonHelper;
import history.util.EntityCollection;
import history.HistoricalEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Đây là lớp đóng vai trò như một cơ sở dữ liệu cho
 * toàn bộ đối tượng lớp Era, dùng để lưu trữ đối tượng
 * chuyển thành file json hoặc đọc dữ liệu từ các file json chuyển
 * thành đối tượng sử dụng trong chương trình
 */
public class Eras {

    /* Tập hợp các đối tượng lớp Era đã được khởi tạo hoặc lấy từ các file json đều được lưu ở đây */
    public static EntityCollection<Era> collection = new EntityCollection<>();

    /* Folder chứa toàn bộ file json ghi dữ liệu của đối tượng lớp Era */
    public final static String DIR_NAME = "\\Era";

    /**
     * Đọc toàn bộ dữ liệu từ các file json từ folder
     * và chuyển thành đối tượng trong chương trình
     * sau đó lưu vào tập hợp
     */
    public static void loadJSON(){
        try {
            Stream<Path> paths = Files.list(Paths.get(JsonHelper.PREFIX_URL + DIR_NAME ));

            List<Era> eras = paths.map(path -> {
                try {
                    return JsonHelper.MAPPER.<Era>readValue(path.toFile(), new TypeReference<>(){});
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

    /**
     * Lưu trữ toàn bộ đối tượng trong tập hợp vào các file JSON
     */
    public static void save(){
        for (HistoricalEntity entity : collection.getData())
            entity.save();
    }
}
