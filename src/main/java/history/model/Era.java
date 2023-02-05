package history.model;

import history.collection.Eras;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Đây là lớp cho loại thực thể triều đại lịch sử
 * gồm các thuộc tính
 *      name: tên triều đại
 *      aliases: tên gọi khác
 *      belongsToTimestamp: thuộc về thời kỳ
 *      homeland: quê hương
 *      founder: người sáng lập
 *      locationOfCaptal: vị trí kinh đô
 *      time: thời gian
 *      listOfKings: danh sách vua cai trị (liên kết ID)
 */

public class Era extends HistoricalEntity {

    private String belongsToTimestamp;
    private String homeland;
    private String founder;
    private String locationOfCapital;
    private String time;
    private String overview;
    private Map<String, Integer> listOfKingsId = new HashMap<>();

    /* Getters */
    public String getBelongsToTimestamp() {
        return belongsToTimestamp;
    }

    public String getHomeland() {
        return homeland;
    }

    public String getFounder() {
        return founder;
    }

    public String getLocationOfCapital() {
        return locationOfCapital;
    }

    public String getTime() {
        return time;
    }

    public String getOverview() {
        return overview;
    }

    public Map<String, Integer> getListOfKingsId() {
        return listOfKingsId;
    }

    /* Setters */
    public void setBelongsToTimestamp(String belongsToTimestamp) {
        this.belongsToTimestamp = belongsToTimestamp;
    }

    public void setHomeland(String homeland) {
        this.homeland = homeland;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setLocationOfCapital(String locationOfCapital) {
        this.locationOfCapital = locationOfCapital;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setListOfKingsId(Map<String, Integer> newListOfKings) {
        this.listOfKingsId = newListOfKings;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /* Constructors */
    public Era() {
        super();
        this.id = Eras.collection.getSequenceId();
        Eras.collection.add(this);
    }

    public Era(String name){
        super(name);
        this.id = Eras.collection.getSequenceId();
        Eras.collection.add(this);
    }

    public Era(
        String name,
        String timestamp,
        String hometown,
        String founder,
        String locationOfCapital,
        String time,
        String overview,
        Collection<String> listOfKings
    ) {
        super(name);
        this.id = Eras.collection.getSequenceId();
        this.belongsToTimestamp = timestamp;
        this.homeland = hometown;
        this.founder = founder;
        this.locationOfCapital = locationOfCapital;
        this.time = time;
        this.overview = overview;
        for (String king : listOfKings){
            listOfKingsId.put(king, null);
        }
        Eras.collection.add(this);
    }

    /**
     * Dùng để lưu đối tượng vào file JSON.
     * fileName = /[Tên class]/[id đối tượng].json
     * extensions: json
     */
    public void save(){
        Eras.writeJSON(this);
    }
}
