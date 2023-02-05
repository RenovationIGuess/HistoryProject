package history.relation;

import history.model.HistoricalEntity;
import history.model.Era;
import history.collection.Eras;
import history.model.Event;
import history.collection.Events;
import history.model.Festival;
import history.collection.Festivals;
import history.model.HistoricalFigure;
import history.collection.HistoricalFigures;
import history.model.HistoricSite;
import history.collection.HistoricSites;

import java.util.Collection;
import java.util.Map;

public class RelationCounter {
    public static int relationCountOnEntity(HistoricalEntity entity){
        int count = 0;
        if (entity instanceof Era) {
            for (Map.Entry<String, Integer> entry : ((Era) entity).getListOfKingsId().entrySet()){
                if (entry.getValue() != null)
                    count++;
            }
        }
        else if (entity instanceof Event){
            for (Map.Entry<String, Integer> entry : ((Event)entity).getRelatedFiguresId().entrySet()){
                if (entry.getValue() != null)
                    count++;
            }
        }
        else if (entity instanceof Festival){
            for (Map.Entry<String, Integer> entry : ((Festival) entity).getRelatedFiguresId().entrySet()){
                if (entry.getValue() != null)
                    count++;
            }
        }
        else if (entity instanceof HistoricalFigure) {
            if (((HistoricalFigure) entity).getEra().getValue() != null) count++;
            if (((HistoricalFigure) entity).getFather().getValue() != null) count++;
            if (((HistoricalFigure) entity).getMother().getValue() != null) count++;
            if (((HistoricalFigure) entity).getPrecededBy().getValue() != null) count++;
            if (((HistoricalFigure) entity).getSucceededBy().getValue() != null) count++;
        }
        else if (entity instanceof HistoricSite) {
            for (Map.Entry<String, Integer> entry : ((HistoricSite) entity).getRelatedFiguresId().entrySet()){
                if (entry.getValue() != null)
                    count++;
            }
        }
        return count;
    }

    public static int relationCountOnCollection(Collection<? extends HistoricalEntity> collection){
        int count = 0;
        for (HistoricalEntity entity : collection){
            count += relationCountOnEntity(entity);
        }
        return count;
    }

    public static int relationCountOnEntire(){
        if (HistoricSites.collection.isEmpty()){
            HistoricSites.loadJSON();
        }
        if (HistoricalFigures.collection.isEmpty()){
            HistoricalFigures.loadJSON();
        }
        if (Festivals.collection.isEmpty()){
            Festivals.loadJSON();
        }
        if (Events.collection.isEmpty()){
            Events.loadJSON();
        }
        if (Eras.collection.isEmpty()){
            Eras.loadJSON();
        }
        int count = 0;
        count += relationCountOnCollection(Eras.collection.getData());
        count += relationCountOnCollection(Events.collection.getData());
        count += relationCountOnCollection(Festivals.collection.getData());
        count += relationCountOnCollection(HistoricalFigures.collection.getData());
        count += relationCountOnCollection(HistoricSites.collection.getData());
        return count;
    }
}
