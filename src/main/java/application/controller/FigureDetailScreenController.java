package application.controller;

import application.SidebarController;
import history.historicalfigure.HistoricalFigure;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class FigureDetailScreenController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label realNameLabel;

    @FXML
    private Label bornLabel;

    @FXML
    private Label diedLabel;

    @FXML
    private Text overviewText;

    @FXML
    private Label workTimeLabel;

    @FXML
    private Label eraLabel;

    @FXML
    private Label fatherLabel;

    @FXML
    private Label motherLabel;

    @FXML
    private Label precededByLabel;

    @FXML
    private Label succeededByLabel;

    @FXML
    private SidebarController sideBarController;

    private HistoricalFigure figure;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/HistoricalFiguresScreen.fxml", event);
    }

    public void setFigure(HistoricalFigure figure) {
        this.figure = figure;
        nameLabel.setText(figure.getName());
        realNameLabel.setText(figure.getRealName());
        bornLabel.setText(figure.getBorn());
        diedLabel.setText(figure.getDied());
        overviewText.setText(figure.getOverview());
        workTimeLabel.setText(figure.getWorkTime());
        eraLabel.setText(figure.getEra().getKey());
        fatherLabel.setText(figure.getFather().getKey());
        motherLabel.setText(figure.getMother().getKey());
        precededByLabel.setText(figure.getPrecededBy().getKey());
        succeededByLabel.setText(figure.getSucceededBy().getKey());
    }
}
