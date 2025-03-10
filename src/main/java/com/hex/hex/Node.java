package com.hex.hex;

import java.util.Arrays;
import java.util.Objects;

enum Team {Red, Blue};

public class Node {

    private static final char[] COL_LABELS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M'};

    private int row;
    private int col;

    private Team team;

    public Node(int row, char colLetter, String team) {
        this.row = row - 1;
        this.col = letterToIndex(colLetter);

        if (this.row < 0 || this.row >= 13 || this.col < 0 || this.col >= 13) {
            throw new IllegalArgumentException("Invalid coordinate: " + colLetter + row);
        }
        if(!Objects.equals(team, "Red") && !Objects.equals(team, "Blue")) {
            throw new IllegalArgumentException("Invalid team: " + team);
        }
        else if(team.equals("Red")) {
            this.team = Team.Red;
        }
        else if(team.equals("Blue")) {
            this.team = Team.Blue;
        }
    }


    private int letterToIndex(char letter) {
        for (int i = 0; i < COL_LABELS.length; i++) {
            if (COL_LABELS[i] == letter) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid column letter: " + letter);
    }

    public String getCoordinate() {
        return COL_LABELS[col] + String.valueOf(row + 1);
    }

    public int[] getIndexCords() {
        return new int[]{col, row};
    }

    @Override
    public String toString() {
        return "HexNode{" + getCoordinate() + "}" + team.toString();
    }

    public Team getTeam() {
        return team;
    }

//    public static void main(String[] args) {
//        Node node = new Node(1, 'A', "Red");  // A1
//        System.out.println(node); // Output: HexNode{A1}
//
//        Node anotherNode = new Node(6, 'D', "Blue");  // D6
//        System.out.println(anotherNode); // Output: HexNode{D6}
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Node node = (Node) obj;
        return Arrays.equals(this.getIndexCords(), node.getIndexCords());
    }

}
