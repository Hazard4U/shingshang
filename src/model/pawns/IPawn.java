package model.pawns;

import model.board.Square;

import java.util.LinkedList;

public interface IPawn {

    int getTeamId();

    int getHeight();

    int getMovementRange();

    boolean canJump(Square fromSquare, Square betweenSquare, Square toSquare);

    boolean canGo(LinkedList<Square> squares);

    boolean hasMove();

}
