package com.company;

import Jama.Matrix;

public class Jacobian {
    private Matrix jacobian; //macierz jakobiego
    private Matrix invertedJacobian; //odwrotna macierz jakobiego
    private int integralPoint; //punkt całkowania
    private Matrix NEta;
    private Matrix NKsi;
    private double determinant; //wyznacznik macierzy jakobiego - jakobian

    public Jacobian(int point, double[] x, double[] y, Matrix NEta, Matrix NKsi) {
        this.integralPoint = point;
        this.NKsi = NKsi;
        this.NEta = NEta;

        jacobian = new Matrix(2, 2);

        //Nksi i Neta są to macierze 4x4 z numer punktu x numer pochodnej
        //wszystkie pochodne ze wzorów z pdf
        //pochodna x po ksi
        double derivativeXKsi = this.NKsi.get(integralPoint, 0) * x[0]
                + this.NKsi.get(integralPoint, 1) * x[1]
                + this.NKsi.get(integralPoint, 2) * x[2]
                + this.NKsi.get(integralPoint, 3) * x[3];

        //poczhodna x po eta
        double derivativeXEta = this.NEta.get(integralPoint, 0) * x[0]
                + this.NEta.get(integralPoint, 1) * x[1]
                + this.NEta.get(integralPoint, 2) * x[2]
                + this.NEta.get(integralPoint, 3) * x[3];

        //pochodna y po ksi
        double derivativeYKsi = this.NKsi.get(integralPoint, 0) * y[0]
                + this.NKsi.get(integralPoint, 1) * y[1]
                + this.NKsi.get(integralPoint, 2) * y[2]
                + this.NKsi.get(integralPoint, 3) * y[3];

        //pochodna y po eta
        double derivativeYEta = this.NEta.get(integralPoint, 0) * y[0]
                + this.NEta.get(integralPoint, 1) * y[1]
                + this.NEta.get(integralPoint, 2) * y[2]
                + this.NEta.get(integralPoint, 3) * y[3];

        //wstawiamy do macierzy jakobiego
        jacobian.set(0, 0, derivativeXKsi);
        jacobian.set(0, 1, derivativeYKsi);
        jacobian.set(1, 0, derivativeXEta);
        jacobian.set(1, 1, derivativeYEta);

        //wyliczmy wyznacznik - jakobian
        determinant = jacobian.det();

        //odwracamy macierz jakobiego
        invertedJacobian = jacobian.transpose();
        invertedJacobian.set(0,1,invertedJacobian.get(0,1)*(-1.0));
        invertedJacobian.set(1,0,invertedJacobian.get(1,0)*(-1.0));
        invertedJacobian = invertedJacobian.times(1.0/determinant);
    }

    public Matrix getInvertedJacobian() {
        return invertedJacobian;
    }

    public double getDeterminant() {
        return determinant;
    }
}