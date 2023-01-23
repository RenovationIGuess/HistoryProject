package history.historicalfigure;

import history.HistoricalEntity;

import java.util.HashMap;
import java.util.Map;

public class HistoricalFigure extends HistoricalEntity {

    private String born;
    private String died;
    private String overview;
    private Map<String, Integer> era = new HashMap<>();
    private Map<String, Integer> father = new HashMap<>();
    private Map<String, Integer> mother = new HashMap<>();
    private Map<String, Integer> PrecededBy = new HashMap<>();
    private Map<String, Integer> SucceededBy = new HashMap<>();

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

    public Map<String, Integer> getEra() {
        return era;
    }

    public Map<String, Integer> getFather() {
        return father;
    }

    public Map<String, Integer> getMother() {
        return mother;
    }

    public Map<String, Integer> getPrecededBy() {
        return PrecededBy;
    }

    public Map<String, Integer> getSucceededBy() {
        return SucceededBy;
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

    public void setEra(Map<String, Integer> era) {
        this.era = era;
    }

    public void setFather(Map<String, Integer> father) {
        this.father = father;
    }

    public void setMother(Map<String, Integer> mother) {
        this.mother = mother;
    }

    public void setPrecededBy(Map<String, Integer> precededBy) {
        PrecededBy = precededBy;
    }

    public void setSucceededBy(Map<String, Integer> succeededBy) {
        SucceededBy = succeededBy;
    }

    /* Constructors */
    public HistoricalFigure(){
        super();
        this.id = HistoricalFigures.collection.getSequenceId();
    }

    public HistoricalFigure(String name) {
        super(name);
        this.id = HistoricalFigures.collection.getSequenceId();
    }
}
