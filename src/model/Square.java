package model;

import model.pawns.Pawn;

import java.util.Objects;

public class Square {
    private IPawn pawn;
    private int x;
    private int y;

    public Square(IPawn pawn, int x, int y) {
        this.pawn = pawn;
        this.x = x;
        this.y = y;
    }

    public Square(int x, int y){
        this(null, x,y);
    }

    public IPawn getPawn() {
        return pawn;
    }

    public void setPawn(IPawn pawn) {
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
                y == square.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
