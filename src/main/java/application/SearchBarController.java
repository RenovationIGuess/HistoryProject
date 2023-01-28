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

    private SearchBoxListener searchBoxListener;

    public void setSearchBoxListener(SearchBoxListener searchBoxListener){
        this.searchBoxListener = searchBoxListener;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchBox.textProperty().addListener((
                (observableValue, oldValue, newValue) -> {
                    if (newValue.isBlank()){
                        searchBoxListener.onBlankHandler();
                    } else {
                        if (byNameRadioBtn.isSelected()){
                            searchBoxListener.onSearchNameHandler(newValue);
                        } else if (byIdRadioBtn.isSelected()){
                            searchBoxListener.onSearchIdHandler(newValue);
                        }
                    }
                }
        ));
    }
}