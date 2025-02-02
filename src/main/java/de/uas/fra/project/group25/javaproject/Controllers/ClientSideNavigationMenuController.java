package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;


public class ClientSideNavigationMenuController implements Initializable, Refittable{
    @FXML
    private BorderPane applicationWindow;
    @FXML
    private Button sideMenuCollapser;


    /**
    * Method that is called when home button (in side menu) is pressed
    */
    public void homeOnAction(ActionEvent actionEvent){
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getHomeView());
    }
    /**
     * Method that is called when "Drone List" button (in side menu) is pressed
     */
    public void droneListOnAction(ActionEvent actionEvent){
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getListView());
    }
    /**
     * Method that is called when "Drone Catalogue" button (in side menu) is pressed
     */
    public void droneCatalogueOnAction(ActionEvent actionEvent){
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getCatalogueView());
    }
    /**
     * Method that is called when "Drone Details" button (in side menu) is pressed
     */
    public void droneDetailsOnAction(ActionEvent actionEvent){
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getDetailsView());
    }

    /**
     * Method that collapses/uncollapses the side menu
     */
    public void collapseSideMenu(){
        if (sideMenuCollapser.getText().equals("<<")) {
            //Collapse
            ((BorderPane) applicationWindow.getLeft()).getCenter().setScaleX(0);
            ((BorderPane) applicationWindow.getLeft()).setPrefWidth(0);
            sideMenuCollapser.setText(">>");

        }else if (sideMenuCollapser.getText().equals(">>")) {
            //Uncollapse
            ((BorderPane) applicationWindow.getLeft()).getCenter().setScaleX(1);
            ((BorderPane) applicationWindow.getLeft()).setPrefWidth(270);
            sideMenuCollapser.setText("<<");
        }
    }

    /**
    * Method that is called when the refresh button is pressed
    */
    public void refreshOnAction(ActionEvent actionEvent){
        DroneStorage.getInstance().updateData();
    }

    /**
     * Implementation of fitCenter() from Refittable Interface.
     * Binds size of center pane to the window size
     */
    @Override
    public void fitCenter(){
        ((AnchorPane)applicationWindow.getCenter()).prefHeightProperty().bind(WindowAppearance.getInstance().getStage().heightProperty());
        ((AnchorPane)applicationWindow.getCenter()).prefWidthProperty().bind(WindowAppearance.getInstance().getStage().widthProperty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Changes the size of center pane when side menu is collapsed
        sideMenuCollapser.onActionProperty().addListener((observable, oldValue, newValue) -> {
            fitCenter();
        });
    }
}