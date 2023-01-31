package application.controller;

import application.SidebarController;
import history.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class EventDetailScreenController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label overviewLabel;

    @FXML
    private Text reasonLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private SidebarController sideBarController;

    private Event eve;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/EventScreen.fxml", event);
    }

    public void setEvent(Event eve) {
        this.eve = eve;
        nameLabel.setText(eve.getName());
        timeLabel.setText(eve.getDate());
        locationLabel.setText(eve.getLocation());
        overviewLabel.setText(eve.getOverview());
        reasonLabel.setText(eve.getCause());
        resultLabel.setText(eve.getResult());
    }
}
