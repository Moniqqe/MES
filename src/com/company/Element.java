package com.company;

import java.util.LinkedList;
import java.util.List;

public class Element {

    private int[] ID = null; //id węzłów elementu
    private Node[] nodes; //węzły elementu
    private Surface[] surfaces = new Surface[4]; //każdy element ma 4 powierzchnie
    private int numOfEdgeNodes = 0; //ile węzłów elementu leży na krawędziach
    private List<Integer> idOfEdgeSurfaces = new LinkedList<>();//id powierzchni leżących na krawędziach



    public Element(int[] ID, Node[] nodes) {
        this.ID = ID;
        this.nodes = nodes;

        //powierzchnie numerowane są od dołu przeciwnie do zegara
        surfaces[0] = new Surface(nodes[3], nodes[0]);
        surfaces[1] = new Surface(nodes[0], nodes[1]);
        surfaces[2] = new Surface(nodes[1], nodes[2]);
        surfaces[3] = new Surface(nodes[2], nodes[3]);

        //sprawdzenie czy powierznia leży na krawędzi
        for(int i =0; i<4; i++){
            if(surfaces[i].getSurface()[0].isStatus() && surfaces[i].getSurface()[1].isStatus()){

                idOfEdgeSurfaces.add(i);
            }
        }
        for(Surface surface: surfaces){
            if(surface.getSurface()[0].isStatus() && surface.getSurface()[1].isStatus())
                numOfEdgeNodes++;
        }
    }

    public int[] getID() {
        return ID;
    }

    public Surface[] getSurfaces() {
        return surfaces;
    }

    public Surface getSurfaces(int id){
        if(0<=id && id<=4)
            return surfaces[id];
        else
            return null;
    }

    public int getNumOfEdgeNodes() {
        return numOfEdgeNodes;
    }

    public List<Integer> getIdOfEdgeSurfaces() {
        return idOfEdgeSurfaces;
    }

    public void setSurfaces(Surface[] surfaces) {
        this.surfaces = surfaces;
    }

}
