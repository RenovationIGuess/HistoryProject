package application.controller;

import application.SidebarController;
import history.era.Era;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public class EraDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    private Text timeStampText;

    @FXML
    private Text homelandText;

    @FXML
    private Text founderText;

    @FXML
    private Text capLocateText;

    @FXML
    private Text timeText;

    @FXML
    private SidebarController sideBarController;

    private Era era;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/EraScreen.fxml", event);
    }

    public void setEra(Era era) {
        this.era = era;
        nameText.setText(era.getName());
        timeStampText.setText(era.getBelongsToTimestamp());
        homelandText.setText(era.getHomeland());
        founderText.setText(era.getFounder());
        capLocateText.setText(era.getLocationOfCapital());
        timeText.setText(era.getTime());
    }
}
