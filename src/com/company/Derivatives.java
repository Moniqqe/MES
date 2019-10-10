package com.company;

public class Derivatives {
    //pochodne ksi
    public double derivativeKsi1(double eta){
        return -0.25*(1-eta);
    }

    public double derivativeKsi2(double eta){
        return 0.25*(1-eta);
    }

    public double derivativeKsi3(double eta){
        return 0.25*(1+eta);
    }

    public double derivativeKsi4(double eta){
        return -0.25*(1+eta);
    }

    //pochodne eta
    public double derivativeEta1(double ksi){
        return -0.25*(1-ksi);
    }

    public double derivativeEta2(double ksi){
        return -0.25*(1+ksi);
    }

    public double derivativeEta3(double ksi){
        return 0.25*(1+ksi);
    }

    public double derivativeEta4(double ksi){
        return 0.25*(1-ksi);
    }
}
