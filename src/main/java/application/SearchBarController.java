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
    public ComboBox<String> filterComboBox;
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
            if (searchBox.getText().isBlank()){
                searchBoxListener.onBlankHandler();
            } else {
                if (filterComboBox.getSelectionModel().getSelectedItem().equals("By ID")) {
                    /* Thực thi khi chuyển comboBox sang By ID */
                    searchBoxListener.onSearchIdHandler(searchBox.getText());
                } else if (filterComboBox.getSelectionModel().getSelectedItem().equals("By Name")) {
                    /* Thực thi khi chuyển comboBox sang By Name */
                    searchBoxListener.onSearchNameHandler(searchBox.getText());
                }
            }
        });

        searchBox.textProperty().addListener((
                (observableValue, oldValue, newValue) -> {
                    if (newValue.isBlank()){
                        searchBoxListener.onBlankHandler();
                    } else {
                        if (filterComboBox.getValue().equals("By ID")){
                            searchBoxListener.onSearchIdHandler(newValue);
                        }
                        else if (filterComboBox.getValue().equals("By Name")){
                            searchBoxListener.onSearchNameHandler(newValue);
                        }
                    }
                }
        ));
    }
}