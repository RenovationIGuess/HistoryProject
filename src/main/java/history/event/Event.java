package history.event;

import history.HistoricalEntity;
import history.historicalfigure.HistoricalFigure;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Đây là lớp cho thực thể sự kiện lịch sử
 * gồm các thuộc tính:
 *      name: tên sự kiện
 *      aliases: tên gọi khác
 *      startDate: thời gian bắt đầu
 *      endDate: thời gian kết thúc
 *      location: địa điểm
 *      relatedEra: triều đại liên quan
 */

public class Event extends HistoricalEntity {
    private String date;
    private String location;
    private String overview;
    private String reason;
    private String result;
    private Map<String, Integer> relatedFiguresId = new HashMap<>();

    /* Getters */

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getOverview() {
        return overview;
    }

    public String getReason() {
        return reason;
    }

    public String getResult() {
        return result;
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

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setRelatedFigures(List<String> relatedFigures){
        relatedFiguresId.clear();

        for (String figure : relatedFigures){
            relatedFiguresId.put(figure, null);
        }
    }

    public void putRelatedFigures(String name, HistoricalFigure figure){
        relatedFiguresId.put(name, figure.getId());
    }

    /* Constructors */
    public Event(){
        super();
        this.id = Events.collection.getSequenceId();
    }

    public Event(String name, String time) {
        super(name);
        this.id = Events.collection.getSequenceId();
    }

    public Event(String name){
        super(name);
        this.id = Events.collection.getSequenceId();
    }
}
