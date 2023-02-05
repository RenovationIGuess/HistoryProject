package crawl;

import history.relation.Relation;

public class CreateJSON {
    public static void main(String[] args) {
        Relation.crawlData();
        Relation.createRelation();
    }
}

