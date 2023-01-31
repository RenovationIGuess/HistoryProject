package history;

import json.JSON;

import java.util.*;

public abstract class HistoricalEntity {

    protected int id;
    protected String name;
    protected Set<String> aliases = new HashSet<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public void setAliases(String... aliases) {
        this.aliases = new HashSet<>(List.of(aliases));
    }

    /**
     * Thêm một tên gọi khác cho đối tượng
     * @param alias tên gọi khác của đối tượng cần thêm
     */
    public void addAlias(String alias){
        if (!this.aliases.contains(alias)){
            this.aliases.add(alias);
        }
    }

    public void addAlias(String ...aliases){
        for (String alias: aliases){
            this.addAlias(alias);
        }
    }

    public void removeAlias(String alias){
        this.aliases.remove(alias);
    }

    public void clearAlias() {
        this.aliases.clear();
    }

    /**
     * @param id id cần kiểm tra
     * @return Đối tượng có id như vậy không
     */
    public boolean isMatch(int id){
        return this.getId() == id;
    }

    /**
     * Kiểm tra tên đối tượng có khớp với tên tìm kiếm không
     * @param name tên tìm kiếm
     * @return true nếu tên đối tượng khớp 1 phần với tên tìm kiếm
     */
    public boolean isMatch(String name){
//        if (this.getName().toLowerCase().contains(name.toLowerCase()))
//            return true;
//        for (String alias : aliases){
//            if (alias.toLowerCase().contains(name.toLowerCase()))
//                return true;
//        }
//        return false;
        return this.name.toLowerCase().contains(name.toLowerCase());
    }

    /**
     * Kiểm tra đối tượng có tên xác định
     * @param name
     * @return true nếu tên trùng
     */
    public boolean hasName(String name){
        return this.name.equalsIgnoreCase(name);
    }

    /**
     * @return String dạng JSON của đối tượng
     */
    public String toJSON(){
        return JSON.toJSON(this);
    }

    /**
     * Dùng để lưu đối tượng vào file JSON
     * fileName = /[Tên class]/[id đối tượng].json
     * extensions: json
     */
    public void save(){
        String className = this.getClass().getSimpleName();
        String fileName = "\\" + className + "\\" + this.getId() + ".json";
        JSON.writeJSON(fileName, this);
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
                else if (this.hasName(((HistoricalEntity) obj).getName()))
                    return true;
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

    public void printObject(){
        System.out.println(this.toJSON());
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
