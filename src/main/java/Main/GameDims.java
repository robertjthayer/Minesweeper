package Main;

public class GameDims{
    // TODO: It would be nice if these numbers could be input at runtime
    public static final int ROW_COUNT = 10;
    public static final int COL_COUNT = 10;
    public static final int MINES_COUNT = 20;
    public static final int SQUARE_COUNT = COL_COUNT * ROW_COUNT;

    private GameDims(){
        throw new RuntimeException("Not instantiable");
    }
}
