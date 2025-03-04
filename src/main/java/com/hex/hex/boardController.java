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

enum Player {RED, BLUE}; // enum to keep track of player turns

/**
 * Board Class to control and store data regarding Cells and Nodes.
 */

public class boardController {
    Player Turn = Player.RED;
//    int user1 = 0;
//    int user2 = 0;


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
    private Circle TempCircle;

    /**
     * Function to handle clinking on a Cell.
     * @param mouseEvent pass the mouse event to function.
     */
    @FXML
    public void getHexID(javafx.scene.input.MouseEvent mouseEvent) {

        if(Turn == Player.RED){
            Polygon hexagon = (Polygon) mouseEvent.getSource();
            double x = hexagon.getLayoutX();
            double y = hexagon.getLayoutY();

            Circle circle = new Circle(x, y, 32.5);
            circles.add(circle);
            circle.setFill(Color.RED);
            ((AnchorPane) hexagon.getParent()).getChildren().add(circle);
            Turn = Player.BLUE;
//            user1 = 1;
//            user2 = 0;
            AnchorPane parent = (AnchorPane) hexagon.getParent();
            displayTurn(parent);
        }
        else if(Turn == Player.BLUE){
            Polygon hexagon = (Polygon) mouseEvent.getSource();
            double x = hexagon.getLayoutX();
            double y = hexagon.getLayoutY();

            Circle circle = new Circle(x, y, 32.5);
            circles.add(circle);
            circle.setFill(Color.BLUE);
            ((AnchorPane) hexagon.getParent()).getChildren().add(circle);
            Turn = Player.RED;
//            user2 = 1;
//            user1 = 0;
            AnchorPane parent = (AnchorPane) hexagon.getParent();
            displayTurn(parent);
        }

    }
    private Label turnLabel;
    public void displayTurn(AnchorPane parent){
        if(turnLabel != null){
            parent.getChildren().remove(turnLabel);
        }
        if(Turn == Player.RED){
            turnLabel = new Label("Red players turn");
            turnLabel.setLayoutX(1095);
            turnLabel.setLayoutY(375);
            parent.getChildren().add(turnLabel);

        }
        else if(Turn == Player.BLUE){
            turnLabel = new Label("Blue players turn");
            turnLabel.setLayoutX(1095);
            turnLabel.setLayoutY(375);
            parent.getChildren().add(turnLabel);
        }
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert hex1 != null : "fx:id=\"hex1\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert hex11 != null : "fx:id=\"hex11\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert hex2 != null : "fx:id=\"hex2\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

    /**
     * Function to handle clicking on "Reset" button.
     * @param mouseEvent pass the Mouse event to function.
     */
    public void removeCircles(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        AnchorPane parent = (AnchorPane) button.getParent();
        removeAllCircles(parent);
    }

    public void removeTempCircles(MouseEvent mouseEvent) {
        Polygon Cell = (Polygon) mouseEvent.getSource();
        AnchorPane pane = (AnchorPane) Cell.getParent();
        pane.getChildren().remove(TempCircle);
        TempCircle = null;
    }
    private void removeAllCircles(AnchorPane pane) {
        for(Circle circle : circles){
            pane.getChildren().remove(circle);
        }

        pane.getChildren().remove(TempCircle);
        TempCircle = null;

    }

    /**
     * Function to handle clicking on the "Close" button to exit the application.
     * @param actionEvent pass the Action event to the function.
     */
    public void Close(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Handles highlighting of Cells if it is empty.
     * @param mouseEvent - pass the Mouse event to function.
     */
    public void highlight(MouseEvent mouseEvent) {
        Polygon hexagon = (Polygon) mouseEvent.getSource();
        if (TempCircle == null){
            double x = hexagon.getLayoutX();
            double y = hexagon.getLayoutY();

            Circle circle = new Circle(x, y, 27);
            //circles.add(circle);
            circle.setOpacity(0.5);
            circle.setMouseTransparent(true);  // This allows interaction with the Cell through the TempCircle.
            ((AnchorPane) hexagon.getParent()).getChildren().add(circle);
            if (Turn == Player.RED){
                circle.setFill(Color.RED);
            }
            else if(Turn == Player.BLUE){
                circle.setFill(Color.BLUE);
            }
            else {
                circle.setFill(Color.WHITE);
            }
            TempCircle = circle;
            AnchorPane parent = (AnchorPane) hexagon.getParent();
            if (!parent.getChildren().contains(circle)) {
                parent.getChildren().add(circle);
            }
        }

    }
}