package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {

    @FXML
    private TextField searchBox;

    @FXML
    private RadioButton byIdRadioBtn;

    @FXML
    private RadioButton byNameRadioBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
