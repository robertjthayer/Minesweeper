package Main;

import Solver.MannySolver;
/*
        Set game dimensions in GameDims Class
        Choose which solver to use in main class below
 */
public class JMinesweeper {
    public static void main(String args[]){
        System.out.println("Run sequence initialized");

        // Choose which solver is being used for the new game here
        Game game = new Game(new MannySolver());

        game.playGame();
        game.printSolution();
        System.out.println("Run sequence complete");
    }
}

