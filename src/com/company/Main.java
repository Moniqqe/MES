package com.company;

public class Main {

    public static void main(String[] args) {

        Data x = new Data();
        Matrixes m = new Matrixes();
        double[] table;
        int counter;
        double temp, min=0, max=0;

        for (int i = 0; i < x.getTau(); i += x.getdTau()) {

            //System.out.println(i + "\n");

            m.compute();
            table = Gauss.gaussElimination(x.getNh(), m.gethGlobal().getArray(), m.getpGlobal());

            for (int j = 0; j < x.getNh(); j++) {
                ((Node) (m.getGrid().getNodeList().get(j))).setT(table[j]);

            }

            counter = 0;
            min = ((Node) (m.getGrid().getNodeList().get(1))).getT();
            max = ((Node) (m.getGrid().getNodeList().get(1))).getT();

            for (int j = 0; j < x.getnL(); j++) {
                for (int k = 0; k < x.getnH(); k++) {
                    temp = ((Node) (m.getGrid().getNodeList().get(counter++))).getT();
                    if (temp > max)
                        max = temp;
                    if (temp < min)
                        min = temp;
                    //System.out.printf("%.5f\t", temp);
                }
                //System.out.println("");
            }
            System.out.printf("%.3f\t%.3f\n", min, max);
            //System.out.println("\n\n");
        }
        //System.out.printf("%.3f\t%.3f\n", min, max);
    }
}
