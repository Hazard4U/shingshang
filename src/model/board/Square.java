package model.board;

import model.pawns.Pawn;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class Square{
    private Pawn pawn;
    private int x;
    private int y;

    public Square(Square square){
        this.x = square.x;
        this.y = square.y;
        try {
            if (null == square.pawn){
                this.pawn = null;
            }else{
                Constructor<?> constructor = square.pawn.getClass().getConstructor(Pawn.class);
                this.pawn = (Pawn) constructor.newInstance(square.pawn);
            }
        }catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e){
            throw new IllegalArgumentException();
        }
    }

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
}
