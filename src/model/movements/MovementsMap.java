package model.movements;

import model.board.Square;

import java.util.HashMap;

public class MovementsMap {

    /**
     * HashMap qui a pour clef une case de destination
     * et pour valeur MovementsInformation
     */
    private HashMap<Square, MovementsInformation> movementsHashMap;

    public MovementsMap(HashMap<Square, MovementsInformation> movementsHashMap) {
        this.movementsHashMap = movementsHashMap;
    }

    public HashMap<Square, MovementsInformation> getHashMap(){
        return this.movementsHashMap;
    }

    public MovementsInformation getMovementsInformation(Square destination){
        return this.movementsHashMap.get(destination);
    }

    public void setMovementsInformation(Square destination, MovementsInformation movementsInformation){
        this.movementsHashMap.put(destination, movementsInformation);
    }

    public void setAllMovementInformation(MovementsMap movementsMap){
        movementsHashMap.putAll(movementsMap.getHashMap());
    }
}
