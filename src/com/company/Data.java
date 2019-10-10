package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Data {

    private double H; //wysokość siatki
    private double L; //długość siatki
    private int nH; //ilość węzłów na wysokość
    private int nL; // ilość węzłów na długość
    private int nh; // ilość węzłów
    private int ne; //ilość elementów
    private double deltaH; //odległość między węzłami na wysokość
    private double deltaL; //odległość między węzłami na długość
    private double tStart; // temperatura startowa
    private double tau; //czas trwania symulacji
    private double dTau; //skok czasu
    private double alfa; //współczynnik wymiany ciepła
    private double k; //przewodność
    private double c; //ciepło właściwe
    private double ro; //gęstość
    private double tAmbient; //temperatura otoczenia

    public Data() {
        scanFromFile();
        this.setNh(this.getnH() * this.getnL());
        this.setNe((this.getnL() - 1) * (this.getnH() - 1));
        this.setDeltaH(this.getH() / (this.getnH() - 1));
        this.setDeltaL(this.getL() / (this.getnL() - 1));
    }

    public double getH() {
        return H;
    }

    public double getL() {
        return L;
    }

    public int getnH() {
        return nH;
    }

    public int getnL() {
        return nL;
    }

    public double getDeltaH() {
        return deltaH;
    }

    public double getDeltaL() {
        return deltaL;
    }

    public int getNh() {
        return nh;
    }

    public void setNh(int nh) {
        this.nh = nh;
    }

    public int getNe() {
        return ne;
    }

    public void setNe(int ne) {
        this.ne = ne;
    }

    public void setDeltaH(double deltaH) {
        this.deltaH = deltaH;
    }

    public void setDeltaL(double deltaL) {
        this.deltaL = deltaL;
    }

    public double gettStart() {
        return tStart;
    }

    public double getTau() {
        return tau;
    }

    public double getdTau() {
        return dTau;
    }

    public double getAlfa() {
        return alfa;
    }

    public double getK() {
        return k;
    }

    public double getC() {
        return c;
    }

    public double getRo() {
        return ro;
    }

    public double gettAmbient() {
        return tAmbient;
    }

    private void scanFromFile() {
        File paramFile = new File("params.txt");
        Scanner in = null;
        try {
            in = new Scanner(paramFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        tStart = Double.parseDouble(in.nextLine());
//        System.out.println(tStart);
        tau = Double.parseDouble(in.nextLine());
//        System.out.println(tau);
        dTau = Double.parseDouble(in.nextLine());
//        System.out.println(dTau);
        tAmbient = Double.parseDouble(in.nextLine());
//        System.out.println(tAmbient);
        alfa = Double.parseDouble(in.nextLine());
//        System.out.println(alfa);
        H = Double.parseDouble(in.nextLine());
//        System.out.println(H);
        L = Double.parseDouble(in.nextLine());
//        System.out.println(L);
        nH = Integer.parseInt(in.nextLine());
//        System.out.println(nH);
        nL = Integer.parseInt(in.nextLine());
//        System.out.println(nL);
        c = Double.parseDouble(in.nextLine());
//        System.out.println(c);
        k = Double.parseDouble(in.nextLine());
//        System.out.println(k);
        ro = Double.parseDouble(in.nextLine());
//        System.out.println(ro);
    }
}
