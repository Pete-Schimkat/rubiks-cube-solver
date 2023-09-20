# Rubiks Cube Solver

This Rubiks Cube solver was implemented by me as part of my bachelor's thesis. 

## Features
* Computation of a shortest solution for any given solvable Rubiks Cube configuration using IDA*
* Finding sub-optimal solutions using weighted IDA*
* Severely increased performance compared to the original paper published by Korf
* Custom efficient data-structure to represent the cube  
* Pattern databases to serve as heuristic for the search algorithms

## References
Korf, Richard E. (1997) [Finding Optimal Solutions to Rubik's Cube Using Pattern Databases.](https://www.cs.princeton.edu/courses/archive/fall06/cos402/papers/korfrubik.pdf)

Korf, Richard E. et. al. (2005) [Large-Scale Parallel Breadth-First Search.](https://www.aaai.org/Papers/AAAI/2005/AAAI05-219.pdf)

Botto, Ben (2020) [Implementing an Optimal Rubik’s Cube Solver using Korf’s Algorithm](https://medium.com/@benjamin.botto/implementing-an-optimal-rubiks-cube-solver-using-korf-s-algorithm-bf750b332cf9)

Botto, Ben (2019) [Sequentially Indexing Permutations: A Linear Algorithm for Computing Lexicographic Rank](https://medium.com/@benjamin.botto/sequentially-indexing-permutations-a-linear-algorithm-for-computing-lexicographic-rank-a22220ffd6e3)
