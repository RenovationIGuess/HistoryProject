import history.historicalfigure.HistoricalFigures;

public class Main {
    public static void main(String[] args) {
        HistoricalFigures.loadJSON();
        HistoricalFigures.collection
                .get(1206).printObject();
    }
}