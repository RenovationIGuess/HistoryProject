package history.historicsite;

import history.HistoricalEntity;
import history.era.Era;

public class HistoricSite extends HistoricalEntity {
    private String location;
    private int constructionYear;
    private Era builtInEra;

    public HistoricSite() {
    }

    public HistoricSite(String name) {
        super(name);
    }

    public HistoricSite(String name, String location, int constructionYear, Era builtInEra) {
        super(name);
        this.location = location;
        this.constructionYear = constructionYear;
        this.builtInEra = builtInEra;
    }
}
