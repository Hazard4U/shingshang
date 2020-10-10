import model.Board;
import model.Player;

public class Main {
    public static void main(String[] args) {
        try{
            Player firstPlayer = new Player(0);
            Player secondPlayer = new Player(1);
            Board board = new Board(new Player[]{firstPlayer,secondPlayer});
            System.out.println(board.toString());
            System.out.println(firstPlayer.movePawn(new int[]{0,8},new int[]{2,6}));
            System.out.println(board.toString());
            System.out.println(secondPlayer.movePawn(new int[]{8,7},new int[]{6,7}));
            System.out.println(board.toString());
            System.out.println(firstPlayer.movePawn(new int[]{1,7},new int[]{3,7}));
            System.out.println(board.toString());
            System.out.println(secondPlayer.movePawn(new int[]{6,7},new int[]{5,7}));
            System.out.println(board.toString());
            System.out.println(firstPlayer.movePawn(new int[]{2,6},new int[]{6,6}));
            System.out.println(board.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
