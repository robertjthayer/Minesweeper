package Skeleton;

import Main.GameDims;
import java.util.*;

public class PlayingField {
    private final boolean[] mineLocations;
    private final Square[] gameBoard;
    private final int[] nearbyMineIndex;

    private PlayingField(Square[] gameBoard, boolean[] mineLocations, int[] nearbyMineIndex){
        this.gameBoard = gameBoard;
        this.mineLocations = mineLocations;
        this.nearbyMineIndex = nearbyMineIndex;
    }

    public static PlayingField initializeNewGame(){
        System.out.println("Initializing new game");

        final PlayingField playingField = new PlayingField(new Square[GameDims.SQUARE_COUNT], null, new int[GameDims.SQUARE_COUNT]);
        for (int i = 0; i < GameDims.SQUARE_COUNT; i++){
            playingField.setSquare(i, Square.Status.UNKNOWN);
        }
        System.out.println("Mine locations not initialized until after first move");
        return playingField;
    }

    public PlayingField makeMove(int destinationSquareID){
        final PlayingField playingField = new PlayingField(gameBoard, mineLocations, nearbyMineIndex);
        // No changes made unless the move is on an unknown square
        if(gameBoard[destinationSquareID].getStatus() == Square.Status.UNKNOWN){
            // If move is uncovering a mine a new mine square is made, if it's not a mine then the new square is safe
            playingField.setSquare(destinationSquareID,
                    mineLocations[destinationSquareID] ? Square.Status.MINE : Square.Status.SAFE);
        }
        return playingField;
    }

    public Square getSquare(final int squareID){
        return gameBoard[squareID];
    }

    public Square.Status getSquareStatus(final int squareID){
        return gameBoard[squareID].getStatus();
    }

    // Randomizes bomb locations without putting any bombs around the first move square
    public PlayingField makeFirstMove(int firstMove) {
        boolean[] newMines = new boolean[GameDims.SQUARE_COUNT];
        int[] newNearbyMines = new int[GameDims.SQUARE_COUNT];

        // Set up the mines. The first square chosen is not a possible mine location
        // TODO: Change to a Set, there should be no repeating values. Should improve removing
        ArrayList<Integer> possibleBombLocations = new ArrayList<>();
        for (int i=0; i < GameDims.SQUARE_COUNT; i++) {
            possibleBombLocations.add(new Integer(i));
        }
        possibleBombLocations.remove(firstMove);
        if(GameDims.SQUARE_COUNT >= GameDims.MINES_COUNT + 9)
        {
            for (int neighbor : gameBoard[firstMove].getLegalNearbySquares()){
                possibleBombLocations.removeIf(s -> s.equals(neighbor));
            }
        }
        else{
            // TODO: Address the case where there are so many mines that at least one surrounding square must be a mine
            throw new RuntimeException("Fringe case, not accounted for yet");
        }

        Collections.shuffle(possibleBombLocations);

        // Set mines & nearby mine counts
        for (int i=0; i < GameDims.MINES_COUNT; i++) {
            newMines[possibleBombLocations.get(i)] = true;
            for (int neighbor : gameBoard[possibleBombLocations.get(i)].getLegalNearbySquares()){
                newNearbyMines[neighbor]++;
            }
        }

        final PlayingField playingField = new PlayingField(this.gameBoard, newMines, newNearbyMines);
        if(gameBoard[firstMove].getStatus() == Square.Status.UNKNOWN) {
            if (!newMines[firstMove]) {
                playingField.setSquare(firstMove, Square.Status.SAFE);
            } else {
                throw new RuntimeException("ERROR: First move was a mine");
            }
        }
        return playingField;
    }

    private void setSquare(final int squareID, final Square.Status status)
    {
        switch (status){
            case SAFE : gameBoard[squareID] = new Square.SafeSquare(squareID, nearbyMineIndex[squareID]);
                break;
            case MINE : gameBoard[squareID] = new Square.MineSquare(squareID);
                break;
            //TODO : Try making default an error and adding case UNKNOWN
            default: gameBoard[squareID] = new Square.UnknownSquare(squareID);
        }
    }

    public void printSolution(){
        System.out.println("SOLUTION:");
        int gridTracker = 0;
        for (int i = 0; i <= GameDims.COL_COUNT*2 + 1; i++){
            System.out.print("_");
        }
        System.out.println("_");
        for(int i = 0; i < GameDims.ROW_COUNT; i++){
            System.out.print("| ");
            for (int j = 0; j < GameDims.COL_COUNT; j++){
                char c = mineLocations[gridTracker] ? '@' : '-';
                System.out.print(c + " ");
                gridTracker++;
            }
            System.out.println("|");
        }
        for (int i = 0; i <= (GameDims.COL_COUNT*2)+1; i++){
            System.out.print("_");
        }
        System.out.println();
    }
}
