package history;

import java.util.*;

public abstract class HistoricalEntity {

    protected long id;
    protected String name;
    protected List<String> aliases = new ArrayList<>();

    public long getId() {
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

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public void addAlias(String alias){
        if (this.aliases.contains(alias)){
            //Already has this alias
            return;
        } else {
            this.aliases.add(alias);
        }
    }

    public void removeAlias(String alias){
        if (this.aliases.contains(alias)){
            this.aliases.remove(alias);
        }
    }

    public HistoricalEntity() {
    }

    public HistoricalEntity(String name) {
        this.name = name;
    }
}
