package Main;

import Skeleton.PlayingField;
import Skeleton.Square;
import Solver.Solver;

import static Skeleton.Square.*;

public class Game {
    private int gameClock;
    private final long startTime;
    private PlayingField playingField;
    private Solver solver;

    Game(Solver solver){
        this.solver = solver;
        this.gameClock = 0;

        // Confirm legal board dimensions
        if (GameDims.SQUARE_COUNT > GameDims.MINES_COUNT && GameDims.MINES_COUNT > 0 &&
                GameDims.ROW_COUNT > 0 && GameDims.COL_COUNT > 1)
            playingField = playingField.initializeNewGame();
        else {
            // TODO: Request new dimensions without terminating the program
            throw new RuntimeException("Illegal board conditions");
        }
        this.startTime = System.nanoTime();
    }

    public void playGame(){
        boolean isGameOver = false;
        final int firstMoveSquareID = this.solver.startingSquare(playingField);
        int movesMade = 0;

        // First move made separately, mines aren't initialized until after first move is made
        playingField = playingField.makeFirstMove(firstMoveSquareID);
        // In most games all squares around first move are not mine squares
        movesMade += 1 + revealSquaresAroundZeros(playingField.getSquare(firstMoveSquareID), playingField);

        // Helpful for debugging
        printSolution();

        while (!isGameOver){
            printCurrentState();
            final int solversChosenSquare = solver.chooseSquare(playingField);
            if (playingField.getSquareStatus(solversChosenSquare) == Status.UNKNOWN) {
                playingField = playingField.makeMove(solversChosenSquare);
                if (playingField.getSquareStatus(solversChosenSquare) == Status.MINE) {
                    System.out.println("LOSE");
                    break;
                }
                movesMade += 1 + revealSquaresAroundZeros(playingField.getSquare(solversChosenSquare), playingField);

                if (movesMade == (GameDims.SQUARE_COUNT - GameDims.MINES_COUNT)) {
                    long completionTime = (System.nanoTime() - this.startTime) / 1000000000;
                    System.out.println("Game Completed in " + completionTime + " seconds");
                    isGameOver = true;
                }
            }
        }
    }

    public int revealSquaresAroundZeros(Square square, PlayingField playingField){
        if (square.getNearbyMineCount() == 0) {
            if(square instanceof SafeSquare){
                int numSafeSquares = 0;
                for (int neighbor : square.getLegalNearbySquares()) {
                    if (playingField.getSquare(neighbor) instanceof UnknownSquare) {
                        playingField = playingField.makeMove(neighbor);
                        numSafeSquares += 1 + revealSquaresAroundZeros(playingField.getSquare(neighbor), playingField);
                    }
                }
                return numSafeSquares;
            }
        }
        return 0;
    }

    public void printCurrentState(){
        for (int i = 0; i <= GameDims.COL_COUNT*2 + 1; i++){
            System.out.print("_");
        }
        System.out.println("_");
        for(int i = 0; i < GameDims.ROW_COUNT; i++){
            System.out.print("| ");
            for (int j = 0; j < GameDims.COL_COUNT; j++){
                int squareID = Utils.coordToSquareID(i, j);
                char c = playingField.getSquare(squareID).printSquare();
                System.out.print(c + " ");
            }
            System.out.println("|");
        }
        for (int i = 0; i <= (GameDims.COL_COUNT*2)+1; i++){
            System.out.print("_");
        }
        System.out.println();
    }

    public void printSolution() {
        playingField.printSolution();
    }
}
