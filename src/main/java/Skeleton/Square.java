package Skeleton;

import Main.GameDims;
import Main.Utils;

import java.util.LinkedList;
import java.util.List;

abstract public class Square {
    private final int squareID;
    private final List<Integer> legalNearbySquares;
    private final static int COLS = GameDims.COL_COUNT;
    private final static int[] CANDIDATE_NEARBY_OFFSET = {-(COLS + 1), -COLS, -(COLS - 1), -1, 1, COLS - 1, COLS, COLS + 1};
    public abstract char printSquare();
    public abstract Status getStatus();
    public abstract int getNearbyMineCount();

    public Square(final int squareID) {
        this.squareID = squareID;
        this.legalNearbySquares = computeLegalNearbySquares();
    }

    private List<Integer> computeLegalNearbySquares() {
        List<Integer> legalSquares = new LinkedList<Integer>();
        for (final int currentOffset : CANDIDATE_NEARBY_OFFSET) {
            int candidateOffsetCoordinate = this.squareID + currentOffset;

            if (candidateOffsetCoordinate < 0 || candidateOffsetCoordinate >= GameDims.SQUARE_COUNT ||
                    isFirstColumnExclusion(this.squareID, currentOffset) ||
                    isLastColumnExclusion(this.squareID, currentOffset)) {
                continue;
            }
            legalSquares.add(candidateOffsetCoordinate);
        }
        return legalSquares;
    }

    private boolean isFirstColumnExclusion(int squareID, int offset) {
        return Utils.FIRST_COLUMN[squareID] && (offset == -(COLS + 1) || offset == -1 || offset == COLS - 1);
    }

    private boolean isLastColumnExclusion(int squareID, int offset) {
        return Utils.LAST_COLUMN[squareID] && (offset == -(COLS - 1) || offset == 1 || offset == COLS + 1);
    }

    public List<Integer> getLegalNearbySquares() {
        return legalNearbySquares;
    }

    public enum Status {
        UNKNOWN,
        SAFE,
        MINE
    }

    public static class UnknownSquare extends Square{
         public UnknownSquare(final int squareID){
             super(squareID);
        }

        @Override
        public char printSquare(){
            return '-';
        }

        @Override
        public Status getStatus() {
            return Status.UNKNOWN;
        }

        @Override  // TODO: Get rid of this method if possible, it shouldn't exist
        public int getNearbyMineCount() {
            return -1;
        }
    }

    public static class MineSquare extends Square{
        public MineSquare(final int squareID){
            super(squareID);
        }

        @Override
        public char printSquare(){
            return '@';
        }

        @Override
        public Status getStatus() {
            return Status.MINE;
        }

        @Override // TODO: Get rid of this method if possible, it shouldn't exist
        public int getNearbyMineCount() {
            return -1;
        }
    }

    public static class SafeSquare extends Square{
        private final int nearbyMineCount;
        public SafeSquare(final int squareID, int nearbyMines){
            super(squareID);
            this.nearbyMineCount = nearbyMines;
        }

        @Override
        public int getNearbyMineCount(){
            return nearbyMineCount;
        }

        @Override
        public char printSquare(){
            if (this.nearbyMineCount == 0) {
                return ' ';
            }
            return Character.forDigit(this.nearbyMineCount, 10);
        }

        @Override
        public Status getStatus() {
            return Status.SAFE;
        }
    }



}


