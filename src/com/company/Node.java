package com.company;

public class Node {

    private double x; //współrzędna x
    private double y; //współrzędna y
    private double t; //temperatura
    private int nodeID; // id węzła
    private boolean isOnEdge; //sprawdzenie węzeł znajduje się na krawędzi siatki
    private double initTemp; //temperatura początkowa węzła

    public Node(double x, double y, int nodeID, double t ,boolean isOnEdge) {
        this.x = x;
        this.y = y;
        this.t = t;
        this.nodeID = nodeID;
        this.isOnEdge = isOnEdge;
        this.initTemp = t;
    }

    public Node(Point point){
        this.x = point.getX();
        this.y = point.getY();
        nodeID = -1;
        isOnEdge = false;
    }

    public void setT(double t) {
        this.t = t;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getT() {
        return t;
    }

    public boolean isStatus() {
        return isOnEdge;
    }
}
