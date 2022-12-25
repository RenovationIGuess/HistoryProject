package history.event;

import history.HistoricalEntity;
import history.era.Era;

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
    private static long nbEvents = 0;
    private int startDate;
    private int endDate;
    private String location;
    private Era relatedEra;


    public int getStartDate() {
        return startDate;
    }

    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Era getRelatedEra() {
        return relatedEra;
    }

    public void setRelatedEra(Era relatedEra) {
        this.relatedEra = relatedEra;
    }

    public Event(){}

    public Event(String name, int startDate, int endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(String name, int startDate, int endDate, String location) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    public Event(String name){
        super(name);
    }
}
