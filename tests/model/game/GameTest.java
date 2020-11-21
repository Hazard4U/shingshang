package model.game;

import exceptions.*;
import model.board.Board;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;
    @BeforeEach
    void setUp() {
        this.game = new Game();
    }

    @Test
    void firstPlayerMove() {
        this.game.start();
        try{
            Board pastBoard = this.game.getBoard();
            this.game.firstPlayerMove(false, new int[]{8,0}, new int[]{6,2});
            Board board = this.game.getBoard();
            assertEquals(null, board.getSquare(8,0).getPawn());
            assertEquals(true, board.getSquare(6,2).getPawn().equals(pastBoard.getSquare(8,0).getPawn()));
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void secondPlayerMoveBeforeFirst() {
        this.game.start();
        assertThrows(WrongRoundException.class, () ->{
            this.game.secondPlayerMove(false, new int[]{8,9}, new int[]{6,7});
        });
    }

    @Test
    void secondPlayerMove() throws Exception{
        this.game.start();
        this.game.firstPlayerMove(false, new int[]{8,0}, new int[]{6,2});
        this.game.firstPlayerMove(true, null,null);
        Board pastBoard = this.game.getBoard();
        this.game.secondPlayerMove(false, new int[]{8,9}, new int[]{6,7});
        Board board = this.game.getBoard();
        assertEquals(null, board.getSquare(8,9).getPawn());
        assertEquals(true, board.getSquare(6,7).getPawn().equals(pastBoard.getSquare(8,9).getPawn()));
    }

    @Test
    void secondPlayerMoveDuringFirstPlayerRound() throws Exception{
        this.game.start();
        this.game.firstPlayerMove(false, new int[]{8,0}, new int[]{6,2});
        assertThrows(WrongRoundException.class, () -> {
            this.game.secondPlayerMove(false, new int[]{8,9}, new int[]{6,7});
        });
    }
}