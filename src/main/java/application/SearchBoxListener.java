package application;

/**
 *
 */
public interface SearchBoxListener {
    /**
     * Định nghĩa phương thức thực thi khi có sự kiện tìm kiếm theo tên
     * @param name tên cần tìm kiếm
     */
    void handleSearchName(String name);

    /**
     * Định nghĩa phương thức thực thi khi có sự kiện tìm kiếm theo id
     * @param id id cần tìm kiếm
     */
    void handleSearchId(String id);

    /**
     * Định nghĩa phương thức thực thi khi có thanh tìm kiếm rỗng
     */
    void handleBlank();
}
