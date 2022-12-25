package history.festival;

import history.HistoricalEntity;
import history.event.Event;
import history.historicsite.HistoricSite;

import java.time.LocalDate;

public class Festival extends HistoricalEntity {

    private static long nbFestivals = 0;
    private LocalDate date;
    private String location;
    private HistoricSite relatedSite;
    private Event relatedEvent;

    public Festival() {
        this.id = ++nbFestivals;
    }

    public Festival(String name){
        super(name);
        this.id = ++nbFestivals;
    }

    public Festival(String name, LocalDate date, String location, HistoricSite relatedSite, Event relatedEvent) {
        super(name);
        this.id = ++nbFestivals;
        this.date = date;
        this.location = location;
        this.relatedSite = relatedSite;
        this.relatedEvent = relatedEvent;
    }
}
