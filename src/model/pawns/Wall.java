package model.pawns;

import model.board.Board;

public class Wall extends Pawn{
    private static final int MAX_MOVE_RANGE = 0;
    private static final int HEIGHT = 4;

    public Wall(int teamId)throws IllegalArgumentException {
        super(teamId, Wall.HEIGHT, Wall.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[37m";
        }else{
            color = "\u001B[30m";
        }
        return "\u001B[30m"+color+"| 4 |\u001B[0m";
    }
}
