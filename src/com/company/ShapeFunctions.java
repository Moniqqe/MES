package com.company;

//wzory przepisane z pdf'a o jakobianie
public class ShapeFunctions{

    public double N1(double ksi, double eta){
        return 0.25 * ((1-ksi)*(1-eta));
    }

    public double N2(double ksi, double eta){
        return 0.25 * ((1+ksi)*(1-eta));
    }

    public double N3(double ksi, double eta){
        return 0.25 * ((1+ksi)*(1+eta));
    }

    public double N4(double ksi, double eta){
        return 0.25 * ((1-ksi)*(1+eta));
    }
}

