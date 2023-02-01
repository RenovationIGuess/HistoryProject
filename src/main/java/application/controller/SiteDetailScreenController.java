package application.controller;

import application.App;
import application.SidebarController;
import history.historicalfigure.HistoricalFigure;
import history.historicalfigure.HistoricalFigures;
import history.historicsite.HistoricSite;
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

public class SiteDetailScreenController {
    @FXML
    private Text nameText;

    @FXML
    private Text locationText;

    @FXML
    private Text constructionDateText;

    @FXML
    private Text founderText;

    @FXML
    private Text overviewText;

    @FXML
    private Text festivalsText;

    @FXML
    private FlowPane relatedCharsFlowPane;

    @FXML
    private SidebarController sideBarController;

    private HistoricSite site;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/HistoricSiteScreen.fxml", event);
    }

    public void setHistoricSite(HistoricSite site) {
        this.site = site;
        nameText.setText(site.getName());
        locationText.setText(site.getLocation());
        constructionDateText.setText(site.getConstructionDate());
        founderText.setText(site.getFounder());
        overviewText.setText(site.getOverview());
        for (Map.Entry<String, Integer> entry : site.getRelatedFiguresId().entrySet()){
            Text figureText = new Text(entry.getKey());
            if(entry.getValue() != null) {
                figureText.setFill(Color.web("#3498db"));
                figureText.setOnMouseClicked(mouseEvent -> {
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
            relatedCharsFlowPane.getChildren().add(figureText);
        }
    }
}
