package history;

import json.JSON;

import java.util.*;

public abstract class HistoricalEntity {

    protected int id;
    protected String name;
    protected List<String> aliases = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(String... aliases) {
        this.aliases = List.of(aliases);
    }

    public void addAlias(String alias){
        if (!this.aliases.contains(alias)){
            this.aliases.add(alias);
        }
    }

    public void addAlias(String ...aliases){
        for (String alias: aliases){
            this.addAlias(alias);
        }
    }

    public void removeAlias(String alias){
        this.aliases.remove(alias);
    }

    public void clearAlias() {
        this.aliases.clear();
    }

    public boolean isMatch(int id){
        return this.getId() == id;
    }

    public boolean isMatch(String name){
        if (this.getName().toLowerCase().contains(name.toLowerCase()))
            return true;
        for (String alias : aliases){
            if (alias.toLowerCase().contains(name.toLowerCase()))
                return true;
        }
        return false;
    }

    public boolean hasName(String name){
        return this.getName().equals(name);
    }

    /**
     * @return String dạng JSON của đối tượng
     */
    public String toJSON(){
        return JSON.toJSON(this);
    }

    /**
     * Dùng để lưu đối tượng vào file JSON
     * fileName = Tên class + id
     * extensions: json
     */
    public void save(){
        String className = this.getClass().getSimpleName();
        String fileName = "\\" + className + "\\" + this.getId() + ".json";
        JSON.writeJSON(fileName, this);
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof HistoricalEntity) {
            if (this.isMatch(((HistoricalEntity) obj).getId()))
                return true;
            else if (this.name.toLowerCase().equals(((HistoricalEntity) obj).getName().toLowerCase()))
                return true;
        }
        return false;
    }

    /* Constructor */

    public HistoricalEntity() {
    }

    public HistoricalEntity(String name) {
        this.name = name;
    }

    public HistoricalEntity(
        String name,
        ArrayList<String> alterName
    ) {
        this.name = name;
        this.aliases = alterName;
    }
}
