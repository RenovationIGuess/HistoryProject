package application;

import history.era.Era;
import history.era.Eras;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EraScreenController implements Initializable {

    @FXML
    private TableView<Era> eraTable;

    @FXML
    private TableColumn<Era, Integer> colEraId;

    @FXML
    private TableColumn<Era, String> colEraName;

    @FXML
    private TableColumn<Era, String> colEraDate;

    @FXML
    private TableColumn<Era, String> colEraTimeStamp;

    @FXML
    private SearchBarController searchBarController;

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

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void onSearchNameHandler(String name) {
                        eraTable.setItems(Eras.collection.searchByName(name));
                    }

                    @Override
                    public void onSearchIdHandler(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            eraTable.setItems(
                                    FXCollections.singletonObservableList(Eras.collection.get(intId))
                            );
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void onBlankHandler() {
                        eraTable.setItems(Eras.collection.getData());
                    }
                }
        );
    }
}
