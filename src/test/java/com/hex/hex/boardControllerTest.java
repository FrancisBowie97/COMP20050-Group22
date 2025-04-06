package com.hex.hex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class boardControllerTest {

    /*
        Testing the checkColor function to return the Team of a given Node.
     */
    @Test
    void checkColorTest() {
        Node n = new Node(2,'B', "Red");
        Node n2 = new Node(5,'G', "Blue");
        Node n3 = new Node(1,'D', "Red");

        assertEquals(Team.Red,boardController.checkColor(n));
        assertEquals(Team.Blue,boardController.checkColor(n2));
        assertEquals(Team.Red,boardController.checkColor(n3));
        assertNotEquals(null,boardController.checkColor(n));

    }
}