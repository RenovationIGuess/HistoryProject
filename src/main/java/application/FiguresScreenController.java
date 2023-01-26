package application;

import history.HistoricalEntity;
import history.historicalfigure.HistoricalFigures;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FiguresScreenController implements Initializable {

    @FXML
    private SearchBarController searchBarController;

    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    private ObservableList<HistoricalEntity> listOfFigures;

    void getData(){
        List<HistoricalEntity> list = HistoricalFigures.collection.parseCollection().subList(0, 20);
        listOfFigures = FXCollections.observableList(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HistoricalFigures.loadJSON();
        getData();
        int column = 0;
        int row = 0;
        try {
            for (HistoricalEntity entity : listOfFigures){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(App.convertToURL("/application/fxml/Item.fxml"));
                HBox hbox = loader.load();

                ItemController itemController = loader.getController();
                itemController.setEntity(entity);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(hbox, column++, row);
                GridPane.setMargin(hbox, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}