package Solver;

import Main.GameDims;
import Skeleton.PlayingField;

import java.util.Scanner;

public class MannySolver implements Solver {
    public int chooseSquare(final PlayingField board) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number: ");
        while (!sc.hasNextInt()) sc.next();
        return sc.nextInt();
    }

    public int startingSquare(final PlayingField board) {
        return (GameDims.ROW_COUNT/2)*GameDims.COL_COUNT + GameDims.COL_COUNT/2;
    }
}
