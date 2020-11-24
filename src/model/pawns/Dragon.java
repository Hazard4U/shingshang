package model.pawns;

import model.board.Board;

public class Dragon extends Pawn {
    private static final int MAX_MOVE_RANGE = 0;
    private static final int HEIGHT = 3;

    public Dragon(Pawn pawn){
        super(pawn);
    }

    public Dragon(int teamId)throws IllegalArgumentException {
        super(teamId, Dragon.HEIGHT, Dragon.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[41m";
        }else{
            color = "\u001B[44m";
        }
        return "\u001B[30m"+color+" D \u001B[0m";
    }
}
