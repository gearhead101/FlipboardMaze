package com.company;

import com.company.adapter.Maze;
import com.company.helper.MazeVerifier;

public class Main {

    public static void main(String[] args) {

        // Setup maze
        Maze newMaze = new Maze();
        String mazeId = newMaze.getId();

        // Solve maze
        String solution = newMaze.solve();
        System.out.println("Solution found:" + solution);

        // Verify solution
        MazeVerifier verifier = new MazeVerifier();
        if(verifier.verifyGuess(mazeId, solution)) {
            System.out.println("Solution verified for maze id:" + mazeId);
        } else {
            System.out.println("Invalid solution for maze id:" + mazeId);
        }

    }
}
