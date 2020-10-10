package libs;

import model.Square;

import java.util.HashMap;
import java.util.LinkedList;

public class MovementsInformation {
    private HashMap<Square, Tuple<Square, LinkedList<Square>>> movementsHashMap;

    public MovementsInformation(HashMap<Square, Tuple<Square, LinkedList<Square>>> movementsHashMap) {
        this.movementsHashMap = movementsHashMap;
    }

    public HashMap<Square, Tuple<Square, LinkedList<Square>>> getMovementsHashMap(){
        return this.movementsHashMap;
    }

    public Tuple<Square, LinkedList<Square>> getMovementsInformation(Square destination){
        return this.movementsHashMap.get(destination);
    }

    public void setMovementsInformation(Square destination, Square squareOfCapturedPawn, LinkedList<Square> historic){
        this.movementsHashMap.put(destination, new Tuple<>(squareOfCapturedPawn, historic));
    }

    public void setAllMovementInformation(MovementsInformation movementsInformation){
        movementsHashMap.putAll(movementsInformation.getMovementsHashMap());
    }

    public LinkedList<Square> getHistoric(Square destination){
        return this.movementsHashMap.get(destination).getSecondElement();
    }

    public Square getCapturedPawn(Square destination){
        return this.movementsHashMap.get(destination).getFirstElement();
    }
}
