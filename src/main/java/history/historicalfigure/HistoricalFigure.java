package history.historicalfigure;

import history.HistoricalEntity;
import history.era.Era;
import history.event.Event;

import java.util.ArrayList;
import java.util.List;

public class HistoricalFigure extends HistoricalEntity {
    private int born;
    private int died;
    private List<Era> livedInEras = new ArrayList<>();
    private List<Event> relatedEvent = new ArrayList<>();
    private HistoricalFigure isChildOf;
    private HistoricalFigure isParentOf;

    public HistoricalFigure(){}

    public HistoricalFigure(String name) {
        super(name);
    }

    public HistoricalFigure(String name, int born, int died) {
        super(name);
        this.born = born;
        this.died = died;
    }
}
