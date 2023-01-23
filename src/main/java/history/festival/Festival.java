package history.festival;

import history.HistoricalEntity;
import history.historicalfigure.HistoricalFigure;
import history.historicalfigure.HistoricalFigures;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Festival extends HistoricalEntity {

    private String date;
    private String location;
    private Map<String, Integer> relatedFiguresId = new HashMap<>();
    private String firstTime;
    private String note;

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

    public void setRelatedFigures(List<String> figures){
        relatedFiguresId.clear();

        for (String figure : figures){
            relatedFiguresId.put(figure, null);
        }
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
        String relatedChars,
        String firstTime
    ) {
        super(name);
        this.id = Festivals.collection.getSequenceId();
        this.date = date;
        this.location = location;
        this.note = note;
        this.firstTime = firstTime;
        Festivals.collection.add(this);
    }
}
