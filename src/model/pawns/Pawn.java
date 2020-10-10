package model.pawns;

import model.Board;
import model.IPawn;
import model.Square;

public abstract class Pawn implements IPawn {
    private int teamId;
    private int height;
    private int range;
    private Square position;

    public Pawn(int teamId, int height, int range) throws IllegalArgumentException{
        if (!Board.isValidTeamId(teamId)){
            throw new IllegalArgumentException("Identifiant d'équipe non valide");
        }
        this.teamId = teamId;
        this.height = height;
        this.range = range;
    }

    public int getTeamId() { return teamId; }

    public int getHeight(){
        return this.height;
    }

    public int getMovementRange() { return this.range; }

    public boolean canJump(Square fromSquare, Square betweenSquare, Square toSquare){
        return (!fromSquare.equals(toSquare)
                && betweenSquare.hasPawn()
                && this.getHeight() >= betweenSquare.getPawn().getHeight()
                && !toSquare.hasPawn());
    }

    public boolean canGo(Square fromSquare, Square toSquare){
        int range = (int) Math.sqrt(Math.pow(fromSquare.getX()-toSquare.getX(),2)+Math.pow(fromSquare.getY()-toSquare.getY(),2));
        return (range > 0
                && range <= this.getMovementRange()
                && !toSquare.hasPawn());
    }

    @Override
    public String toString() {
        String color;
        if (teamId == Board.FIRST_TEAM_ID){
            color = "\u001B[37m";
        }else{
            color = "\u001B[30m";
        }
        switch (height){
            case 1:{
                return "\u001B[45m"+color+"| 1 |\u001B[0m";
            }
            case 2:{
                return "\u001B[43m"+color+"| 2 |\u001B[0m";
            }
            case 3:{
                return "\u001B[41m"+color+"| 3 |\u001B[0m";
            }
            case 4:{
                return "\u001B[30m"+color+"| 4 |\u001B[0m";
            }
            default: return height+"";
        }
    }
}
