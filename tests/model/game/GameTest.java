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
    void moveEnemyPawn(){
        this.game.start();
        assertThrows(MoveEnemyPawnException.class, () -> {
           this.game.firstPlayerMove(false, new int[]{7,8}, new int[]{7,6});
        });
    }

    @Test
    void movePawnOnEmptySquare(){
        this.game.start();
        assertThrows(NoPawnException.class, () -> {
            this.game.firstPlayerMove(false, new int[]{7,2}, new int[]{7,3});
        });
    }

    @Test
    void skipBeforeMovePawn(){
        this.game.start();
        assertThrows(PlayerNotPlayingException.class, () -> {
            this.game.firstPlayerMove(true, null, null);
        });
    }

    @Test
    void skipAfterEatAndBeforeMoveSecondPawn() throws Exception{
        this.game.start();
        this.game.firstPlayerMove(false, new int[]{8,2}, new int[]{8,4});
        this.game.secondPlayerMove(false, new int[]{8,7}, new int[]{8,5});
        this.game.firstPlayerMove(false, new int[]{8,4}, new int[]{8,6});
        assertThrows(PlayerNotPlayingException.class, () -> {
            this.game.firstPlayerMove(true, null, null);
        });
    }

    @Test
    void secondPlayerMoveBeforeFirst() {
        this.game.start();
        assertThrows(WrongRoundException.class, () ->{
            this.game.secondPlayerMove(false, new int[]{8,9}, new int[]{6,7});
        });
    }

    @Test
    void secondPlayerMoveDuringFirstPlayerRound() throws Exception{
        this.game.start();
        this.game.firstPlayerMove(false, new int[]{8,0}, new int[]{6,2});
        assertThrows(WrongRoundException.class, () -> {
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
    void winOnPortal() throws Exception{
        this.game.start();
        this.game.firstPlayerMove(false, new int[]{8,0}, new int[]{6,2});
        this.game.firstPlayerMove(true, null,null);
        this.game.secondPlayerMove(false, new int[]{7,8}, new int[]{7,6});
        this.game.firstPlayerMove(false, new int[]{7,1}, new int[]{7,3});
        this.game.secondPlayerMove(false, new int[]{7,6}, new int[]{7,5});
        this.game.firstPlayerMove(false, new int[]{6,2}, new int[]{8,4});
        this.game.firstPlayerMove(false, new int[]{8,4}, new int[]{6,6});
        this.game.firstPlayerMove(false, new int[]{7,3}, new int[]{7,4});
        this.game.secondPlayerMove(false, new int[]{3,9}, new int[]{5,7});
        this.game.firstPlayerMove(false, new int[]{6,6}, new int[]{4,8});
        assertThrows(GameOverException.class, ()->{
           this.game.firstPlayerMove(true, new int[]{0,1}, new int[]{3,2});
        });
        assertEquals(Board.FIRST_TEAM_ID, this.game.winner());
    }

    @Test
    void winOnAttackEnemyDragon() throws Exception{
        this.game.start();
        this.game.firstPlayerMove(false, new int[]{8,0}, new int[]{6,2});
        this.game.firstPlayerMove(true, null,null);
        this.game.secondPlayerMove(false, new int[]{8,9}, new int[]{6,7});
        this.game.secondPlayerMove(true, null,null);
        this.game.firstPlayerMove(false, new int[]{7,1}, new int[]{7,3});
        this.game.secondPlayerMove(false, new int[]{1,9}, new int[]{3,7});
        this.game.secondPlayerMove(true, null,null);
        this.game.firstPlayerMove(false, new int[]{8,2}, new int[]{8,4});
        this.game.secondPlayerMove(false, new int[]{3,9}, new int[]{5,7});
        this.game.firstPlayerMove(false, new int[]{8,4}, new int[]{7,5});
        this.game.secondPlayerMove(false, new int[]{2,8}, new int[]{2,7});
        this.game.firstPlayerMove(false, new int[]{6,2}, new int[]{8,4});
        this.game.firstPlayerMove(false, new int[]{8,4}, new int[]{6,6});
        this.game.firstPlayerMove(false, new int[]{6,6}, new int[]{6,8});
        this.game.firstPlayerMove(false, new int[]{7,5}, new int[]{7,6});
        this.game.secondPlayerMove(false, new int[]{2,9}, new int[]{3,9});
        this.game.firstPlayerMove(false, new int[]{6,8}, new int[]{4,6});
        this.game.firstPlayerMove(false, new int[]{7,6}, new int[]{7,7});
        this.game.secondPlayerMove(false, new int[]{3,9}, new int[]{4,9});
        this.game.firstPlayerMove(false, new int[]{4,6}, new int[]{2,8});
        assertThrows(GameOverException.class, ()->{
            this.game.firstPlayerMove(true, new int[]{0,1}, new int[]{3,2});
        });
        assertEquals(Board.FIRST_TEAM_ID, this.game.winner());
    }
}