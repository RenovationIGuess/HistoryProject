package history.historicalfigure;

import history.HistoricalEntity;
import history.relation.Pair;

import java.util.ArrayList;

public class HistoricalFigure extends HistoricalEntity {
    private String realName; // Ten that
    private String born; // Ngay sinh voi noi sinh
    private String died; // Ngay mat va noi mat
    private String overview; // Mo ta ngan gon
    private String workTime; // Thoi gian tai chuc
    private Pair<String, Integer> era;
    private Pair<String, Integer> father;
    private Pair<String, Integer> mother;
    private Pair<String, Integer> precededBy;
    private Pair<String, Integer> succeededBy;

    /* Getters */

    public String getBorn() {
        return born;
    }

    public String getDied() {
        return died;
    }

    public String getOverview() {
        return overview;
    }

    public Pair<String, Integer> getEra() {
        return era;
    }

    public Pair<String, Integer> getFather() {
        return father;
    }

    public Pair<String, Integer> getMother() {
        return mother;
    }

    public Pair<String, Integer> getPrecededBy() {
        return precededBy;
    }

    public Pair<String, Integer> getSucceededBy() {
        return succeededBy;
    }
    /* Setters */
    public void setBorn(String born) {
        this.born = born;
    }

    public void setDied(String died) {
        this.died = died;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    /* Constructors */
    public HistoricalFigure(){
        super();
        this.id = HistoricalFigures.collection.getSequenceId();
        HistoricalFigures.collection.add(this);
    }

    public HistoricalFigure(String name) {
        super(name);
        this.id = HistoricalFigures.collection.getSequenceId();
        HistoricalFigures.collection.add(this);
    }

    public HistoricalFigure(
        String name,
        String realName,
        ArrayList<String> alterName,
        String birth,
        String lost,
        String position,
        String workTime,
        String era,
        String father,
        String mother,
        String preceded,
        String succeeded
    ) {
        super(name, alterName);
        this.id = HistoricalFigures.collection.getSequenceId();
        this.realName = realName;
        this.born = birth;
        this.died = lost;
        this.overview = position;
        this.workTime = workTime;
        this.era = new Pair<>(era, null);
        this.father = new Pair<>(father, null);
        this.mother = new Pair<>(mother, null);
        this.precededBy = new Pair<>(preceded, null);
        this.succeededBy = new Pair<>(succeeded, null);
        HistoricalFigures.collection.add(this);
    }
}
