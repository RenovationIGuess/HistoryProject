package application.controller;

import application.SidebarController;
import history.historicalfigure.HistoricalFigure;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;

public class FigureDetailScreenController {

    @FXML
    private Text nameText;

    @FXML
    private Text realNameText;

    @FXML
    private Text bornText;

    @FXML
    private Text diedText;

    @FXML
    private Text overviewText;

    @FXML
    private Text workTimeText;

    @FXML
    private Text eraText;

    @FXML
    private Text fatherText;

    @FXML
    private Text motherText;

    @FXML
    private Text precededByText;

    @FXML
    private Text succeededByText;

    @FXML
    private SidebarController sideBarController;

    private HistoricalFigure figure;

    @FXML
    public void onClickBack(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/HistoricalFiguresScreen.fxml", event);
    }

    public void setFigure(HistoricalFigure figure) {
        this.figure = figure;
        nameText.setText(figure.getName());
        realNameText.setText(figure.getRealName());
        bornText.setText(figure.getBorn());
        diedText.setText(figure.getDied());
        overviewText.setText(figure.getOverview());
        workTimeText.setText(figure.getWorkTime());
        eraText.setText(figure.getEra().getKey());
        fatherText.setText(figure.getFather().getKey());
        motherText.setText(figure.getMother().getKey());
        precededByText.setText(figure.getPrecededBy().getKey());
        succeededByText.setText(figure.getSucceededBy().getKey());

        if(figure.fetchEra() != null) {
            eraText.setFill(Color.web("#3498db"));
            eraText.setOnMouseClicked(mouseEvent -> System.out.println("clicked"));
        }
        if(figure.fetchFather() != null) {
            fatherText.setFill(Color.web("#3498db"));
            fatherText.setOnMouseClicked(mouseEvent -> System.out.println("clicked"));
        }
        if(figure.fetchMother() != null) {
            motherText.setFill(Color.web("#3498db"));
            motherText.setOnMouseClicked(mouseEvent -> System.out.println("clicked"));
        }
        if(figure.fetchPrecededBy() != null) {
            precededByText.setFill(Color.web("#3498db"));
            precededByText.setOnMouseClicked(mouseEvent -> System.out.println("clicked"));
        }
        if(figure.fetchSucceededBy() != null) {
            succeededByText.setFill(Color.web("#3498db"));
            succeededByText.setOnMouseClicked(mouseEvent -> System.out.println("clicked"));
        }
    }
}
