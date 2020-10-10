package model.pawns;

public class Dragon extends Pawn {
    private static final int MAX_MOVE_RANGE = 0;
    private static final int height = 3;

    public Dragon(int teamId)throws IllegalArgumentException {
        super(teamId, Dragon.height, Dragon.MAX_MOVE_RANGE);
    }
}
