package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchBarController implements Initializable {

    @FXML
    public ComboBox filterComboBox;
    @FXML
    private TextField searchBox;

    private SearchBoxListener searchBoxListener;

    public void setSearchBoxListener(SearchBoxListener searchBoxListener){
        this.searchBoxListener = searchBoxListener;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filterComboBox.setItems(FXCollections.observableArrayList("By Name", "By ID"));

        filterComboBox.getSelectionModel().selectFirst();

        filterComboBox.setOnAction((e) -> {
            System.out.println(filterComboBox.getSelectionModel().getSelectedItem());
        });

        searchBox.textProperty().addListener((
                (observableValue, oldValue, newValue) -> {
                    if (newValue.isBlank()){
                        searchBoxListener.onBlankHandler();
                    } else {
                        //
                    }
                }
        ));
    }
}