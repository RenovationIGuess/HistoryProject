package history.model;

import helper.JsonHelper;

import java.util.*;

/**
 *  Đây là lớp trừu tượng thể hiện bất kỳ thực thể liên quan đến lịch sử
 *  gồm các thuộc tính:
 *      id: ID (mã số nhận dạng),
 *      name: tên của thực thể (tên hay gọi),
 *      aliases: tên gọi khác của thực thể
 */
public abstract class HistoricalEntity implements Storable {

    protected int id;
    protected String name;
    protected Set<String> aliases = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAliases(String... aliases) {
        this.aliases = new HashSet<>(List.of(aliases));
    }

    /**
     * Thêm một tên gọi khác cho đối tượng
     * @param aliases các tên gọi khác của đối tượng cần thêm
     */
    public void addAlias(String ...aliases){
        this.aliases.addAll(List.of(aliases));
    }

    /**
     * @param id id cần kiểm tra
     * @return true nếu đối tượng có id như thế
     */
    public boolean isMatch(Integer id){
        if (id == null) return false;
        return this.getId() == id;
    }

    /**
     * Kiểm tra tên đối tượng có khớp với tên tìm kiếm không
     * @param name tên tìm kiếm
     * @return true nếu tên đối tượng khớp 1 phần với tên tìm kiếm
     */
    public boolean isMatch(String name){
        if (this.name != null){
            return this.name.toLowerCase().contains(name.toLowerCase());
        }
        return false;
    }

    /**
     * Kiểm tra đối tượng có tên xác định
     * @param name tên của đối tượng kiểm tra
     * @return true nếu tên trùng
     */
    public boolean hasName(String name){
        return this.name.equalsIgnoreCase(name);
    }

    /**
     * @return String dạng JSON của đối tượng
     */
    public String toJSON(){
        return JsonHelper.stringify(this);
    }

    /**
     * So sánh 2 đối tượng
     * nếu id giống nhau hoặc tên giống nhau trả về true
     */
    @Override
    public boolean equals(Object obj){
        if (obj instanceof HistoricalEntity) {
            if (this.isMatch(((HistoricalEntity) obj).getId()))
                return true;
            else {
                if (this.name == null){
                    return false;
                }
                else return this.hasName(((HistoricalEntity) obj).getName());
            }
        }
        return false;
    }

    /**
     * @return tất cả tên gọi khác nhau của một đối tượng
     */
    public Set<String> fetchAllPossibleNames() {
        Set<String> names = new HashSet<>(this.aliases);
        names.add(this.name);
        return names;
    }

    /* Constructor */
    public HistoricalEntity() {
    }

    public HistoricalEntity(String name) {
        this.name = name;
    }

    public HistoricalEntity(
            String name,
            List<String> alterName
    ) {
        this.name = name;
        this.aliases.addAll(alterName);
    }
}
