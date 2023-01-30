package application;

import history.HistoricalEntity;
import history.era.Era;
import history.era.Eras;
import history.festival.Festivals;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FestivalScreenController implements Initializable {
    @FXML
    private TableView fesTable;

    @FXML
    private TableColumn colFesId;

    @FXML
    private TableColumn colFesName;

    @FXML
    private TableColumn colFesDate;

    @FXML
    private TableColumn colFesLocate;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Festivals.loadJSON();

        colFesId.setCellValueFactory(
                new PropertyValueFactory<Era, Integer>("id")
        );
        colFesName.setCellValueFactory(
                new PropertyValueFactory<Era, String>("name")
        );
        colFesDate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("date")
        );
        colFesLocate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("location")
        );
        fesTable.setItems(Festivals.collection.getData());
    }
}
