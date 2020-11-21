package model.pawns;

import model.board.Board;
import model.board.Square;

import java.util.LinkedList;
import java.util.Objects;

public abstract class Pawn implements IPawn, Cloneable{
    protected int teamId;
    private int height;
    private int range;
    private boolean hasMove;

    public Pawn(int teamId, int height, int range) throws IllegalArgumentException{
        if (!Board.isValidTeamId(teamId)){
            throw new IllegalArgumentException("Identifiant d'équipe non valide");
        }
        this.teamId = teamId;
        this.height = height;
        this.range = range;
    }

    public int getTeamId() { return teamId; }

    public int getHeight(){
        return this.height;
    }

    public int getMovementRange() { return this.range; }

    public boolean canJump(Square fromSquare, Square betweenSquare, Square toSquare){
        int range = (int) Math.sqrt(Math.pow(fromSquare.getX()-toSquare.getX(),2)+Math.pow(fromSquare.getY()-toSquare.getY(),2));
        int betweenSquareX = (fromSquare.getX()+toSquare.getX())/2;
        int betweenSquareY = (fromSquare.getY()+toSquare.getY())/2;
        return (
                range == 2
                && betweenSquare.getX() == betweenSquareX
                && betweenSquare.getY() == betweenSquareY
                && betweenSquare.hasPawn()
                && this.getHeight() >= betweenSquare.getPawn().getHeight()
                && !toSquare.hasPawn()
                && directionNotChanged(fromSquare, toSquare));
    }

    public boolean canGo(LinkedList<Square> squares){
        Square fromSquare = squares.getFirst();
        Square toSquare = squares.getLast();
        boolean noPawnsOnRoad = true;
        squares.removeFirst();
        for (Square square : squares){
            noPawnsOnRoad = !square.hasPawn() && noPawnsOnRoad;
        }
        int range = (int) Math.sqrt(Math.pow(fromSquare.getX()-toSquare.getX(),2)+Math.pow(fromSquare.getY()-toSquare.getY(),2));
        return (range > 0
                && range <= this.getMovementRange()
                && directionNotChanged(fromSquare, toSquare)
                && noPawnsOnRoad);
    }

    private boolean directionNotChanged(Square fromSquare, Square toSquare){
        return fromSquare.getX() == toSquare.getX()
                || fromSquare.getY() == toSquare.getY()
                || Math.abs(fromSquare.getX()-toSquare.getX()) == Math.abs(fromSquare.getY()-toSquare.getY());
    }

    public void setHasMove(boolean hasMove) {
        this.hasMove = hasMove;
    }

    @Override
    public boolean hasMove() {
        return hasMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pawn pawn = (Pawn) o;
        return teamId == pawn.teamId &&
                height == pawn.height &&
                range == pawn.range &&
                hasMove == pawn.hasMove;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamId, height, range, hasMove);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
