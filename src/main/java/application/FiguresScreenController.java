package application;

import history.HistoricalEntity;
import history.historicalfigure.HistoricalFigure;
import history.historicalfigure.HistoricalFigures;
import history.relation.Pair;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FiguresScreenController {

    @FXML
    private TableView tblFigure;

    @FXML
    private TableColumn colFigureId;

    @FXML
    private TableColumn colFigureName;

    @FXML
    private TableColumn colFigureEra;

    @FXML
    private TableColumn colFigureOverview;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    private ScrollPane scroll;

    private ObservableList<HistoricalEntity> listOfFigures;

//    void getData(){
//        List<HistoricalEntity> list = HistoricalFigures.collection.getData();
//        listOfFigures = FXCollections.observableList(list);
//        searchBarController.setSearchBoxListener(
//                new SearchBoxListener() {
//                    @Override
//                    public void onSearchNameHandler(String text) {
//                        listOfFigures = FXCollections.observableArrayList(
//                                HistoricalFigures.collection.searchByName(text)
//                        );
//                        updateContent();
//                    }
//
//                    @Override
//                    public void onSearchIdHandler(String id) {
//                        int searchId;
//                        try {
//                            searchId = Integer.parseInt(id);
//                            listOfFigures = FXCollections.observableArrayList(
//                                    HistoricalFigures.collection.get(searchId)
//                            );
//                        } catch (Exception e) {
//                            System.out.println("Cannot find the entity with the id " + id);
//                        }
//                        updateContent();
//                    }
//
//                    @Override
//                    public void onBlankHandler() {
//                        listOfFigures = HistoricalFigures.collection.getData();
//                        updateContent();
//                    }
//                }
//        );
//    }

//    void updateContent(){
//        grid.getChildren().removeAll(grid.getChildren());
//        int column = 0;
//        int row = 0;
//        try {
//            for (HistoricalEntity entity : listOfFigures){
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(App.convertToURL("/application/fxml/Item.fxml"));
//                HBox hbox = loader.load();
//
//                ItemController itemController = loader.getController();
//                itemController.setEntity(entity);
//
//                if (column == 3) {
//                    column = 0;
//                    row++;
//                }
//
//                grid.add(hbox, column++, row);
//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);
//
//                GridPane.setMargin(hbox, new Insets(5));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    public void initialize() {
        HistoricalFigures.loadJSON();

        colFigureId.setCellValueFactory(
                new PropertyValueFactory<HistoricalFigure, Integer>("id"));
        colFigureName.setCellValueFactory(
                new PropertyValueFactory<HistoricalFigure, String>("name"));
        colFigureEra.setCellValueFactory(
                new PropertyValueFactory<HistoricalFigure, Pair<String, Integer> >("era"));
        colFigureOverview.setCellValueFactory(
                new PropertyValueFactory<HistoricalFigure, String>("overview"));
        tblFigure.setItems(HistoricalFigures.collection.getData());
    }
}