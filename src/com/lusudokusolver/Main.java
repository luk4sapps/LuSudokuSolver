package com.lusudokusolver;

import com.lusudokusolver.util.Sudoku;
import com.lusudokusolver.util.SudokuSolver;

/**
 * Created by Lukas on 15.03.2017.
 */

public class Main {

    public static void main(String[] args) {
        int[][] sudokuData =
                {
                        {6, 0, 0, 8, 0, 0, 9, 0, 7},
                        {3, 7, 0, 9, 0, 0, 5, 0, 0},
                        {0, 0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 0, 2, 3, 0, 6, 1, 0, 0},
                        {0, 0, 0, 0, 7, 0, 0, 0, 3},
                        {8, 0, 3, 0, 5, 0, 0, 0, 0},
                        {0, 0, 0, 4, 0, 0, 0, 0, 9},
                        {0, 0, 0, 0, 0, 0, 3, 0, 0},
                        {4, 6, 0, 0, 2, 0, 0, 0, 0}
                };

        Sudoku sudoku = new Sudoku(sudokuData);
        SudokuSolver.solveSudoku(sudoku);
    }
}
