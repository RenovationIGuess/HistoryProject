package application.controller;

import application.App;
import history.model.Event;
import history.model.HistoricalFigure;
import history.collection.HistoricalFigures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        sideBarController.switchByGetFxml("/application/view/EventScreen.fxml", event);
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
            if(entry.getValue() != null){
                figureText.setFill(Color.web("#3498db"));
                figureText.setOnMouseClicked(mouseEvent -> {
                    HistoricalFigure figure = HistoricalFigures.collection.get(entry.getValue());
                    try {
                        FXMLLoader loader = new FXMLLoader(App.convertToURL("/application/view/FigureDetailScreen.fxml"));
                        Parent root = loader.load();
                        FigureDetailScreenController controller = loader.getController();
                        controller.setFigure(figure);
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
            }
            relatedCharsFlowPane.getChildren().add(figureText);
        }
    }
}
