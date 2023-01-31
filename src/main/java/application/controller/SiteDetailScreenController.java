package application.controller;

import application.SidebarController;
import history.historicsite.HistoricSite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class SiteDetailScreenController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label constructDateLabel;

    @FXML
    private Label founderLabel;

    @FXML
    private Text overviewLabel;

    @FXML
    private SidebarController sideBarController;

    private HistoricSite site;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/HistoricSiteScreen.fxml", event);
    }

    public void setHistoricSite(HistoricSite site) {
        this.site = site;
        nameLabel.setText(site.getName());
        locationLabel.setText(site.getLocation());
        constructDateLabel.setText(site.getConstructionDate());
        founderLabel.setText(site.getFounder());
        overviewLabel.setText(site.getOverview());
    }
}
