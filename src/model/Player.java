package model;

import libs.MovementsInformation;
import libs.Tuple;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {
    private int teamId;
    private int score = 2;
    private Board board;

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
        this.score = 2;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean movePawn(int[] fromSquareCoords, int[] toSquareCoords) throws Exception{
        return this.board.movePawn(this, fromSquareCoords,toSquareCoords);
    }

    public void loseAPoint(){
        this.score--;
    }
}
