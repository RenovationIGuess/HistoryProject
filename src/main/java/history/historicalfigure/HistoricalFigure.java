package history.historicalfigure;

import history.HistoricalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoricalFigure extends HistoricalEntity {
    private String realName; // Ten that
    private String born; // Ngay sinh voi noi sinh
    private String died; // Ngay mat va noi mat
    private String overview; // Mo ta ngan gon
    private String workTime; // Thoi gian tai chuc
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
        this.era.put(era, null);
        this.father.put(father, null);
        this.mother.put(mother, null);
        this.PrecededBy.put(preceded, null);
        this.SucceededBy.put(succeeded, null);
        HistoricalFigures.collection.add(this);
    }
}
