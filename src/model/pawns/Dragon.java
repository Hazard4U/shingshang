package model.pawns;

import model.board.Board;

public class Dragon extends Pawn {
    private static final int MAX_MOVE_RANGE = 0;
    private static final int HEIGHT = 3;

    public Dragon(int teamId)throws IllegalArgumentException {
        super(teamId, Dragon.HEIGHT, Dragon.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[37m";
        }else{
            color = "\u001B[30m";
        }
        return "\u001B[41m"+color+"| 3 |\u001B[0m";
    }
}
