package application.controller;

import application.SidebarController;
import history.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Map;

public class EventDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    private Text timeText;

    @FXML
    private Text locationText;

    @FXML
    private Text overviewText;

    @FXML
    private Text causeText;

    @FXML
    private Text resultText;

    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    private SidebarController sideBarController;

    private Event event;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/EventScreen.fxml", event);
    }

    public void setEvent(Event event) {
        this.event = event;
        nameText.setText(event.getName());
        timeText.setText(event.getDate());
        locationText.setText(event.getLocation());
        overviewText.setText(event.getOverview());
        causeText.setText(event.getCause());
        resultText.setText(event.getResult());
        for(Map.Entry<String, Integer> entry : event.getRelatedFiguresId().entrySet()){
            Text figureText = new Text(entry.getKey());
            relatedCharsFlowPane.getChildren().add(figureText);
        }
    }
}
