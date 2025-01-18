package de.uas.fra.project.group25.javaproject.Controllers;

import de.uas.fra.project.group25.javaproject.Drone.DroneStorage;
import de.uas.fra.project.group25.javaproject.WindowAppearance;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ClientSideNavigationMenuController implements Initializable {

    public BorderPane applicationWindow;
    public Button sideMenuCollapser;
    public Button refreshButton;


    public void homeOnAction(ActionEvent actionEvent) throws IOException {
        //System.out.println("homeOnAction activated");
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getHomeView());
    }

    public void droneMapOnAction(ActionEvent actionEvent) throws IOException {
        //System.out.println("droneMapOnAction activated");
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getMapView());
    }

    public void droneListOnAction(ActionEvent actionEvent) throws IOException {
        //System.out.println("droneListOnAction activated");
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getListView());
    }

    public void droneCatalogueOnAction(ActionEvent actionEvent) throws IOException {
        //System.out.println("droneCatalogueOnAction activated");
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getCatalogueView());
    }

    public void droneDetailsOnAction(ActionEvent actionEvent) throws IOException {
        applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getDetailsView());
    }


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


    public void refreshOnAction(ActionEvent actionEvent){
        DroneStorage.getInstance().updateData();
        try{
            applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getListView());
            System.out.println("DroneList refresh");
            applicationWindow.setCenter(WindowAppearance.getInstance().getWindowFactory().getCatalogueView());
            System.out.println("DroneCatalogue refresh");
            
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void fitCenter() throws IOException {
        ((AnchorPane)applicationWindow.getCenter()).prefHeightProperty().bind(WindowAppearance.getInstance().getStage().heightProperty());
        ((AnchorPane)applicationWindow.getCenter()).prefWidthProperty().bind(WindowAppearance.getInstance().getStage().widthProperty());
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideMenuCollapser.onActionProperty().addListener((observable, oldValue, newValue) -> {
            try {
                fitCenter();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}