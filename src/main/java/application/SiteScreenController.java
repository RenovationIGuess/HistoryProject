package application;

import history.historicsite.HistoricSite;
import history.historicsite.HistoricSites;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SiteScreenController implements Initializable {
    @FXML
    private TableView<HistoricSite> siteTable;

    @FXML
    private TableColumn<HistoricSite, Integer> colSiteId;

    @FXML
    private TableColumn<HistoricSite, String> colSiteName;

    @FXML
    private TableColumn<HistoricSite, String> colSiteDate;

    @FXML
    private TableColumn<HistoricSite, String> colSiteLocate;

    @FXML
    private SearchBarController searchBarController;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        HistoricSites.loadJSON();

        colSiteId.setCellValueFactory(
                new PropertyValueFactory<HistoricSite, Integer>("id")
        );
        colSiteName.setCellValueFactory(
                new PropertyValueFactory<HistoricSite, String>("name")
        );
        colSiteDate.setCellValueFactory(
                new PropertyValueFactory<HistoricSite, String>("constructionDate")
        );
        colSiteLocate.setCellValueFactory(
                new PropertyValueFactory<HistoricSite, String>("location")
        );
        siteTable.setItems(HistoricSites.collection.getData());

        searchBarController.setSearchBoxListener(
                new SearchBoxListener() {
                    @Override
                    public void onSearchNameHandler(String name) {
                        siteTable.setItems(HistoricSites.collection.searchByName(name));
                    }

                    @Override
                    public void onSearchIdHandler(String id) {
                        try {
                            int intId = Integer.parseInt(id);
                            siteTable.setItems(
                                    FXCollections.singletonObservableList(HistoricSites.collection.get(intId))
                            );
                        } catch (Exception e){
                            System.err.println("Cannot find the entity with the id " + id);
                        }
                    }

                    @Override
                    public void onBlankHandler() {
                        siteTable.setItems(HistoricSites.collection.getData());
                    }
                }
        );
    }
}
