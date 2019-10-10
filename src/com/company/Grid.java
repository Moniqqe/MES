package com.company;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private List<Node> nodeList = null; //lista wszystkich węzłów siatki
    private List<Element> elementList = null; //lista wszystkich elementów siatki
    private Data data; //pobranie wartości
    private int nh, ne; //ilość węzłów, elementów w siatce

    public Grid(Data data){
        this.data = data;
        nh = data.getNh();
        ne = data.getNe();

        nodeList = new ArrayList<>(nh);
        elementList = new ArrayList<>(ne);
    }

    public void generateGrid() {
        double dH = data.getDeltaH();
        double dL = data.getDeltaL();

        // generowanie listy węzłów
        for (int i = 0; i < data.getnL(); ++i) {
            for (int j = 0; j < data.getnH(); ++j) {
                double x = i * dL;
                double y = j * dH;
                boolean edge = false;
                if (x == 0.0 || y == 0.0 || x == data.getL() || y == data.getH()) {
                    edge = true;
                    //System.out.println("id: "+ (i * data.getnH() + j + 1) + " x: " + x + " y: " + y + " krawędź: " + Boolean.toString(edge));
                }
                //System.out.println("id: "+ (i * data.getnH() + j + 1) + " x: " + x + " y: " + y + " krawędź: " + Boolean.toString(status));
                nodeList.add(new Node(x, y, i * data.getnH() + j, data.gettStart(), edge));
            }
        }

        //generowanie listy elementów
        for (int i = 0 ; i < data.getnL() - 1; ++i){
            for (int j = 0; j < data.getnH() - 1; ++j){
                int [] tab = new int[4];

                tab[0] = j + i * data.getnH();
                tab[3] = tab[0] + 1;
                tab[1] = j + (i+1) * data.getnH();
                tab[2] = tab[1] + 1;

                Node [] nodes = new Node[4];
                int z = 0;
                for(int nodeId: tab){
                    nodes[z] = nodeList.get(nodeId);
                    z++;
                }
                elementList.add(new Element(tab, nodes));
            }
        }
    }

    public List getNodeList() {
        return nodeList;
    }

    public List getElementList() {
        return elementList;
    }
}
