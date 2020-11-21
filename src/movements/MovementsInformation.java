package movements;

import model.board.Square;

public class MovementsInformation {
    private Movements movements;
    private Square capturedPawnSquare;

    public MovementsInformation(Movements movements, Square capturedPawnSquare){
        this.movements = movements;
        this.capturedPawnSquare = capturedPawnSquare;
    }


    public Movements getMovements() {
        return movements;
    }

    public void setMovements(Movements movements) {
        this.movements = movements;
    }

    public Square getCapturedPawnSquare() {
        return capturedPawnSquare;
    }

    public void setCapturedPawnSquare(Square capturedPawnSquare) {
        this.capturedPawnSquare = capturedPawnSquare;
    }
}
