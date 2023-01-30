package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SidebarController {

    private Parent root;
    private Stage stage;
    private Scene scene;

    /**
     * Di chuyển sang màn hình bằng đường dẫn file FXML
     * @param path đường dẫn từ bắt đầu từ /src/main/java/....
     * @param event sự kiện kích hoạt
     */
    public void switchByGetFxml(String path, ActionEvent event) throws IOException {
        root = FXMLLoader.load(App.convertToURL(path));

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToMain(ActionEvent event) throws IOException {
        switchByGetFxml("/application/fxml/MainScreen.fxml", event);
    }

    @FXML
    void switchToHistoricalFigures(ActionEvent event) throws IOException {
        switchByGetFxml("/application/fxml/FiguresScreen.fxml", event);
    }

    @FXML
    void switchToHistoricSite(ActionEvent event) throws IOException {
        switchByGetFxml("/application/fxml/TrangTimKiemDiTich.fxml", event);
    }

    @FXML
    void switchToEras(ActionEvent event) throws IOException {
        switchByGetFxml("/application/fxml/TrangTimKiemTrieuDai.fxml", event);
    }

    @FXML
    void switchToEvents(ActionEvent event) throws IOException {
        switchByGetFxml("/application/fxml/TrangTimKiemSuKien.fxml", event);
    }

    @FXML
    void switchToFestivals(ActionEvent event) throws IOException {
        switchByGetFxml("/application/fxml/TrangTimKiemLeHoi.fxml", event);
    }

}