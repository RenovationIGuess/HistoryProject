package history.model;

import history.collection.Festivals;
import history.collection.HistoricalFigures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Đây là lớp cho loại thực thể Lễ hội
 * gồm các thuộc tính
 *      name: tên lễ hội
 *      alias: tên gọi khác
 *      date: ngày tổ chức (âm lịch)
 *      location: địa điểm tổ chức
 *      firstTime: lần đầu tổ chức
 *      note: chú thích
 *      relatedFiguresId: danh sách nhân vật lịch sử liên quan (liên kết ID)
 */
public class Festival extends HistoricalEntity {

    private String date;
    private String location;
    private String firstTime;
    private String note;

    private Map<String, Integer> relatedFiguresId = new HashMap<>();

    /* Getters */

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public String getNote() {
        return note;
    }

    public Map<String, Integer> getRelatedFiguresId() {
        return relatedFiguresId;
    }

    /* Setters */

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setRelatedFigures(Map<String, Integer> newRelateFigs) {
        this.relatedFiguresId = newRelateFigs;
    }

    /* Helpers */
    public List<HistoricalFigure> fetchRelatedFigures(){
        List<HistoricalFigure> figures = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: relatedFiguresId.entrySet()){
            HistoricalFigure figure = HistoricalFigures.collection.get(entry.getValue());
            figures.add(figure);
        }
        return figures;
    }

    /* Constructors */
    public Festival() {
        this.id = Festivals.collection.getSequenceId();
        Festivals.collection.add(this);
    }

    public Festival(String name){
        super(name);
        this.id = Festivals.collection.getSequenceId();
        Festivals.collection.add(this);
    }

    public Festival(
        String name,
        String location,
        String date,
        String note,
        List<String> relatedChars,
        String firstTime
    ) {
        super(name);
        this.id = Festivals.collection.getSequenceId();
        this.date = date;
        this.location = location;
        this.note = note;
        this.firstTime = firstTime;
        for (String relatedChar : relatedChars){
            this.relatedFiguresId.put(relatedChar, null);
        }
        Festivals.collection.add(this);
    }

    /**
     * Dùng để lưu đối tượng vào file JSON.
     * fileName = /[Tên class]/[id đối tượng].json
     * extensions: json
     */
    public void save(){
        Festivals.writeJSON(this);
    }
}
