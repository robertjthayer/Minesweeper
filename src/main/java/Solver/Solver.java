package Solver;

import Skeleton.PlayingField;

/**
 * Created by RT on 5/26/2018.
 */
public interface Solver {
    int chooseSquare(final PlayingField board);
    int startingSquare(final PlayingField board);
}
