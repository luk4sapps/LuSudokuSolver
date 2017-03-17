package com.lusudokusolver;

import com.lusudokusolver.util.Sudoku;

/**
 * Created by Lukas on 15.03.2017.
 */

public class Main {

    public static void main(String[] args) {
        int[][] sudokuData =
                {
                        {6, 0, 5, 8, 0, 4, 9, 3, 7},
                        {3, 7, 0, 9, 0, 0, 5, 0, 8},
                        {0, 0, 1, 0, 3, 0, 4, 0, 0},
                        {0, 0, 2, 3, 0, 6, 1, 0, 5},
                        {0, 0, 6, 0, 7, 0, 0, 0, 3},
                        {8, 0, 3, 2, 5, 0, 7, 0, 0},
                        {0, 0, 0, 4, 0, 5, 0, 0, 9},
                        {0, 5, 8, 0, 0, 0, 3, 0, 4},
                        {4, 6, 9, 0, 2, 3, 0, 0, 1}
                };

        Sudoku sudoku = new Sudoku(sudokuData);
        sudoku.printSudoku();
        sudoku.generatePossibleNumbers();

        System.out.println("STARTING!");

        int lastCellsLeft = sudoku.getCellsLeft();
        while (!sudoku.isSolved()) {
            /* print info */
            sudoku.printInformation();

            /* apply methods */
            sudoku.processNakedSingles();
            sudoku.processHiddenSingles();

            /* check if solved something during current session, if not give up */
            int newCellsLeft = sudoku.getCellsLeft();
            if (newCellsLeft == lastCellsLeft)
                break;
            lastCellsLeft = newCellsLeft;
        }

        System.out.println(sudoku.isSolved() ? "SOLVED!" : "FAILED TO SOLVE!");

        sudoku.printSudoku();
    }
}
