package Solver;

import Main.GameDims;
import Skeleton.PlayingField;
import Skeleton.Square;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RT on 6/2/2018.
 */
public class RTSolver implements Solver {
    private boolean[] isKnownMineSquare;
    private Map<Integer, Integer> possibleNearbyBombCounter;

    public RTSolver(){
        isKnownMineSquare = new boolean[GameDims.SQUARE_COUNT];
        possibleNearbyBombCounter = new HashMap<>();
    }

    @Override
    public int chooseSquare(final Square[] board) {
        return 0;
    }

    @Override
    public int startingSquare(final Square[] board) {
        return (GameDims.ROW_COUNT/2)*GameDims.COL_COUNT + GameDims.COL_COUNT/2;
    }
}
