import history.historicalfigure.HistoricalFigures;

public class Main {
    public static void main(String[] args) {
        HistoricalFigures.loadJSON();
        HistoricalFigures.collection
                .searchByName("dương")
                .forEach(figure -> System.out.println(figure.toJSON()));
    }
}