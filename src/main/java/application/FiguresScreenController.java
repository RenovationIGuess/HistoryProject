package application;

import history.historicalfigure.HistoricalFigures;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FiguresScreenController implements Initializable {

    @FXML
    private SearchBarController searchBarController;

    @FXML
    private ResultListController figuresListController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HistoricalFigures.loadJSON();
    }
}
