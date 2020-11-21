package subjects;

import model.board.Board;
import observers.PlayerRoundObserver;

import java.util.LinkedList;

/**
 * Notifie lors les observeurs d'un joueur lorsque c'est à son tour de jouer
 */
public interface PlayerRoundSubject {
    public LinkedList<PlayerRoundObserver> secondPlayerRoundObservers = new LinkedList<>();
    public LinkedList<PlayerRoundObserver> firstPlayerRoundObservers = new LinkedList<>();

    default public void addFirstPlayerRoundObservers(PlayerRoundObserver observer){
        firstPlayerRoundObservers.add(observer);
    }

    default public void removeFirstPlayerRoundObservers(PlayerRoundObserver observer){
        firstPlayerRoundObservers.remove(observer);
    }

    default public void notifyFirstPlayerRoundObservers(Board board){
        for (PlayerRoundObserver firstPlayerRoundObserver : firstPlayerRoundObservers){
            firstPlayerRoundObserver.nextRoundHandler(board);
        }
    }

    default public void addSecondPlayerRoundObservers(PlayerRoundObserver observer){
        secondPlayerRoundObservers.add(observer);
    }

    default public void removeSecondPlayerRoundObservers(PlayerRoundObserver observer){
        secondPlayerRoundObservers.remove(observer);
    }

    default public void notifySecondPlayerRoundObservers(Board board){
        for (PlayerRoundObserver secondPlayerRoundObserver : secondPlayerRoundObservers){
            secondPlayerRoundObserver.nextRoundHandler(board);
        }
    }
}
