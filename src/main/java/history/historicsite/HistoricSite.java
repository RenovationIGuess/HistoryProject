package history.historicsite;

import history.HistoricalEntity;

import java.util.List;
import java.util.Map;

public class HistoricSite extends HistoricalEntity {

    private String location;
    private String constructionDate;
    private String founder;
    private Map<String, Integer> relatedFestivalId;
    private String overview;
    private Map<String, Integer> relatedFiguresId;

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

    public void setRelatedFigures(List<String> figures){
        relatedFiguresId.clear();
        for (String figure : figures){
            relatedFiguresId.put(figure, null);
        }
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
}
