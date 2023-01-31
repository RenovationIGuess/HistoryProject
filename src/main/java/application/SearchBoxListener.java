package application;

/**
 *
 */
public interface SearchBoxListener {
    /**
     * Định nghĩa phương thức thực thi khi có sự kiện tìm kiếm theo tên
     * @param name tên cần tìm kiếm
     */
    void onSearchNameHandler(String name);

    /**
     * Định nghĩa phương thức thực thi khi có sự kiện tìm kiếm theo id
     * @param id id cần tìm kiếm
     */
    void onSearchIdHandler(String id);

    /**
     * Định nghĩa phương thức thực thi khi có thanh tìm kiếm rỗng
     */
    void onBlankHandler();
}
