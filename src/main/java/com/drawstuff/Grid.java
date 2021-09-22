package com.drawstuff;

import java.util.ArrayList;

public class Grid {

    private int size;
    private ArrayList<Cell> cells = new ArrayList<>();

    public Grid(int size){
        this.size = size;
        //generateGrid();
    }

//    private void generateGrid() {
//        for (int i = 0; i < this.size; i++){
//            for (int j = 0; j < this.size; j++){
//                Cell cell = new Cell();
//                this.cells.add(cell);
//            }
//        }
//    }

}
