package application.controller;

import application.SidebarController;
import history.era.Era;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;

public class EraDetailScreenController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label timeStampLabel;

    @FXML
    private Label homelandLabel;

    @FXML
    private Label founderLabel;

    @FXML
    private Text capLocateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private SidebarController sideBarController;

    private Era era;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/EraScreen.fxml", event);
    }

    public void setEra(Era era) {
        this.era = era;
        nameLabel.setText(era.getName());
        timeStampLabel.setText(era.getBelongsToTimestamp());
        homelandLabel.setText(era.getHomeland());
        founderLabel.setText(era.getFounder());
        capLocateLabel.setText(era.getLocationOfCapital());
        timeLabel.setText(era.getTime());
    }
}
