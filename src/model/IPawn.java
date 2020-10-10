package model;

public interface IPawn {

    int getTeamId();

    int getHeight();

    int getMovementRange();

    boolean canJump(Square fromSquare, Square betweenSquare, Square toSquare);

    boolean canGo(Square fromSquare, Square toSquare);
}
