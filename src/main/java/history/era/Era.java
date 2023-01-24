package history.era;

import history.HistoricalEntity;
import history.historicalfigure.HistoricalFigure;
import history.historicalfigure.HistoricalFigures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Đây là lớp cho thực thể triều đại lịch sử
 * gồm các thuộc tính
 *      name: tên triều đại
 *      aliases: tên gọi khác
 *      fromYear: năm bắt đầu
 *      toYear: năm kết thúc
 *      precededEra: triều đại liền trước
 *      succeededEra: triều đại kế tiếp
 */

public class Era extends HistoricalEntity {

    private String belongsToTimestamp;
    private String homeland;
    private String founder;
    private String locationOfCapital;
    private String time;
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

    public List<HistoricalEntity> fetchListOfKings(){
        List<HistoricalEntity> kings = new ArrayList<>();
        for (Map.Entry<String, Integer> king : listOfKingsId.entrySet())
        {
            HistoricalFigure figure = HistoricalFigures.collection.get(king.getValue());
            kings.add(figure);
        }
        return kings;
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
        ArrayList<String> listOfKings
    ) {
        super(name);
        this.id = Eras.collection.getSequenceId();
        this.belongsToTimestamp = timestamp;
        this.homeland = hometown;
        this.founder = founder;
        this.locationOfCapital = locationOfCapital;
        this.time = time;
        Eras.collection.add(this);
        for (String king : listOfKings){
            listOfKingsId.put(king, null);
        }
    }
}
