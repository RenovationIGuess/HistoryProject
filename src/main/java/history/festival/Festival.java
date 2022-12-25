package history.festival;

import history.HistoricalEntity;
import history.event.Event;
import history.historicsite.HistoricSite;

import java.time.LocalDate;

public class Festival extends HistoricalEntity {
    private LocalDate date;
    private String location;
    private HistoricSite relatedSite;
    private Event relatedEvent;

    public Festival() {
    }

    public Festival(String name){
        super(name);
    }

    public Festival(String name, LocalDate date, String location, HistoricSite relatedSite, Event relatedEvent) {
        super(name);
        this.date = date;
        this.location = location;
        this.relatedSite = relatedSite;
        this.relatedEvent = relatedEvent;
    }
}
