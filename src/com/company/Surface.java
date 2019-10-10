package com.company;

public class Surface {
    //powierzchnia składa się z 2 węzłów oraz wartości funkcji kształtu w tych węzłach

    private Node[] surface = new Node[2];
    private double[][] valueOfShapeFunctions;

    public Surface(Node node1, Node node2){
        this.surface[0] = node1;
        this.surface[1] = node2;
    }

    public Node[] getSurface() {
        return surface;
    }

    public double[][] getValueOfShapeFunctions() {
        return valueOfShapeFunctions;
    }

    public void setValueOfShapeFunctions(double[][] valueOfShapeFunctions) {
        this.valueOfShapeFunctions = valueOfShapeFunctions;
    }
}
