package model.pawns;

public class Wall extends Pawn{
    private static final int MAX_MOVE_RANGE = 0;
    private static final int height = 4;

    public Wall(int teamId)throws IllegalArgumentException {
        super(teamId, Wall.height, Wall.MAX_MOVE_RANGE);
    }
}
