package model.board;

import model.pawns.Pawn;

import java.util.Objects;

public class Square implements Cloneable{
    private Pawn pawn;
    private int x;
    private int y;

    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public boolean hasPawn(){
        return this.pawn != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Square{" +
                "pawn=" + pawn +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return x == square.x &&
                y == square.y &&
                Objects.equals(pawn, square.pawn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pawn, x, y);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Square newSquare = null;
        newSquare = (Square) super.clone();
        newSquare.pawn = (Pawn) pawn.clone();
        return super.clone();
    }
}
