package observers;

import model.board.Board;

public interface PlayerRoundObserver {
    public void nextRoundHandler(Board board);
}
