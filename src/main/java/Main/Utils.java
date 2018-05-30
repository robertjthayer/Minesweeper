package Main;

import Skeleton.PlayingField;

public class Utils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SECOND_TO_LAST_COLUMN = initColumn(GameDims.COL_COUNT - 2);
    public static final boolean[] LAST_COLUMN = initColumn(GameDims.COL_COUNT - 1);

    private Utils(){
        throw new RuntimeException("Can't instantiate Utils");
    }

    // Converts row, column inputs into corresponding square ID number
    public static int coordToSquareID (int row, int col){
        return (row * GameDims.COL_COUNT + col);
    }

    // Creates the array which will reveal whether a given square is in a given column
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] isSquareInColumn = new boolean[GameDims.SQUARE_COUNT];
        if (columnNumber >= 0 && columnNumber < GameDims.COL_COUNT) {
            do {
                isSquareInColumn[columnNumber] = true;
                columnNumber += GameDims.COL_COUNT;
            } while (columnNumber < GameDims.SQUARE_COUNT);
        }
        return isSquareInColumn;
    }
}
