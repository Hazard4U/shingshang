package subjects;

import observers.EndGameObserver;

import java.util.LinkedList;

public interface EndGameSubject {
    public LinkedList<EndGameObserver> endGameObservers = new LinkedList<>();

    default public void addEndGameObservers(EndGameObserver observer){
        endGameObservers.add(observer);
    }

    default public void removeEndGameObservers(EndGameObserver observer){
        endGameObservers.remove(observer);
    }

    default public void notifyEndGameObservers(int teamWinnerID){
        for (EndGameObserver endGameObserver : endGameObservers){
            endGameObserver.endGameHandler(teamWinnerID);
        }
    }
}
