package history;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;

public class EntityCollection <T extends HistoricalEntity> {
    private ObservableList<T> data;

    private int numberOfRecords = 0;

    public int getSequenceId(){
        return ++numberOfRecords;
    }

    public void setData(List<T> data){
        this.data = FXCollections.observableList(data);
        this.numberOfRecords = data.size();
    }

    public ObservableList<T> getData(){
        return data;
    }

    /**
     * Kiểm tra đối tượng đã có trong tập hợp hay chưa
     * @param entity Đối tượng lịch sử cần kiểm tra
     * @return true nếu tập hợp đã chứa đối tượng
     */
    public boolean exists(T entity){
        return data.contains(entity);
    }

    /**
     * Thêm đối tượng lịch sử vào tập hợp
     * @param entity Đối tượng lịch sử cần thêm vào
     */
    public void add(T entity){
        if (!exists(entity))
            data.add(entity);
    }

    /**
     * Xóa đối tượng có id khỏi tập hợp
     * @param id id của đối tượng cần xóa
     */
    public void remove (int id){
        for (T entity : data){
            if (entity.isMatch(id)) data.remove(entity);
        }
    }

    /**
     * Trả về đối tượng lịch sử có id xác định lấy ra từ tập hợp
     * @param id id của đối tượng cần lấy
     * @return đối tượng lịch sử
     */
    public T get(int id){
        for (T entity : data){
            if (entity.isMatch(id)) return entity;
        }
        return null;
    }

    /**
     * Lấy ra đối tượng lịch sử có tên xác định từ tập hợp
     * @param name tên của đối tượng lịch sử cần lấy
     * @return dối tượng lịch sử
     */
    public T get(String name){
        for (T entity: data){
            if (entity.hasName(name)) return entity;
        }
        return null;
    }

    /**
     * Tìm kiếm các đối tượng theo tên
     * @param name tên tìm kiếm
     * @return Danh sách lọc ra các đối tượng chứa tên được tìm kiếm
     */
    public FilteredList<T> searchByName(String name){
        return new FilteredList<>(data, entity -> entity.isMatch(name));
    }

    /**
     * Lưu trữ toàn bộ đối tượng trong tập hợp vào các file JSON
     */
    public void save(){
        for (T element : data){
            element.save();
        }
    }
}
