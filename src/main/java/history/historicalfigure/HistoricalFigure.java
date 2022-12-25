package history.historicalfigure;

import history.HistoricalEntity;
import history.Storable;
import history.era.Era;
import history.event.Event;
import json.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoricalFigure extends HistoricalEntity implements Storable {

    public static long nbHistoricalFigures = 0;
    private int born;
    private int died;
    private List<Era> livedInEras = new ArrayList<>();
    private List<Event> relatedEvents = new ArrayList<>();
    private HistoricalFigure isChildOf;
    private HistoricalFigure isParentOf;


    public int getBorn() {
        return born;
    }

    public void setBorn(int born) {
        this.born = born;
    }

    public int getDied() {
        return died;
    }

    public void setDied(int died) {
        this.died = died;
    }

    public List<Era> getLivedInEras() {
        return livedInEras;
    }

    public void setLivedInEras(Era ...livedInEras) {
        this.livedInEras.clear();

        for (Era era: livedInEras){
            this.livedInEras.add(era);
        }
    }

    public List<Event> getRelatedEvents() {
        return relatedEvents;
    }

    public void setRelatedEvents(Event ...relatedEvents) {
        this.relatedEvents.clear();

        for (Event event: relatedEvents){
            this.relatedEvents.add(event);
        }
    }

    public HistoricalFigure getIsChildOf() {
        return isChildOf;
    }

    public void setIsChildOf(HistoricalFigure isChildOf) {
        this.isChildOf = isChildOf;
    }

    public HistoricalFigure getIsParentOf() {
        return isParentOf;
    }

    public void setIsParentOf(HistoricalFigure isParentOf) {
        this.isParentOf = isParentOf;
    }

    public HistoricalFigure(){
        super();
        this.id = ++nbHistoricalFigures;
    }

    public HistoricalFigure(String name) {
        super(name);
        this.id = ++nbHistoricalFigures;
    }

    public HistoricalFigure(String name, int born, int died) {
        super(name);
        this.id = ++nbHistoricalFigures;
        this.born = born;
        this.died = died;
    }

    public void save() throws IOException {
        String filename = "\\HistoricalFigure" + this.id + ".json";
        JSON.writeJSON(filename, this);
    }
}
