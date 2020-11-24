package model.pawns;

import model.board.Board;

public class Lion extends Pawn {
    private static final int MAX_MOVE_RANGE = 1;
    private static final int HEIGHT = 2;

    public Lion(Pawn pawn){
        super(pawn);
    }

    public Lion(int teamId)throws IllegalArgumentException {
        super(teamId, Lion.HEIGHT, Lion.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[41m";
        }else{
            color = "\u001B[44m";
        }
        return "\u001B[30m"+color+" L \u001B[0m";
    }
}
