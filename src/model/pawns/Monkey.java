package model.pawns;

import model.board.Board;

public class Monkey extends Pawn {
    private static final int MAX_MOVE_RANGE = 2;
    private static final int HEIGHT = 1;

    public Monkey(Pawn pawn){
        super(pawn);
    }

    public Monkey(int teamId)throws IllegalArgumentException {
        super(teamId, Monkey.HEIGHT, Monkey.MAX_MOVE_RANGE);
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID) {
            color = "\u001B[41m";
        } else {
            color = "\u001B[44m";
        }
        return "\u001B[30m" + color + " M \u001B[0m";
    }
}
