import crawl.site.crawlHistorySite;
import history.era.Eras;
import history.event.Events;
import history.festival.Festivals;
import history.historicalfigure.HistoricalFigures;
import history.historicsite.HistoricSite;
import history.historicsite.HistoricSites;
import history.relation.Relation;
import javafx.collections.ObservableList;

public class Test {
    public static void main(String[] args) {
        Relation.crawlData();
        Relation.createRelation();
//        crawlHistorySite.crawlData();
//        System.out.println(HistoricSites.collection.getData().size());
//        ObservableList<HistoricSite> listOfSites = HistoricSites.collection.getData();
//        for (int i = 0; i < 21; ++i) {
//            System.out.println("\nName: " + listOfSites.get(i).getName());
//            System.out.println("Built date: " + listOfSites.get(i).getConstructionDate());
//            System.out.println("Approved Year: " + listOfSites.get(i).getApprovedYear());
//            System.out.println("Category: " + listOfSites.get(i).getCategory());
//            System.out.println("Note: " + listOfSites.get(i).getNote());
//        }
    }
}
