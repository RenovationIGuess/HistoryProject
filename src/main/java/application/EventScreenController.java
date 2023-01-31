package application;

import history.event.Event;
import history.event.Events;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventScreenController implements Initializable {
    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableColumn<Event, Integer> colEventId;

    @FXML
    private TableColumn<Event, String> colEventName;

    @FXML
    private TableColumn<Event, String> colEventDate;

    @FXML
    private TableColumn<Event, String> colEventLocate;

    @FXML
    private SearchBarController searchBarController;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Events.loadJSON();

        colEventId.setCellValueFactory(
                new PropertyValueFactory<Event, Integer>("id")
        );
        colEventName.setCellValueFactory(
                new PropertyValueFactory<Event, String>("name")
        );
        colEventDate.setCellValueFactory(
                new PropertyValueFactory<Event, String>("date")
        );
        colEventLocate.setCellValueFactory(
                new PropertyValueFactory<Event, String>("location")
        );
        eventTable.setItems(Events.collection.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void onSearchNameHandler(String name) {
                        eventTable.setItems(Events.collection.searchByName(name));
                    }

                    @Override
                    public void onSearchIdHandler(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            eventTable.setItems(
                                    FXCollections.singletonObservableList(Events.collection.get(intId))
                            );
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void onBlankHandler() {
                        eventTable.setItems(Events.collection.getData());
                    }
                }
        );
    }
}
