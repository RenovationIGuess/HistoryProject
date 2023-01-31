package application.controller;

import application.SidebarController;
import history.festival.Festival;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class FesDetailScreenController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label firstTimeLabel;

    @FXML
    private Text noteLabel;

    @FXML
    private SidebarController sideBarController;

    private Festival fes;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/FestivalScreen.fxml", event);
    }

    public void setFestival(Festival fes) {
        this.fes = fes;
        nameLabel.setText(fes.getName());
        timeLabel.setText(fes.getDate());
        locationLabel.setText(fes.getLocation());
        firstTimeLabel.setText(fes.getFirstTime());
        noteLabel.setText(fes.getNote());
    }
}
