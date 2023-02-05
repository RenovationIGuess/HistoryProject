package history.model;

import history.collection.HistoricSites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Đây là lớp thể hiện loại thực thể di tích lịch sử
 * bao gồm các thuộc tính
 *      location: vị trí
 *      constructionDate: ngày khởi công
 *      founder: người sáng lập
 *      relatedFestivalId: lễ hội liên quan
 *      overview: mô tả ngắn gọn
 *      note: ghi chú
 *      category: hạng mục
 *      relatedFiguresId: nhân vật liên quan
 */
public class HistoricSite extends HistoricalEntity implements Storable {

    private String location;
    private String constructionDate;
    private String founder;
    private Map<String, Integer> relatedFestivalId = new HashMap<>();;
    private String overview;

    private String note;

    private String category;

    private String approvedYear;

    private Map<String, Integer> relatedFiguresId = new HashMap<>();;

    /* Getters */
    public String getLocation() {
        return location;
    }

    public String getConstructionDate() {
        return constructionDate;
    }

    public String getFounder() {
        return founder;
    }

    public Map<String, Integer> getRelatedFestivalId() {
        return relatedFestivalId;
    }

    public String getOverview() {
        return overview;
    }

    public Map<String, Integer> getRelatedFiguresId() {
        return relatedFiguresId;
    }

    public String getNote(){
        return note;
    }

    public String getCategory(){
        return category;
    }

    public String getApprovedYear(){
        return approvedYear;
    }

    /* Setters */
    public void setLocation(String location) {
        this.location = location;
    }

    public void setConstructionDate(String constructionDate) {
        this.constructionDate = constructionDate;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelatedFestival(Map<String, Integer> newRelateFes) {
        this.relatedFestivalId = newRelateFes;
    }

    public void setRelatedFigures(Map<String, Integer> newRelateFigs) {
        this.relatedFiguresId = newRelateFigs;
    }

    public void setNote(String note){
        this.note = note;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setApprovedYear(String approvedYear){
        this.approvedYear = approvedYear;
    }

    /* Constructors */
    public HistoricSite() {
        super();
        this.id = HistoricSites.collection.getSequenceId();
        HistoricSites.collection.add(this);
    }

    public HistoricSite(String name) {
        super(name);
        this.id = HistoricSites.collection.getSequenceId();
        HistoricSites.collection.add(this);
    }

    public HistoricSite(
            String name,
            String builtDate,
            String location,
            String founder,
            String relatedFes,
            String desc,
            String note,
            String category,
            String approvedYear,
            ArrayList<String> relatedChars
    ) {
        super(name);
        this.id = HistoricSites.collection.getSequenceId();
        this.constructionDate = builtDate;
        this.location = location;
        this.founder = founder;
        this.relatedFestivalId.put(relatedFes, null);
        this.overview = desc;
        this.note = note;
        this.category = category;
        this.approvedYear = approvedYear;
        for (String relatedChar : relatedChars) {
            this.relatedFiguresId.put(relatedChar, null);
        }
        HistoricSites.collection.add(this);
    }

    /**
     * Dùng để lưu đối tượng vào file JSON.
     * fileName = /[Tên class]/[id đối tượng].json
     * extensions: json
     */
    public void save(){
        HistoricSites.writeJSON(this);
    }
}
