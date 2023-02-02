package application.controller;

import application.App;
import history.era.Era;
import history.historicalfigure.HistoricalFigure;
import history.historicalfigure.HistoricalFigures;
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

public class EraDetailScreenController {

    @FXML
    public FlowPane kingsFlowPane;

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
        for(Map.Entry<String, Integer> entry : era.getListOfKingsId().entrySet()){
            Text kingText = new Text(entry.getKey());
            if(entry.getValue() != null) {
                kingText.setFill(Color.web("#3498db"));
                kingText.setOnMouseClicked(mouseEvent -> {
                    HistoricalFigure figure = HistoricalFigures.collection.get(entry.getValue());
                    try {
                        FXMLLoader loader = new FXMLLoader(App.convertToURL("/application/fxml/FigureDetailScreen.fxml"));
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
            kingsFlowPane.getChildren().add(kingText);
        }
    }
}
