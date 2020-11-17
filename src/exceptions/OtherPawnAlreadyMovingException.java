package exceptions;

public class OtherPawnAlreadyMovingException extends Exception{
    public OtherPawnAlreadyMovingException(String msg){
        super(msg);
    }
}
