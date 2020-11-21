package model.player;

import model.board.Board;
import observers.PlayerScoreObserver;
import subjects.PlayerScoreSubject;

import java.util.LinkedList;

public class Player implements PlayerScoreSubject {
    private int teamId;
    private int score = 2;
    LinkedList<PlayerScoreObserver> playerScoreObservers = new LinkedList<>();

    public Player(int teamId) throws IllegalArgumentException{
        if (!Board.isValidTeamId(teamId)){
            throw new IllegalArgumentException("Identifiant d'équipe non valide");
        }
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getScore() {
        return score;
    }

    public void resetPoint(){
        setScore(2);
    }

    public void loseAPoint(){
        this.setScore(this.getScore()-1);
    }

    private void setScore(int score){
        this.score = score;
        this.notifyPlayerScoreObservers(this.getScore());
    }

    public void notifyPlayerScoreObservers(int score){
        for (PlayerScoreObserver playerScoreObserver : playerScoreObservers){
            playerScoreObserver.newScoreHandler(score);
        }
    }

    public void addPlayerScoreObservers(PlayerScoreObserver observer){
        playerScoreObservers.add(observer);
    }

    public void removePlayerScoreObservers(PlayerScoreObserver observer){
        playerScoreObservers.remove(observer);
    }
}
