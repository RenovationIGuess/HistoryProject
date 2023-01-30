package application;

import history.HistoricalEntity;
import history.era.Era;
import history.era.Eras;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EraScreenController implements Initializable {
    private FiguresScreenController figuresScreenController;

    private TableView<HistoricalEntity> eraTable;

    private ObservableList<HistoricalEntity> listOfEras;

    void getData() {
        List<HistoricalEntity> eras = Eras.collection.getData();
        listOfEras = FXCollections.observableList(eras);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Eras.loadJSON();
        getData();
        eraTable.setItems(this.listOfEras);
    }
}
