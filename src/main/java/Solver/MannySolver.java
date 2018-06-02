package Solver;

import Main.GameDims;
import Skeleton.PlayingField;
import Skeleton.Square;

import java.util.Scanner;

public class MannySolver implements Solver {
    @Override
    public int chooseSquare(Square[] board) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number: ");
        while (!sc.hasNextInt()) sc.next();
        return sc.nextInt();
    }

    @Override
    public int startingSquare(Square[] board) {
        return (GameDims.ROW_COUNT/2)*GameDims.COL_COUNT + GameDims.COL_COUNT/2;
    }
}
