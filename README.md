# Sudoku-Solver-Generator

## Description

A simple application that allows the user to create randomly generated Sudoku puzzles and solve pre-existing ones.

## Solving Algorithm

This program utilizes a *recursive backtracking algorithm* to exhaust every possible configuration of the board until a suitable solution is found. 

If no solution is found, the GUI will update to inform the user.

## Generation Algorithm

This program generates puzzles using the same recursive backtracking algorithm as mentioned previously.

First the algorithm generates a pre-completed Sudoku board, then it removes individual squares from the board, re-solving the board at each step. 
To ensure that the puzzle only has one unique solution, the algorithm terminates once it the solving algorithm produces a solution that doesn't match the original board.

## Example Images

An example of a user-uploaded puzzle:

![alt text](https://github.com/anthonyh-1199/Sudoku-Solver-Generator/blob/master/example%20images/sudoku1.PNG?raw=true)

And the resulting solution:

![alt text](https://github.com/anthonyh-1199/Sudoku-Solver-Generator/blob/master/example%20images/sudoku2.PNG?raw=true)
