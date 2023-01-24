package history.historicsite;

import history.HistoricalEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricSite extends HistoricalEntity {

    private String location;
    private String constructionDate;
    private String founder;
    private Map<String, Integer> relatedFestivalId = new HashMap<>();;
    private String overview;
    private Map<String, Integer> relatedFiguresId = new HashMap<>();;

    /* Getters */

    public String getLocation() {
        return location;
    }

    public String getConstructionDate() {
        return constructionDate;
    }

    public String getFounder() {
        return founder;
    }

    public Map<String, Integer> getRelatedFestivalId() {
        return relatedFestivalId;
    }

    public String getOverview() {
        return overview;
    }

    public Map<String, Integer> getRelatedFiguresId() {
        return relatedFiguresId;
    }

    /* Setters */
    public void setLocation(String location) {
        this.location = location;
    }

    public void setConstructionDate(String constructionDate) {
        this.constructionDate = constructionDate;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelatedFestival(String festival){
        relatedFestivalId.clear();
        relatedFestivalId.put(festival, null);
    }

    public void setRelatedFestival(Map<String, Integer> newRelateFes) {
        this.relatedFestivalId = newRelateFes;
    }

    public void setRelatedFigures(List<String> figures){
        relatedFiguresId.clear();
        for (String figure : figures){
            relatedFiguresId.put(figure, null);
        }
    }

    public void setRelatedFigures(Map<String, Integer> newRelateFigs) {
        this.relatedFiguresId = newRelateFigs;
    }

    /* Helpers */
    //Add sau

    /* Constructors */

    public HistoricSite() {
        super();
        this.id = HistoricSites.collection.getSequenceId();
        HistoricSites.collection.add(this);
    }

    public HistoricSite(String name) {
        super(name);
        this.id = HistoricSites.collection.getSequenceId();
        HistoricSites.collection.add(this);
    }

    public HistoricSite(
            String name,
            String builtDate,
            String location,
            String founder,
            String relatedFes,
            String desc,
            ArrayList<String> relatedChars
    ) {
        super(name);
        this.id = HistoricSites.collection.getSequenceId();
        this.constructionDate = builtDate;
        this.location = location;
        this.founder = founder;
        this.relatedFestivalId.put(relatedFes, null);
        this.overview = desc;
        for (String relatedChar : relatedChars) {
            this.relatedFiguresId.put(relatedChar, null);
        }
        HistoricSites.collection.add(this);
    }
}
