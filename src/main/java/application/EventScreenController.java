package application;

import history.era.Era;
import history.era.Eras;
import history.event.Events;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventScreenController implements Initializable {
    @FXML
    private TableView eventTable;

    @FXML
    private TableColumn colEventId;

    @FXML
    private TableColumn colEventName;

    @FXML
    private TableColumn colEventDate;

    @FXML
    private TableColumn colEventLocate;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Events.loadJSON();

        colEventId.setCellValueFactory(
                new PropertyValueFactory<Era, Integer>("id")
        );
        colEventName.setCellValueFactory(
                new PropertyValueFactory<Era, String>("name")
        );
        colEventDate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("date")
        );
        colEventLocate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("location")
        );
        eventTable.setItems(Events.collection.getData());
    }
}
