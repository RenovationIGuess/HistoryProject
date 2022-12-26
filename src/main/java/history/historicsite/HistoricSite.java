package history.historicsite;

import history.HistoricalEntity;
import history.Storable;
import history.era.Era;
import json.JSON;

import java.io.IOException;

public class HistoricSite extends HistoricalEntity implements Storable {

    public static long nbHistoricSites = 0;
    private String location;
    private int constructionYear;
    private Era builtInEra;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getConstructionYear() {
        return constructionYear;
    }

    public void setConstructionYear(int constructionYear) {
        this.constructionYear = constructionYear;
    }

    public Era getBuiltInEra() {
        return builtInEra;
    }

    public void setBuiltInEra(Era builtInEra) {
        this.builtInEra = builtInEra;
    }

    public HistoricSite() {
        super();
        this.id = ++nbHistoricSites;
    }

    public HistoricSite(String name) {
        super(name);
        this.id = ++nbHistoricSites;
    }

    public HistoricSite(String name, String location, int constructionYear, Era builtInEra) {
        super(name);
        this.id = ++nbHistoricSites;
        this.location = location;
        this.constructionYear = constructionYear;
        this.builtInEra = builtInEra;
    }

    /**
     * Dùng để lưu đối tượng vào file json
     * Tên file: HistoricSite+id.json
     */
    public void save() {
        String filename = "\\HistoricSite" + this.id + ".json";
        JSON.writeJSON(filename, this);
    }
}
