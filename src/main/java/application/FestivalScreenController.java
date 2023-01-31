package application;

import history.festival.Festival;
import history.festival.Festivals;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class FestivalScreenController implements Initializable {
    @FXML
    private TableView<Festival> fesTable;

    @FXML
    private TableColumn<Festival, Integer> colFesId;

    @FXML
    private TableColumn<Festival, String> colFesName;

    @FXML
    private TableColumn<Festival, String> colFesDate;

    @FXML
    private TableColumn<Festival, String> colFesLocate;

    @FXML
    private SearchBarController searchBarController;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Festivals.loadJSON();

        colFesId.setCellValueFactory(
                new PropertyValueFactory<Festival, Integer>("id")
        );
        colFesName.setCellValueFactory(
                new PropertyValueFactory<Festival, String>("name")
        );
        colFesDate.setCellValueFactory(
                new PropertyValueFactory<Festival, String>("date")
        );
        colFesLocate.setCellValueFactory(
                new PropertyValueFactory<Festival, String>("location")
        );
        fesTable.setItems(Festivals.collection.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void onSearchNameHandler(String name) {
                        fesTable.setItems(Festivals.collection.searchByName(name));
                    }

                    @Override
                    public void onSearchIdHandler(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            fesTable.setItems(
                                    FXCollections.singletonObservableList(Festivals.collection.get(intId))
                            );
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void onBlankHandler() {
                        fesTable.setItems(Festivals.collection.getData());
                    }
                }
        );
    }
}
