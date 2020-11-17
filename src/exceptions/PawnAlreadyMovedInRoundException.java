package exceptions;

public class PawnAlreadyMovedInRoundException extends Exception{
    public PawnAlreadyMovedInRoundException(String msg){
        super(msg);
    }
}
