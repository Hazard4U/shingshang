package model;

import libs.MovementsInformation;
import libs.Tuple;
import model.pawns.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Board {
    private final int COLS = 10;
    private final int ROWS = 10;
    public static final int FIRST_TEAM_ID = 0;
    public static final int SECOND_TEAM_ID = 1;
    private static final int NO_TEAM_ID = -1;

    private ArrayList<ArrayList<Square>> board = new ArrayList<>();
    private Player[] players;

    public Board(Player[] players){
        if (players.length > 2){
            throw new IllegalArgumentException("Partie limité à deux joueurs");
        }
        if (players[0].getTeamId() == players[1].getTeamId()){
            throw new IllegalArgumentException("Les joueurs ne peuvent être dans le même camp");
        }
        this.players = players;
        reset(players);
    }

    public static boolean isValidTeamId(int teamId){
        return teamId == Board.FIRST_TEAM_ID || teamId == Board.SECOND_TEAM_ID || teamId == Board.NO_TEAM_ID;
    }

    private Square getSquare(int x, int y){
        return this.board.get(x).get(y);
    }

    private void setPawnOnSquare(Square square, IPawn pawn){
        square.setPawn(pawn);
    }

    private void setFirstPlayerPawns(Player firstPlayer) throws IllegalArgumentException{
        //Création des Dragons
        this.setPawnOnSquare(this.getSquare(0,1), new Dragon(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(0,8), new Dragon(firstPlayer.getTeamId()));

        //Création des Lions
        this.setPawnOnSquare(this.getSquare(0, 2), new Lion(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(0, 7), new Lion(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(1, 1), new Lion(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(1, 8), new Lion(firstPlayer.getTeamId()));

        //Création des Singes
        this.setPawnOnSquare(this.getSquare(0, 3), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(1, 2), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(2, 1), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(0, 6), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(1, 7), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(2, 8), new Monkey(firstPlayer.getTeamId()));
    }

    private void setSecondPlayerPawns(Player secondPlayer) throws IllegalArgumentException{
        //Création des Dragons
        this.setPawnOnSquare(this.getSquare(9, 1), new Dragon(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(9, 8), new Dragon(secondPlayer.getTeamId()));

        //Création des Lions
        this.setPawnOnSquare(this.getSquare(8, 1), new Lion(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 8), new Lion(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(9, 2), new Lion(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(9, 7), new Lion(secondPlayer.getTeamId()));

        //Création des Singes
        this.setPawnOnSquare(this.getSquare(7, 1), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 2), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(9, 3), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(7, 8), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 7), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(9, 6), new Monkey(secondPlayer.getTeamId()));
    }

    public void reset(Player[] players){
        players[FIRST_TEAM_ID].resetPoint();
        players[FIRST_TEAM_ID].setBoard(this);
        players[SECOND_TEAM_ID].resetPoint();
        players[SECOND_TEAM_ID].setBoard(this);

        for (int i = 0; i < COLS; i++){
            this.board.add(new ArrayList<>());
            for (int j = 0; j < ROWS; j++){
                this.board.get(i).add(new Square(i,j));
            }
        }

        //Ajout des murs pour former le terrain correct
        for (int i = 0; i < 4; i++){
            int indexColumn = i < 2 ? 0 : 9;
            int indexRow = i % 2 == 0 ? 0 : 6;
            for (int j = 0; j < 4; j++) {
                this.board.get(j+indexRow).get(indexColumn).setPawn(new Wall(Board.NO_TEAM_ID));
            }
        }

        //Création des Pions
        setFirstPlayerPawns(players[FIRST_TEAM_ID]);
        setSecondPlayerPawns(players[SECOND_TEAM_ID]);
    }

    private MovementsInformation getAvailableGoToSquares(IPawn pawn, Square currentSquare){
        MovementsInformation availableGoToSquares = new MovementsInformation(new HashMap<>());
        int x = -1;
        int y = -1;
        boolean canContinue;
        for (int j = 0; j < 9; j++){
            LinkedList<Square> historic = new LinkedList<>();
            int range = 0;
            do {
                canContinue = false;
                range++;
                int X = currentSquare.getX()+x*range;
                int Y = currentSquare.getY()+y*range;
                if (validCoords(X,Y)) {
                    Square square = this.getSquare(X, Y);
                    if (pawn.canGo(currentSquare, square)) {
                        canContinue = true;
                        availableGoToSquares.setMovementsInformation(square, null,historic);
                        historic.add(square);
                    }
                }
            }while(canContinue || range < pawn.getMovementRange());
            if ((j + 1) % 3 == 0) {
                x += 1;
                y = -1;
            } else {
                y += 1;
            }
        }
        return availableGoToSquares;
    }

    private MovementsInformation getAvailableJumpToSquares(
            IPawn pawn,
            Square currentSquare,
            Square lastSquare,
            MovementsInformation availableJumpToSquares
    ){
        int x = -1;
        int y = -1;
        for (int j = 0; j < 9; j++){
            // Coords de la case adjacente à notre position
            int aX = currentSquare.getX()+x;
            int aY = currentSquare.getY()+y;
            // Coords de la case cible
            int tX = aX*2-currentSquare.getX();
            int tY = aY*2-currentSquare.getY();

            if (this.validCoords(aX,aY) && this.validCoords(tX,tY)){
                Square adjacentSquare = this.getSquare(aX,aY);
                Square targetSquare = this.getSquare(tX,tY);
                if (pawn.canJump(currentSquare, adjacentSquare, targetSquare)){
                    LinkedList<Square> historic = new LinkedList<>();
                    Tuple<Square,LinkedList<Square>> lastMoveInfo = availableJumpToSquares.getMovementsInformation(lastSquare);
                    if (lastMoveInfo != null){
                        historic.addAll(lastMoveInfo.getSecondElement());
                        historic.add(lastSquare);
                    }
                    if (adjacentSquare.getPawn().getTeamId() == pawn.getTeamId()){
                        availableJumpToSquares.setMovementsInformation(targetSquare, null, historic);
                        availableJumpToSquares = getAvailableJumpToSquares(pawn, targetSquare,currentSquare, availableJumpToSquares);
                    }else{
                        availableJumpToSquares.setMovementsInformation(targetSquare, adjacentSquare, historic);
                    }
                }
            }
            if ((j+1) % 3 == 0){
                x++;
                y=-1;
            }else{
                y++;
            }
        }
        return availableJumpToSquares;
    }

    public MovementsInformation getAvailableMovements(int[] currentSquareCoords){
        Square currentSquare = this.getSquare(currentSquareCoords[0],currentSquareCoords[1]);
        MovementsInformation availableSquares = new MovementsInformation(new HashMap<>());
        if (!currentSquare.hasPawn())
            return availableSquares;

        availableSquares.setAllMovementInformation(getAvailableJumpToSquares(currentSquare.getPawn(), currentSquare, null, availableSquares));
        availableSquares.setAllMovementInformation(getAvailableGoToSquares(currentSquare.getPawn(), currentSquare));
        return availableSquares;
    }

    public boolean movePawn(Player player, int[] fromSquareCoords, int[] toSquareCoords) throws Exception{
        Square fromSquare = this.getSquare(fromSquareCoords[0],fromSquareCoords[1]);
        Square toSquare = this.getSquare(toSquareCoords[0],toSquareCoords[1]);
        IPawn pawnToMove = fromSquare.getPawn();
        if (pawnToMove == null)
            throw new Exception("Pas de pion sur la case à déplacer.");
        if (pawnToMove.getTeamId() != player.getTeamId())
            throw new Exception("Interdit de déplacer un pion adverse.");
        MovementsInformation availableSquares = getAvailableMovements(fromSquareCoords);

        Tuple<Square, LinkedList<Square>> moveInfo = availableSquares.getMovementsInformation(toSquare);
        if (moveInfo == null){
            return false;
        }

        this.setPawnOnSquare(toSquare,fromSquare.getPawn());
        this.setPawnOnSquare(fromSquare, null);
        if (moveInfo.getFirstElement() != null){
            this.capturePawn(moveInfo.getFirstElement());
        }
        return true;
    }

    private void capturePawn(Square square){
        IPawn pawnToCapture = square.getPawn();
        if (pawnToCapture == null)
            throw new NullPointerException("Pas de pion à capturer sur cette case");

        if ("Dragon".equals(pawnToCapture.getClass().getSimpleName())){
            if (pawnToCapture.getTeamId() == this.players[FIRST_TEAM_ID].getTeamId()){
                this.players[FIRST_TEAM_ID].loseAPoint();
            }else{
                this.players[SECOND_TEAM_ID].loseAPoint();
            }
        }
        square.setPawn(null);
    }

    public int winner(){
        Square[] portalsFirstTeam = {this.getSquare(1,4),this.getSquare(1,5)};
        Square[] portalsSecondTeam = {this.getSquare(8,4),this.getSquare(8,5)};
        for (Square portal : portalsFirstTeam){
            if (portal.getPawn() != null){
                if ("Dragon".equals(portal.getPawn().getClass().getSimpleName()) && portal.getPawn().getTeamId() == SECOND_TEAM_ID){
                    return SECOND_TEAM_ID;
                }
            }
        }
        for (Square portal : portalsSecondTeam){
            if (portal.getPawn() != null){
                if ("Dragon".equals(portal.getPawn().getClass().getSimpleName()) && portal.getPawn().getTeamId() == FIRST_TEAM_ID){
                    return FIRST_TEAM_ID;
                }
            }
        }
        return NO_TEAM_ID;
    }

    private boolean validCoords(int x , int y){
        return x < ROWS && x >= 0 && y < COLS && y >= 0;
    }

    @Override
    public String toString() {
        String boardString = "    \u001B[32m 0    1    2    3    4    5    6    7    8    9 \u001B[0m\n";
        boardString += "    ---  ---  ---  ---  ---  ---  ---  ---  ---  ---\n";
        for (int i = 0; i < COLS; i++){
            boardString += "\u001B[32m"+i+" \u001B[0m|";
            for (int j = 0; j < ROWS; j++){
                IPawn pawn = this.getSquare(i,j).getPawn();
                boardString += pawn == null ? "|   |" : pawn.toString();
            }
            boardString += "|\n";
            boardString += "    ---  ---  ---  ---  ---  ---  ---  ---  ---  ---\n";
        }
        return "Board{" +
                "COLS=" + COLS +
                ", ROWS=" + ROWS +
                ", board=\n" + boardString +
                '}';
    }
}
