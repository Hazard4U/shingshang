package subjects;

import observers.PlayerScoreObserver;

public interface PlayerScoreSubject {

    public void notifyPlayerScoreObservers(int score);

    public void addPlayerScoreObservers(PlayerScoreObserver observer);

    public void removePlayerScoreObservers(PlayerScoreObserver observer);
}
