package model.pawns;

public class Lion extends Pawn {
    private static final int MAX_MOVE_RANGE = 1;
    private static final int height = 2;

    public Lion(int teamId)throws IllegalArgumentException {
        super(teamId, Lion.height, Lion.MAX_MOVE_RANGE);
    }
}
