package com.hex.hex;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import static javafx.scene.paint.Color.color;

/**
 * Sample Skeleton for 'hello-view.fxml' Controller Class
 */

public class boardController {
    int user1 = 0;
    int user2 = 0;


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="hex1"
    private Polygon hex1; // Value injected by FXMLLoader

    @FXML // fx:id="hex11"
    private Polygon hex11; // Value injected by FXMLLoader

    @FXML // fx:id="hex2"
    private Polygon hex2; // Value injected by FXMLLoader

    private ArrayList<Circle> circles = new ArrayList<>();

    @FXML
    public void getHexID(javafx.scene.input.MouseEvent mouseEvent) {

        if(user1 == 0){
            Polygon hexagon = (Polygon) mouseEvent.getSource();
            Double x = hexagon.getLayoutX();
            Double y = hexagon.getLayoutY();

            Circle circle = new Circle(x, y, 43.3);
            circles.add(circle);
            circle.setFill(Color.RED);
            ((AnchorPane) hexagon.getParent()).getChildren().add(circle);
            user1 = 1;
            user2 = 0;
            AnchorPane parent = (AnchorPane) hexagon.getParent();
            displayTurn(parent);
        }
        else if(user2 == 0){
            Polygon hexagon = (Polygon) mouseEvent.getSource();
            Double x = hexagon.getLayoutX();
            Double y = hexagon.getLayoutY();

            Circle circle = new Circle(x, y, 43.3);
            circles.add(circle);
            circle.setFill(Color.BLUE);
            ((AnchorPane) hexagon.getParent()).getChildren().add(circle);
            user2 = 1;
            user1 = 0;
            AnchorPane parent = (AnchorPane) hexagon.getParent();
            displayTurn(parent);
        }

    }
    private Label turn;
    public void displayTurn(AnchorPane parent){
        if(turn != null){
            parent.getChildren().remove(turn);
        }
        if(user1 == 0){
            turn= new Label("red players turn");
            turn.setLayoutX(804);
            turn.setLayoutY(562);
            parent.getChildren().add(turn);

        }
        else if(user2 == 0){
            turn = new Label("blue players turn");
            turn.setLayoutX(804);
            turn.setLayoutY(562);
            parent.getChildren().add(turn);
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert hex1 != null : "fx:id=\"hex1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert hex11 != null : "fx:id=\"hex11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert hex2 != null : "fx:id=\"hex2\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

    public void removeCircles(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        AnchorPane parent = (AnchorPane) button.getParent();
        removeAllCircles(parent);
    }
    private void removeAllCircles(AnchorPane pane) {
        for(Circle circle : circles){
            pane.getChildren().remove(circle);
        }
    }


    public void Close(ActionEvent actionEvent) {
        System.exit(0);
    }
}