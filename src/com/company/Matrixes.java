package com.company;

import Jama.Matrix;

public class Matrixes {

    private Data data = new Data();
    private Grid grid;
    private Matrix hLocal;
    private Matrix hGlobal;
    private double[] pLocal;
    private double[] pGlobal;
    private Matrix derivativesEta;
    private Matrix derivativesKsi;
    private Matrix shapeFunctionsMatrix;
    private IntegralPoints integralPoints = new IntegralPoints();
    private ShapeFunctions shapeFunctions = new ShapeFunctions();
    private Derivatives derivatives = new Derivatives();
    private Node[] nodes;
    private int[] IDs;
    private Element element, localElement;
    private Surface[] surfaces;
    private Surface surface;
    private double[][] shapeFunctionsValues;
    private Jacobian jacobian;
    private double[] dNdX;
    private double[] dNdY;
    private double[] cordX;
    private double[] cordY;
    private double[] startTemperatures;
    private double interpolatedTemperature;
    private double cellFromMatrixC;
    private int ID;
    private double jacobiansDeterminant;
    private int temporary1;
    private int temporary2;

    public Matrixes() {
        this.hLocal = null;
        this.hGlobal = new Matrix(data.getNh(), data.getNh());
        this.pLocal = null;
        this.pGlobal = new double[data.getNh()];
        for (int i = 0; i < data.getNh(); i++)
            pGlobal[i] = 0;
        generateShapeFunctionsMatrixes();
        generateElement();
        grid = new Grid(data);
        grid.generateGrid();
    }

    private void generateShapeFunctionsMatrixes() {
        Point[] integralPointsTab = integralPoints.getiPoints();
        derivativesEta = new Matrix(4, 4);
        derivativesKsi = new Matrix(4, 4);
        shapeFunctionsMatrix = new Matrix(4, 4);
        for (int i = 0; i < 4; i++) {
            derivativesKsi.set(i, 0, derivatives.derivativeKsi1(integralPointsTab[i].getY()));
            derivativesKsi.set(i, 1, derivatives.derivativeKsi2(integralPointsTab[i].getY()));
            derivativesKsi.set(i, 2, derivatives.derivativeKsi3(integralPointsTab[i].getY()));
            derivativesKsi.set(i, 3, derivatives.derivativeKsi4(integralPointsTab[i].getY()));
            derivativesEta.set(i, 0, derivatives.derivativeEta1(integralPointsTab[i].getX()));
            derivativesEta.set(i, 1, derivatives.derivativeEta2(integralPointsTab[i].getX()));
            derivativesEta.set(i, 2, derivatives.derivativeEta3(integralPointsTab[i].getX()));
            derivativesEta.set(i, 3, derivatives.derivativeEta4(integralPointsTab[i].getX()));
            shapeFunctionsMatrix.set(i, 0, shapeFunctions.N1(integralPointsTab[i].getX(), integralPointsTab[i].getY()));
            shapeFunctionsMatrix.set(i, 1, shapeFunctions.N2(integralPointsTab[i].getX(), integralPointsTab[i].getY()));
            shapeFunctionsMatrix.set(i, 2, shapeFunctions.N3(integralPointsTab[i].getX(), integralPointsTab[i].getY()));
            shapeFunctionsMatrix.set(i, 3, shapeFunctions.N4(integralPointsTab[i].getX(), integralPointsTab[i].getY()));
        }
    }

    private void generateElement() {
        Point[] integralPointsTab = integralPoints.getiPoints();
        nodes = new Node[4];
        IDs = new int[]{0, 0, 0, 0};

        for (int i = 0; i < nodes.length; i++)
            nodes[i] = new Node(integralPointsTab[i]);

        element = new Element(IDs, nodes);

        surfaces = new Surface[4];

        surfaces[0] = new Surface(new Node(new Point(-1.0, 0.577)), new Node(new Point(-1.0, -0.577)));
        surfaces[1] = new Surface(new Node(new Point(-0.577, -1)), new Node(new Point(0.577, -1.0)));
        surfaces[2] = new Surface(new Node(new Point(1.0, -0.577)), new Node(new Point(1.0, 0.577)));
        surfaces[3] = new Surface(new Node(new Point(0.577, 1.0)), new Node(new Point(-0.577, 1.0)));

        element.setSurfaces(surfaces);

        for (int m = 0; m < 4; m++) {
            shapeFunctionsValues = new double[2][4];
            for (int n = 0; n < 2; n++) {
                shapeFunctionsValues[n][0] = shapeFunctions.N1(surfaces[m].getSurface()[n].getX(), surfaces[m].getSurface()[n].getY());
                shapeFunctionsValues[n][1] = shapeFunctions.N2(surfaces[m].getSurface()[n].getX(), surfaces[m].getSurface()[n].getY());
                shapeFunctionsValues[n][2] = shapeFunctions.N3(surfaces[m].getSurface()[n].getX(), surfaces[m].getSurface()[n].getY());
                shapeFunctionsValues[n][3] = shapeFunctions.N4(surfaces[m].getSurface()[n].getX(), surfaces[m].getSurface()[n].getY());
            }
            surfaces[m].setValueOfShapeFunctions(shapeFunctionsValues);
        }
    }

    public void compute() {
        for (int i = 0; i < pGlobal.length; i++) pGlobal[i] = 0.;
        hGlobal = new Matrix(data.getNh(), data.getNh());

        dNdX = new double[4];
        dNdY = new double[4];
        cordX = new double[4];
        cordY = new double[4];
        startTemperatures = new double[4];
        interpolatedTemperature = 0.;
        jacobiansDeterminant = 0.;

        //Iterujemy po wszystkich elementach siatki
        for (int gridsElements = 0; gridsElements < data.getNe(); gridsElements++) {
            localElement = (Element) (grid.getElementList().get(gridsElements));
            hLocal = new Matrix(4, 4);
            pLocal = new double[]{0., 0., 0., 0.};

            for (int i = 0; i < 4; i++) {
                //pobieramy ID węzłów elementu
                ID = localElement.getID()[i];
                //zapisujemy współrzędne węzłów
                cordX[i] = ((Node) (grid.getNodeList().get(ID))).getX();
                cordY[i] = ((Node) (grid.getNodeList().get(ID))).getY();
                //temperatura startowa
                startTemperatures[i] = ((Node) (grid.getNodeList().get(ID))).getT();
            }

            for (int iteration = 0; iteration < 4; iteration++) {
                jacobian = new Jacobian(iteration, cordX, cordY, derivativesEta, derivativesKsi);
                interpolatedTemperature = 0;

                for (int i = 0; i < 4; i++) {
                    //wyznaczenie pochodnej dN po dX
                    dNdX[i] = jacobian.getInvertedJacobian().get(0, 0) * derivativesKsi.get(iteration, i)
                            + jacobian.getInvertedJacobian().get(0, 1) * derivativesEta.get(iteration, i);
                    //wyznaczenie pochodnej dN po dY
                    dNdY[i] = jacobian.getInvertedJacobian().get(1, 0) * derivativesKsi.get(iteration, i)
                            + jacobian.getInvertedJacobian().get(1, 1) * derivativesEta.get(iteration, i);
                    //oraz temperature interpolacyjnie (t = N1*t1 * ... *N4*t4)
                    interpolatedTemperature += startTemperatures[i] * this.shapeFunctionsMatrix.get(iteration, i);
                }

                //bierzemy wartość bezwzględną jakobianu
                jacobiansDeterminant = Math.abs(jacobian.getDeterminant());

                
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        double temp;
                        //całka z c * ro * N * NT, mnożymy przez jakobian aby pozbyć się całki
                        cellFromMatrixC = data.getC() * data.getRo() * shapeFunctionsMatrix.get(iteration, i) * shapeFunctionsMatrix.get(iteration, j) * jacobiansDeterminant;
                        //pierwszy człon wzoru na macierz H (czyli bez warunku brzegowago)
                        temp = hLocal.get(i, j) + data.getK() * (dNdX[i] * dNdX[j] + dNdY[i] * dNdY[j]) * jacobiansDeterminant + cellFromMatrixC / data.getdTau();
                        hLocal.set(i, j, temp);
                        temp = pLocal[i] + cellFromMatrixC / data.getdTau() * interpolatedTemperature;
                        pLocal[i] = temp;
                    }
                }
            }

            //warunki brzegowe
            //iterujemy po ilości węzłów leżących na krawędziach
            for (int surfIter = 0; surfIter < localElement.getNumOfEdgeNodes(); surfIter++) {
                //id powierzni leżącej na krawędziach
                ID = localElement.getIdOfEdgeSurfaces().get(surfIter);
                surface = localElement.getSurfaces(ID);

                //długość przez 2 - wyznacznik
                jacobiansDeterminant = Math.sqrt(Math.pow((surface.getSurface()[0].getX() - surface.getSurface()[1].getX()), 2)
                        + Math.pow((surface.getSurface()[0].getY() - surface.getSurface()[1].getY()), 2)) / 2.0;

                //2 punkty na każdej powierzchni
                for (int i = 0; i < 2; i++) {
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < 4; k++) {
                            double valueOfTemperature = hLocal.get(j, k);
                            //uwzględnienie warunku brzegowego
                            valueOfTemperature += data.getAlfa() * element.getSurfaces()[ID].getValueOfShapeFunctions()[i][j]
                                    * element.getSurfaces()[ID].getValueOfShapeFunctions()[i][k] * jacobiansDeterminant;
                            hLocal.set(j, k, valueOfTemperature);
                        }
                        pLocal[j] += data.getAlfa() * data.gettAmbient() * element.getSurfaces()[ID].getValueOfShapeFunctions()[i][j] * jacobiansDeterminant;
                    }
                }
            }

            //agregacja
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    temporary1 = localElement.getID()[i];
                    temporary2 = localElement.getID()[j];
                    double valueOfTemperature  = hGlobal.get(temporary1, temporary2) + hLocal.get(i, j);
                    hGlobal.set(temporary1, temporary2, valueOfTemperature );
                }
                pGlobal[localElement.getID()[i]] += pLocal[i];
            }
        }
    }

    public Matrix gethGlobal() {
        return hGlobal;
    }

    public double[] getpGlobal() {
        return pGlobal;
    }

    public Grid getGrid() {
        return grid;
    }
}
