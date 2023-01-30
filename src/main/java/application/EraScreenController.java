package application;

import history.HistoricalEntity;
import history.era.Era;
import history.era.Eras;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EraScreenController implements Initializable {
    private FiguresScreenController figuresScreenController;

    @FXML
    private TableView eraTable;

    @FXML
    private TableColumn colEraId;

    @FXML
    private TableColumn colEraName;

    @FXML
    private TableColumn colEraDate;

    @FXML
    private TableColumn colEraTimeStamp;

    private ObservableList<HistoricalEntity> listOfEras;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eras.loadJSON();

        colEraId.setCellValueFactory(
                new PropertyValueFactory<Era, Integer>("id")
        );
        colEraName.setCellValueFactory(
                new PropertyValueFactory<Era, String>("name")
        );
        colEraDate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("time")
        );
        colEraTimeStamp.setCellValueFactory(
                new PropertyValueFactory<Era, String>("belongsToTimestamp")
        );
        eraTable.setItems(Eras.collection.getData());
    }
}
