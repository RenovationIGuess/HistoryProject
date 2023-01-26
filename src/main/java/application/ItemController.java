package application;

import history.HistoricalEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ItemController {

    private HistoricalEntity entity;
    @FXML
    private Button itemBtn;

    @FXML
    void onClickItem(ActionEvent event) {
        System.out.println(this.entity.toJSON());
    }

    void setEntity(HistoricalEntity entity){
        this.entity = entity;
        itemBtn.setText(entity.getName());
    }
}