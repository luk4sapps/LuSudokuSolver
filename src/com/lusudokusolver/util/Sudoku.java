package com.lusudokusolver.util;

import java.util.ArrayList;

/**
 * Created by Lukas on 15.03.2017.
 */

public class Sudoku {
    static final int NINE = 9, THREE = 3;

    private int[][] sudokuData;
    private ArrayList<Integer>[][] possibleNumbers;

    public Sudoku(int[][] sudokuData) {
        this.sudokuData = sudokuData;

        possibleNumbers = new ArrayList[NINE][NINE];
        for (int y = 0; y < NINE; y++)
            for (int x = 0; x < NINE; x++)
                possibleNumbers[y][x] = new ArrayList<>();
    }

    void generatePossibleNumbers() {
        /* only for cells with zeroes! */

        for (int y = 0; y < NINE; y++) {
            for (int x = 0; x < NINE; x++) {
                if (sudokuData[y][x] == 0) {
                    ArrayList<Integer> possibleNumbersTmp = new ArrayList<>();

                    /* add all numbers into array */
                    for (int i = 0; i < NINE; i++) {
                        possibleNumbersTmp.add(i + 1);
                    }

                    /* remove impossible ones */

                    /* (1.) row */
                    for (int x2 = 0; x2 < NINE; x2++) {
                        int num = sudokuData[y][x2];
                        if (possibleNumbersTmp.contains(num)) {
                            possibleNumbersTmp.remove(possibleNumbersTmp.indexOf(num));
                        }
                    }
                    /* (2.) column */
                    for (int y2 = 0; y2 < NINE; y2++) {
                        int num = sudokuData[y2][x];
                        if (possibleNumbersTmp.contains(num)) {
                            possibleNumbersTmp.remove(possibleNumbersTmp.indexOf(num));
                        }
                    }
                    /* (3.) 3x3 area */
                    int areaX = divideByThree(x);
                    int areaY = divideByThree(y);

                    //System.out.println("--------------------------");

                    for (int yAdd = 0; yAdd < THREE; yAdd++) {
                        for (int xAdd = 0; xAdd < THREE; xAdd++) {
                            int num = sudokuData[areaY * THREE + yAdd][areaX * THREE + xAdd];
                            //System.out.print(num + " ");
                            if (possibleNumbersTmp.contains(num)) {
                                possibleNumbersTmp.remove(possibleNumbersTmp.indexOf(num));
                            }
                        }
                    }
                    //System.out.println();

                    //System.out.print(x + "/" + y + " -> " + sudokuData[y][x] + " @ " + areaX + " - " + areaY + " : ");
                    for (Integer i : possibleNumbersTmp) {
                        //System.out.print(i + " ");
                    }
                    //System.out.println();

                    /* add possible number tmp to actual possible numbers array */
                    possibleNumbers[y][x] = possibleNumbersTmp;
                }
            }
        }
    }

    /* set number and remove possibilites for a number in x / y pos */
    void setNumberAndUpdate(int x, int y, int num) {
        sudokuData[y][x] = num;

        /* (1.) row */
        for (int x2 = 0; x2 < NINE; x2++) {
            if (x2 == x)
                continue;

            if (possibleNumbers[y][x2].contains(num)) {
                possibleNumbers[y][x2].remove(possibleNumbers[y][x2].indexOf(num));
            }
        }
        /* (2.) column */
        for (int y2 = 0; y2 < NINE; y2++) {
            if (y2 == y)
                continue;

            if (possibleNumbers[y2][x].contains(num)) {
                possibleNumbers[y2][x].remove(possibleNumbers[y2][x].indexOf(num));
            }
        }
        /* (3.) 3x3 area */
        int areaX = divideByThree(x);
        int areaY = divideByThree(y);

        for (int yAdd = 0; yAdd < THREE; yAdd++) {
            for (int xAdd = 0; xAdd < THREE; xAdd++) {
                if ((areaY * THREE + yAdd) == y && (areaX * THREE + xAdd) == x)
                    continue;

                if (possibleNumbers[areaY * THREE + yAdd][areaX * THREE + xAdd].contains(num))
                    possibleNumbers[areaY * THREE + yAdd][areaX * THREE + xAdd].remove(possibleNumbers[areaY * THREE + yAdd][areaX * THREE + xAdd].indexOf(num));
            }
        }
    }

    /* seperate number into big cells (x/y) */

    int divideByThree(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 0;
            case 2:
                return 0;
            case 3:
                return 1;
            case 4:
                return 1;
            case 5:
                return 1;
            case 6:
                return 2;
            case 7:
                return 2;
            case 8:
                return 2;

            default:
                return -1;
        }
    }

    void printInformation() {
        System.out.println("Cells left: " + getCellsLeft() + "/" + (9 * 9) + " ||| Possible numbers: " + getPossibleNumbersSize());
    }

    void printSudoku() {
        for (int y = 0; y < NINE; y++) {
            if (y % 3 == 0)
                System.out.println(" - - - - - - - - - - - - ");
            for (int x = 0; x < NINE; x++) {
                if (x % 3 == 0)
                    System.out.print("| ");
                System.out.print(Integer.toString(sudokuData[y][x]).replace('0', '.') + " ");
            }
            System.out.print("| ");
            System.out.println();
        }
        System.out.println(" - - - - - - - - - - - - ");

        System.out.println("Legal: " + isSudokuLegal());
    }

    // getters & setters

    /* check if sudoku is legal */
    private boolean isSudokuLegal() {
        for (int y = 0; y < NINE; y++)
            for (int x = 0; x < NINE; x++)
                if (!isCellLegal(x, y))
                    return false;
        return true;
    }

    /* check if a cell number is legal */
    private boolean isCellLegal(int x, int y) {
        int currNum = sudokuData[y][x];

        if (currNum == 0)
            return true;

         /* (1.) row */
        for (int x2 = 0; x2 < NINE; x2++) {
            if (x2 == x) {
                continue;
            }

            int num = sudokuData[y][x2];
            if (num == currNum) {
                return false;
            }
        }
        /* (2.) column */
        for (int y2 = 0; y2 < NINE; y2++) {
            if (y2 == y) {
                continue;
            }

            int num = sudokuData[y2][x];
            if (num == currNum) {
                return false;
            }
        }
        /* (3.) 3x3 area */
        int areaX = divideByThree(x);
        int areaY = divideByThree(y);

        for (int yAdd = 0; yAdd < THREE; yAdd++) {
            for (int xAdd = 0; xAdd < THREE; xAdd++) {
                if ((areaX * THREE + xAdd) == x && (areaY * THREE + yAdd) == y) {
                    continue;
                }

                int num = sudokuData[areaY * THREE + yAdd][areaX * THREE + xAdd];
                if (num == currNum) {
                    return false;
                }
            }
        }

        return true;
    }

    int getCellsLeft() {
        int cellsLeft = 0;
        for (int y = 0; y < NINE; y++)
            for (int x = 0; x < NINE; x++)
                if (sudokuData[y][x] == 0)
                    cellsLeft++;
        return cellsLeft;
    }

    /* check if is solved / no cell contains a zero */
    boolean isSolved() {
        for (int y = 0; y < NINE; y++)
            for (int x = 0; x < NINE; x++)
                if (sudokuData[y][x] == 0)
                    return false;
        return true;
    }

    private int getPossibleNumbersSize() {
        int sum = 0;

        for (int y = 0; y < NINE; y++)
            for (int x = 0; x < NINE; x++)
                sum += possibleNumbers[y][x].size();

        return sum;
    }

    int[][] getSudokuData() {
        return sudokuData;
    }

    ArrayList<Integer>[][] getPossibleNumbers() {
        return possibleNumbers;
    }

}
