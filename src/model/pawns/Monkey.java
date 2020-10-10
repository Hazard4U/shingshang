package model.pawns;

public class Monkey extends Pawn {
    private static final int MAX_MOVE_RANGE = 2;
    private static final int height = 1;

    public Monkey(int teamId)throws IllegalArgumentException {
        super(teamId, Monkey.height, Monkey.MAX_MOVE_RANGE);
    }
}
