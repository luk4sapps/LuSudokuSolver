package com.lusudokusolver.util;

import static com.lusudokusolver.util.Sudoku.NINE;
import static com.lusudokusolver.util.Sudoku.THREE;

/**
 * Created by Lukas on 18.03.2017.
 */

public class SudokuSolver {

    public static void solveSudoku(Sudoku sudoku) {
        System.out.println("STARTING!");

        sudoku.generatePossibleNumbers();

        int lastCellsLeft = sudoku.getCellsLeft();
        while (!sudoku.isSolved()) {
            /* print info */
            sudoku.printInformation();

            /* apply methods to reduce possible numbers */
            //SudokuSolver.processHiddenSubsets(sudoku);

            /* apply methods to find numbers */
            processNakedSingles(sudoku);
            processHiddenSingles(sudoku);

            /* check if solved something during current session, if not give up */
            int newCellsLeft = sudoku.getCellsLeft();
            if (newCellsLeft == lastCellsLeft)
                break;
            lastCellsLeft = newCellsLeft;
        }

        System.out.println(sudoku.isSolved() ? "SOLVED!" : "FAILED TO SOLVE!");
        sudoku.printSudoku();
    }

    /* method to reduce possible numbers : */


    /* method to find numbers : only one possible place for a number */
    private static void processNakedSingles(Sudoku sudoku) {
        for (int i = 0; i < 9; i++) {
            int num = i + 1;

            /* (1.) row */
            for (int y = 0; y < NINE; y++) {
                /* possible positions for the number */
                int positionsForNumber = 0;
                int lastX = -1;

                for (int x = 0; x < NINE; x++) {
                    if (sudoku.getSudokuData()[y][x] == 0 && sudoku.getPossibleNumbers()[y][x].contains(num)) {
                        positionsForNumber++;
                        lastX = x;
                    }
                    /* skip row if there are more than one possible positions */
                    if (positionsForNumber > 1)
                        break;
                }

                /* found one */
                if (positionsForNumber == 1) {
                    //System.out.println("Naked single found: " + lastX + "/" + y + " : " + num);
                    sudoku.setNumberAndUpdate(lastX, y, num);
                }
            }

            /* (2.) column */
            for (int x = 0; x < NINE; x++) {
                /* possible positions for the number */
                int positionsForNumber = 0;
                int lastY = -1;

                for (int y = 0; y < NINE; y++) {
                    if (sudoku.getSudokuData()[y][x] == 0 && sudoku.getPossibleNumbers()[y][x].contains(num)) {
                        positionsForNumber++;
                        lastY = y;
                    }
                    /* skip row if there are more than one possible positions */
                    if (positionsForNumber > 1)
                        break;
                }

                /* found one */
                if (positionsForNumber == 1) {
                    //System.out.println("Naked single found: " + x + "/" + lastY + " : " + num);
                    sudoku.setNumberAndUpdate(x, lastY, num);
                }
            }

            /* (3.) 3x3 field */
            for (int areaY = 0; areaY < THREE; areaY++) {
                for (int areaX = 0; areaX < THREE; areaX++) {
                    /* possible positions for the number */
                    int positionsForNumber = 0;
                    int lastX = -1;
                    int lastY = -1;

                    for (int yAdd = 0; yAdd < THREE; yAdd++) {
                        for (int xAdd = 0; xAdd < THREE; xAdd++) {
                            int x = areaX * THREE + xAdd;
                            int y = areaY * THREE + yAdd;

                            if (sudoku.getSudokuData()[y][x] == 0 && sudoku.getPossibleNumbers()[y][x].contains(num)) {
                                positionsForNumber++;
                                lastX = x;
                                lastY = y;
                            }
                        }
                    }

                    /* found one */
                    if (positionsForNumber == 1) {
                        //System.out.println("Naked single found: " + lastX + "/" + lastY + " : " + num);
                        sudoku.setNumberAndUpdate(lastX, lastY, num);
                    }

                }
            }

        }
    }

    /* method to find numbers : hidden singles (only one possible number for a cell) */
    private static void processHiddenSingles(Sudoku sudoku) {
        for (int y = 0; y < NINE; y++) {
            for (int x = 0; x < NINE; x++) {
                if (sudoku.getSudokuData()[y][x] == 0) {
                    for (int i = 0, len = sudoku.getPossibleNumbers()[y][x].size(); i < len; i++) {
                        int num = sudoku.getPossibleNumbers()[y][x].get(i);

                        boolean onlyPossibleNumber;

                         /* (1.) in row */
                        onlyPossibleNumber = true;
                        for (int x2 = 0; x2 < NINE; x2++) {
                            if (x2 == x)
                                continue;

                            if (sudoku.getPossibleNumbers()[y][x2].contains(num)) {
                                onlyPossibleNumber = false;
                            }
                        }
                        if (onlyPossibleNumber) {
                            //System.out.println("Hidden single found: " + x + "/" + y + " : " + possibleNumbers[y][x].get(i));
                            sudoku.getSudokuData()[y][x] = sudoku.getPossibleNumbers()[y][x].get(i);

                            sudoku.setNumberAndUpdate(x, y, num);
                            break;
                        }

                        /* (2.) in column */
                        onlyPossibleNumber = true;
                        for (int y2 = 0; y2 < NINE; y2++) {
                            if (y2 == y)
                                continue;

                            if (sudoku.getPossibleNumbers()[y2][x].contains(num)) {
                                onlyPossibleNumber = false;
                            }
                        }
                        if (onlyPossibleNumber) {
                            //System.out.println("Hidden single found: " + x + "/" + y + " : " + possibleNumbers[y][x].get(i));
                            sudoku.getSudokuData()[y][x] = sudoku.getPossibleNumbers()[y][x].get(i);

                            sudoku.setNumberAndUpdate(x, y, num);
                            break;
                        }

                        /* (3.) in 3x3 area */
                        onlyPossibleNumber = true;
                        int areaX = sudoku.divideByThree(x);
                        int areaY = sudoku.divideByThree(y);

                        for (int yAdd = 0; yAdd < THREE; yAdd++) {
                            for (int xAdd = 0; xAdd < THREE; xAdd++) {
                                if ((areaY * THREE + yAdd) == y && (areaX * THREE + xAdd) == x)
                                    continue;

                                if (sudoku.getPossibleNumbers()[areaY * THREE + yAdd][areaX * THREE + xAdd].contains(num)) {
                                    onlyPossibleNumber = false;
                                }
                            }
                        }
                        if (onlyPossibleNumber) {
                            //System.out.println("Hidden single found: " + x + "/" + y + " : " + possibleNumbers[y][x].get(i));
                            sudoku.getSudokuData()[y][x] = sudoku.getPossibleNumbers()[y][x].get(i);

                            sudoku.setNumberAndUpdate(x, y, num);
                            break;
                        }
                    }
                }
            }
        }
    }

}
