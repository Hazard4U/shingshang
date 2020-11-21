package model.pawns;

import model.board.Board;

public class Lion extends Pawn {
    private static final int MAX_MOVE_RANGE = 1;
    private static final int height = 2;

    public Lion(int teamId)throws IllegalArgumentException {
        super(teamId, Lion.height, Lion.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[37m";
        }else{
            color = "\u001B[30m";
        }
        return "\u001B[43m"+color+"| 2 |\u001B[0m";
    }
}
