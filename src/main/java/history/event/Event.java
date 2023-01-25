package history.event;

import history.HistoricalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Đây là lớp cho loại thực thể Sự kiện lịch sử
 * gồm các thuộc tính:
 *      name: tên sự kiện
 *      aliases: tên gọi khác
 *      date: thời gian diễn ra
 *      location: địa điểm diễn ra
 *      cause: nguyên nhân
 *      result: kết quả
 *      relatedFiguresId: các nhân vật lịch sử liên quan
 */

public class Event extends HistoricalEntity {
    private String date;
    private String location;
    private String overview;
    private String cause;
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

    public String getCause() {
        return cause;
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

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setRelatedFigures(Map<String, Integer> newRelateFigs) {
        this.relatedFiguresId = newRelateFigs;
    }

    /* Constructors */
    public Event(){
        super();
        this.id = Events.collection.getSequenceId();
        Events.collection.add(this);
    }

    public Event(String name){
        super(name);
        this.id = Events.collection.getSequenceId();
        Events.collection.add(this);
    }

    public Event(
        String name,
        String date,
        String location,
        String result,
        String reason,
        String desc,
        ArrayList<String> relatedChars
    ) {
        super(name);
        this.id = Events.collection.getSequenceId();
        this.date = date;
        this.location = location;
        this.cause = reason;
        this.result = result;
        this.overview = desc;
        for (String relatedChar : relatedChars){
            this.relatedFiguresId.put(relatedChar, null);
        }
        Events.collection.add(this);
    }
}
