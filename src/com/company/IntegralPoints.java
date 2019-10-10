package com.company;


public class IntegralPoints {
//klasa określająca położenie punktów całkowania

    private Point[] iPoints = new Point[4];

    public IntegralPoints() {
        //(ksi,eta)
        iPoints[0] = new Point(-0.577, -0.577);
        iPoints[1] = new Point(0.577, -0.577);
        iPoints[2] = new Point(0.577, 0.577);
        iPoints[3] = new Point(-0.577, 0.577);
    }

    public Point[] getiPoints() {
        return iPoints;
    }
}