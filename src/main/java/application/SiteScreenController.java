package application;

import history.HistoricalEntity;
import history.era.Era;
import history.era.Eras;
import history.historicsite.HistoricSites;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SiteScreenController implements Initializable {
    @FXML
    private TableView siteTable;

    @FXML
    private TableColumn colSiteId;

    @FXML
    private TableColumn colSiteName;

    @FXML
    private TableColumn colSiteDate;

    @FXML
    private TableColumn colSiteLocate;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        HistoricSites.loadJSON();

        colSiteId.setCellValueFactory(
                new PropertyValueFactory<Era, Integer>("id")
        );
        colSiteName.setCellValueFactory(
                new PropertyValueFactory<Era, String>("name")
        );
        colSiteDate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("constructionDate")
        );
        colSiteLocate.setCellValueFactory(
                new PropertyValueFactory<Era, String>("location")
        );
        siteTable.setItems(HistoricSites.collection.getData());
    }
}
