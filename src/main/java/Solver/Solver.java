package Solver;

import Skeleton.PlayingField;
import Skeleton.Square;

/**
 * Created by RT on 5/26/2018.
 */
public interface Solver {
    int chooseSquare(final Square[] board);
    int startingSquare(final Square[] board);
}
