package model.pawns;

public class Wall extends Pawn{
    private static final int MAX_MOVE_RANGE = 0;
    private static final int HEIGHT = 4;

    public Wall(Pawn pawn){
        super(pawn);
    }

    public Wall(int teamId)throws IllegalArgumentException {
        super(teamId, Wall.HEIGHT, Wall.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        return "|||";
    }
}
