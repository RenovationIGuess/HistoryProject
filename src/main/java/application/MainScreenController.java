package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private SidebarController sideBarController;

    @FXML
    void switchToInstructionScreen(ActionEvent event) throws IOException {
        sideBarController.switchByGetFxml("/application/fxml/InstructionScreen.fxml", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
