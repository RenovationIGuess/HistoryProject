import history.collection.HistoricalFigures;
import history.model.HistoricalFigure;

public class Main {
    public static void main(String[] args) {
        HistoricalFigures.loadJSON();
        HistoricalFigure figure = HistoricalFigures.collection.get(1);
        figure.setOverview("dkm");
        figure.save();
    }
}