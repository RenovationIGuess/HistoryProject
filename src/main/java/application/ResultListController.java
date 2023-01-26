package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultListController implements Initializable {

    @FXML
    private GridPane gridList;

    public Parent createNewCell(){
        try {
            Parent node = FXMLLoader.load(getClass().getResource("/application/fxml/ResultCell.fxml"));
            HBox hbox = new HBox(node);
            hbox.setAlignment(Pos.CENTER);
            return hbox;
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gridList.addColumn(1, createNewCell());
    }
}
