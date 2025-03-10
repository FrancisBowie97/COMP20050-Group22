package com.hex.hex;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import static java.lang.Integer.parseInt;

enum Player {RED, BLUE} // enum to keep track of player turns

/**
 * Board Class to control and store data regarding Cells and Nodes.
 */

public class boardController {
    Player Turn = Player.RED;


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

    private ArrayList<ArrayList<Node> > RedGroupp = new ArrayList<ArrayList<Node>>();
    private ArrayList<ArrayList<Node> > BlueGroupp = new ArrayList<ArrayList<Node>>();
    private Node[][] Hex_db = new Node[13][13];


    /**
     * Function to handle clinking on a Cell.
     * @param mouseEvent pass the mouse event to function.
     */
    @FXML
    public void getHexID(javafx.scene.input.MouseEvent mouseEvent) {

        Polygon hexagon = (Polygon) mouseEvent.getSource();
        double x = hexagon.getLayoutX();
        double y = hexagon.getLayoutY();

//        System.out.println(x + " " + y + " " + Turn);


        AnchorPane parent = (AnchorPane) hexagon.getParent();

        if (isValid(hexagon)) {

            // Storing location info of the Cell.
            String[] cord = Cord_convert(hexagon);
            int[] cord_index = Cord_to_index(cord);


            if(Turn == Player.RED){

                Circle circle = new Circle(x, y, 32.5);
                circle.setFill(Color.RED);
                circle.setMouseTransparent(true);
                circles.add(circle);
                Node temp = new Node(cord_index[1]+1, cord[0].toCharArray()[0], "Red");
                if(checkGroup(temp) == null){
                    group(temp);
                }
                else{
                    ArrayList<Node> group = checkGroup(temp);
                    group.add(temp);
                    System.out.println("group added lolololol");
                }
                Hex_db[cord_index[0]][cord_index[1]] = temp;
                parent.getChildren().add(circle);
                Turn = Player.BLUE;

                displayTurn(parent);
            }
            else if(Turn == Player.BLUE){

                Circle circle = new Circle(x, y, 32.5);
                circle.setFill(Color.BLUE);
                circle.setMouseTransparent(true);
                circles.add(circle);
                Node temp = new Node(cord_index[1]+1, cord[0].toCharArray()[0], "Blue");
                group(temp);
                Hex_db[cord_index[0]][cord_index[1]] = temp;
                parent.getChildren().add(circle);
                Turn = Player.RED;

                displayTurn(parent);
            }
        }


        //Temp Debugging
        Label Dis_cord;
        Dis_cord = new Label(Arrays.toString(hexagon.getId().split("_")));
        Dis_cord.setLayoutX(x-5);
        Dis_cord.setLayoutY(y-5);
        Dis_cord.setMouseTransparent(true);
        parent.getChildren().add(Dis_cord);

    }

    /**
     * Function to convert alphanumeric coordinates to array index for ease of access.
     * @param cord alphanumeric coordinates from Cord_convert().
     * @return array index to access Cell info.
     */
    private int[] Cord_to_index(String[] cord) {
        int[] cord_ind = new int[2];
        cord_ind[0] = (cord[0].toCharArray()[0] - 65);
        cord_ind[1] = (parseInt(cord[1]) - 1);
        return cord_ind;
    }


    /**
     * Function to return all the neighbours(Nodes) surrounding given node.
     * @param node pass the node whose neighbours are to be returned
     * @return an Arraylist of valid neighbours
     */
    private ArrayList<Node> getNeighbors(Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        int[] loc = node.getIndexCords();
//        System.out.println("COLUMN: " + loc[0]);
//        System.out.println("ROW: "+ loc[1]);
        //not top left edge
        if ( loc[0] > 0 ) {

            if ( (loc[1]-loc[0]) != 6 ) {
//                System.out.println("TOP LEFT");
                neighbors.add(Hex_db[loc[0]-1][loc[1]]);
            }
            if ( loc[1] > 0 ) {
//                System.out.println("TOP");
                neighbors.add(Hex_db[loc[0]-1][loc[1]-1]);
            }
        }
        if ( (loc[0]-loc[1]) != 6 ) {
            if ( loc[1] > 0 ) {
//                System.out.println("TOP RIGHT");
                neighbors.add(Hex_db[loc[0]][loc[1]-1]);
            }
            if ( loc[1] < 12 ) {
//                System.out.println("BOTTOM RIGHT");
                neighbors.add(Hex_db[loc[0]+1][loc[1]]);
            }
        }
        if ( loc[0] < 12 ) {
            if ( loc[1] < 12 ) {
//                System.out.println("BOTTOM");
                neighbors.add(Hex_db[loc[0]+1][loc[1]+1]);
            }
            if ( (loc[1]-loc[0]) != 6 ) {
//                System.out.println("BOTTOM LEFT");
                neighbors.add(Hex_db[loc[0]][loc[1]+1]);
            }
        }
        System.out.println(neighbors.size());
        return neighbors;
    }

    /**
     * Function to determine if a move is valid not. (currently only checks if cell is empty).
     * @param hexagon pass the concerned cell.
     * @return Boolean value depending on validity of move.
     */
    private boolean isValid(Polygon hexagon) {
        int[] loc = Cord_to_index(Cord_convert(hexagon));
        if (Hex_db[loc[0]][loc[1]] == null) {
//            System.out.println("Empty");
            return true;
        }
        else {
//            System.out.println("Occupied");
            return false;
        }
    }

    /**
     * Function to convert X and Y location values into grid coordinate system (e.g. "A1").
     *
     * @param object  the object passed into the function i.e. Hex.
     * @return Converted grid coordinates in alphanumeric.
     */
    private String[] Cord_convert(Polygon object) {
        // X starts from 300 up to 972 with 56 step.
        // Y starts from 41 up to 833 with 33 step.
        String[] loc;
        loc = object.getId().split("_");
//        System.out.println(loc[0] + " " + loc[1]);

//        for (char ch : object.getId().toCharArray()) {}
//        loc[0] += (char) ((x-300)/56);
//
//        loc[1] += (char) ((y-41)/33);
//
//        System.out.println(loc[0] + ": " + (x-300)/56 + "\n" + loc[1] + ": " + (y-41)/33);
        return loc;
    }

    private Label turnLabel;
    public void displayTurn(AnchorPane parent){
        if(turnLabel == null){
            turnLabel = new Label("Red players turn");
            turnLabel.setLayoutX(1095);
            turnLabel.setLayoutY(375);
            parent.getChildren().add(turnLabel);
        } else {
            if (Turn == Player.RED) {
                turnLabel.setText("Red players turn");
            } else if (Turn == Player.BLUE) {
                turnLabel.setText("Blue players turn");
            }
        }
    }

    public Team checkColor(Node node){
        return node.getTeam();
    }

    public ArrayList<Node> checkGroup(Node node){
        //check if it's apart of group and return that group
        if(RedGroupp == null){
            System.out.println("RedGroupp is null");
        }
        for(ArrayList<Node> group : RedGroupp){
            for(Node node1 : group){
                System.out.println(node1.toString());
                if(node1.equals(node)){
                    return group;
                }
            }
        }
        for (ArrayList<Node> group : BlueGroupp){
            for(Node node1 : group){
                if(node1.equals(node)){
                    return group;
                }
            }
        }
        System.out.println("we aint found shiiii");
        return null;
    }

//    private ArrayList<Node> getNeighbors(Node node){
//        ArrayList<Node> neighbors = new ArrayList<>();
//        return neighbors;
//    }

    public boolean isCapturing(Node node){
        ArrayList<Node> neighbors;
        neighbors = getNeighbors(node);
        for(Node neighbor : neighbors){
            if(neighbor.getTeam() == node.getTeam()){
                //group
                //non-capturing
                return false;
            }
            else if(neighbor.getTeam() == null){
                //empty
            }
            else{
                //opps
                ArrayList<Node> bomboclat = checkGroup(neighbor);
                ArrayList<Node> grap = checkGroup(neighbor);
                if(bomboclat.size() > grap.size()){
                    //cant do this move
                    return false;
                }
                else if(bomboclat.size() < grap.size()){
                    //remove that group
                    return true;
                }
            }
        }
        return false;

    }

    public void group(Node hexagon){
        //sample arrayList NEIGHBOUR
         ArrayList<Node> neighbours = getNeighbors(hexagon);
        ArrayList<Node> Blue = new ArrayList<>();
        ArrayList<Node> Red = new ArrayList<>();
        //get neighbours


        //change to node
        if(!neighbours.isEmpty()){
            for(Node neighbour : neighbours){
                if (neighbour == null) {
                    continue;  // Skip null neighbours instead of breaking the loop
                }
                if(neighbour.getTeam() == Team.Red){
                    switch (hexagon.getTeam()){
                        case Team.Blue:
                            break;
                        case Team.Red:
                            Red.add(neighbour);
                            Red.add(hexagon);
                            System.out.println("group red made");
                            RedGroupp.add(Red);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + hexagon.getTeam());
                    }
                }
                if(neighbour.getTeam() == Team.Blue){
                    switch (hexagon.getTeam()){
                        case Team.Blue:
                            Blue.add(neighbour);
                            Blue.add(hexagon);
                            System.out.println("group blue made");
                            BlueGroupp.add(Blue);
                            break;
                        case Team.Red:
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + hexagon.getTeam());
                    }
                }
            }
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
        Hex_db = null;
        Hex_db = new Node[13][13];
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
     *
     */
    public void Close() {
        System.exit(0);
    }

    /**
     * Handles highlighting of Cells.
     * @param mouseEvent - pass the Mouse event to function.
     */
    public void highlight(MouseEvent mouseEvent) {
        Polygon hexagon = (Polygon) mouseEvent.getSource();
        if (isValid(hexagon)) {
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
}