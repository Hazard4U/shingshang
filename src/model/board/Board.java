package model.board;

import exceptions.*;
import model.pawns.*;
import model.player.Player;
import model.movements.Movements;
import model.movements.MovementsInformation;
import model.movements.MovementsMap;

import java.util.*;

public class Board{
    private final int COLS = 10;
    private final int ROWS = 10;
    public static final int FIRST_TEAM_ID = 0;
    public static final int SECOND_TEAM_ID = 1;
    public static final int NO_TEAM_ID = -1;
    private static final int[][] firstPlayerPortals = new int[][]{new int[]{4,1}, new int[]{5,1}};
    private static final int[][] secondPlayerPortals = new int[][]{new int[]{4,8}, new int[]{5,8}};

    private ArrayList<ArrayList<Square>> board;
    private Player[] players;
    private Pawn movingPawn = null;
    private LinkedList<Pawn> movedPawnsInRound = new LinkedList<>();

    public Board(Board board){
        this.players = board.players;
        this.board = new ArrayList<>();
        int x = 0;
        for (ArrayList<Square> column : board.board){
            this.board.add(new ArrayList<>());
            for (Square square : column){
                if(square instanceof Portal){
                    this.board.get(x).add(new Portal((Portal)square));
                }else{
                    this.board.get(x).add(new Square(square));
                }
            }
            x++;
        }
    }

    public Board(Player[] players){
        if (players.length > 2){
            throw new IllegalArgumentException("Partie limité à deux joueurs");
        }
        if (players[0].getTeamId() == players[1].getTeamId()){
            throw new IllegalArgumentException("Les joueurs ne peuvent être dans le même camp");
        }
        this.players = players;
    }

    public static boolean isValidTeamId(int teamId){
        return teamId == Board.FIRST_TEAM_ID || teamId == Board.SECOND_TEAM_ID || teamId == Board.NO_TEAM_ID;
    }

    public Square getSquare(int x, int y) throws WrongCoordsException{
        if (!validCoords(x,y)){
            throw new WrongCoordsException("Coordonnées non valides");
        }
        return this.board.get(x).get(y);
    }

    private void setPawnOnSquare(Square square, Pawn pawn){
        square.setPawn(pawn);
    }

    private void setFirstPlayerPawns(Player firstPlayer) throws WrongCoordsException{
        //Création des Dragons
        this.setPawnOnSquare(this.getSquare(1,0), new Dragon(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8,0), new Dragon(firstPlayer.getTeamId()));

        //Création des Lions
        this.setPawnOnSquare(this.getSquare(2, 0), new Lion(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(7, 0), new Lion(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(1, 1), new Lion(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 1), new Lion(firstPlayer.getTeamId()));

        //Création des Singes
        this.setPawnOnSquare(this.getSquare(3, 0), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(2, 1), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(1, 2), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(6, 0), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(7, 1), new Monkey(firstPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 2), new Monkey(firstPlayer.getTeamId()));
    }

    private void setSecondPlayerPawns(Player secondPlayer) throws WrongCoordsException{
        //Création des Dragons
        this.setPawnOnSquare(this.getSquare(1, 9), new Dragon(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 9), new Dragon(secondPlayer.getTeamId()));

        //Création des Lions
        this.setPawnOnSquare(this.getSquare(1, 8), new Lion(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 8), new Lion(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(2, 9), new Lion(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(7, 9), new Lion(secondPlayer.getTeamId()));

        //Création des Singes
        this.setPawnOnSquare(this.getSquare(1, 7), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(2, 8), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(3, 9), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(8, 7), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(7, 8), new Monkey(secondPlayer.getTeamId()));
        this.setPawnOnSquare(this.getSquare(6, 9), new Monkey(secondPlayer.getTeamId()));
    }

    public void reset(){
        try{
            this.board = new ArrayList<>();
            for (int i = 0; i < COLS; i++){
                this.board.add(new ArrayList<>());
                for (int j = 0; j < ROWS; j++){
                    this.board.get(i).add(new Square(i,j));
                }
            }

            this.board.get(Board.firstPlayerPortals[0][0]).set(Board.firstPlayerPortals[0][1], new Portal(Board.firstPlayerPortals[0][0], Board.firstPlayerPortals[0][1], Board.FIRST_TEAM_ID));
            this.board.get(Board.firstPlayerPortals[1][0]).set(Board.firstPlayerPortals[1][1], new Portal(Board.firstPlayerPortals[1][0], Board.firstPlayerPortals[1][1], Board.FIRST_TEAM_ID));
            this.board.get(Board.secondPlayerPortals[0][0]).set(Board.secondPlayerPortals[0][1], new Portal(Board.secondPlayerPortals[0][0], Board.secondPlayerPortals[0][1], Board.SECOND_TEAM_ID));
            this.board.get(Board.secondPlayerPortals[1][0]).set(Board.secondPlayerPortals[1][1], new Portal(Board.secondPlayerPortals[1][0], Board.secondPlayerPortals[1][1], Board.SECOND_TEAM_ID));

            //Ajout des murs pour former le terrain correct
            for (int i = 0; i < 4; i++){
                int indexColumn = i < 2 ? 0 : 9;
                int indexRow = i % 2 == 0 ? 0 : 6;
                for (int j = 0; j < 4; j++) {
                    this.setPawnOnSquare(this.getSquare(indexColumn,j+indexRow), new Wall(Board.NO_TEAM_ID));
                }
            }

            //Création des Pions
            setFirstPlayerPawns(players[FIRST_TEAM_ID]);
            setSecondPlayerPawns(players[SECOND_TEAM_ID]);
        }catch (WrongCoordsException exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }

    /**
     * retourne une Map avec toutes les cases destinations posssibles en marchant, pour un certain pion avec une certaine case de départ
     * @param pawn pion à considérer
     * @param currentSquare case de départ de l'analyse
     * @return
     * @throws WrongCoordsException
     */
    private MovementsMap getAvailableWalkSquares(IPawn pawn, Square currentSquare) throws WrongCoordsException{
        MovementsMap availableGoToSquares = new MovementsMap(new HashMap<>());
        int x = -1;
        int y = -1;
        boolean canContinue;
        for (int j = 0; j < 9; j++){
            int range = 0;
            do {
                canContinue = false;
                range++;
                int X = currentSquare.getX()+x*range;
                int Y = currentSquare.getY()+y*range;
                if (validCoords(X,Y)) {
                    Square square = this.getSquare(X, Y);
                    if (pawn.canGo(getSquaresBetween(currentSquare, square))) {
                        canContinue = true;
                        availableGoToSquares.setMovementsInformation(square, new MovementsInformation(Movements.WALK, null));
                    }
                }
            }while(canContinue && range < pawn.getMovementRange());
            if ((j + 1) % 3 == 0) {
                x += 1;
                y = -1;
            } else {
                y += 1;
            }
        }
        return availableGoToSquares;
    }

    /**
     * retourne une Map avec toutes les cases destinations posssibles en sautant, pour un certain pion avec une certaine case de départ
     * @param pawn pion à considérer
     * @param currentSquare case de départ de l'analyse
     * @param availableJumpToSquares
     * @return
     * @throws WrongCoordsException
     */
    private MovementsMap getAvailableJumpToSquares(
            IPawn pawn,
            Square currentSquare,
            MovementsMap availableJumpToSquares
    ) throws WrongCoordsException{
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
                    if (adjacentSquare.getPawn().getTeamId() == pawn.getTeamId()){
                        // Si dans mon historique des déplacements déjà cherché la case cible n'existe pas je vais l'analyser
                        // Sinon je l'ignore pour éviter les boucles infinies
                        if(availableJumpToSquares.getMovementsInformation(targetSquare) == null){
                            availableJumpToSquares.setMovementsInformation(targetSquare, new MovementsInformation(Movements.JUMP, null));
                            availableJumpToSquares = getAvailableJumpToSquares(pawn, targetSquare, availableJumpToSquares);
                        }
                    }else{
                        availableJumpToSquares.setMovementsInformation(targetSquare, new MovementsInformation(Movements.JUMP, adjacentSquare));
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

    /**
     * Retoune une map de tous les déplacements depuis une coordonnées de départ
     * @param fromSquareCoords index 0 = x, index 1 = y
     * @return
     * @throws WrongCoordsException
     */
    public MovementsMap getAvailableMovements(int[] fromSquareCoords) throws WrongCoordsException{
        Square currentSquare = this.getSquare(fromSquareCoords[0],fromSquareCoords[1]);
        MovementsMap availableSquares = new MovementsMap(new HashMap<>());
        if (currentSquare.hasPawn()){
            availableSquares.setAllMovementInformation(getAvailableJumpToSquares(currentSquare.getPawn(), currentSquare, availableSquares));
            availableSquares.setAllMovementInformation(getAvailableWalkSquares(currentSquare.getPawn(), currentSquare));
        }
        return availableSquares;
    }

    /**
     * Retoune une map de tous les déplacements en sautant depuis une coordonnées de départ
     * @param fromSquareCoords index 0 = x, index 1 = y
     * @return
     * @throws WrongCoordsException
     */
    public MovementsMap getAvailableJumpMovements(int[] fromSquareCoords) throws WrongCoordsException {
        Square currentSquare = this.getSquare(fromSquareCoords[0], fromSquareCoords[1]);
        MovementsMap availableSquares = new MovementsMap(new HashMap<>());
        if (currentSquare.hasPawn()) {
            availableSquares.setAllMovementInformation(getAvailableJumpToSquares(currentSquare.getPawn(), currentSquare, availableSquares));
        }
        return availableSquares;
    }

    /**
     * Retoune une map de tous les déplacements en marchant depuis une coordonées de départ
     * @param fromSquareCoords index 0 = x, index 1 = y
     * @return
     * @throws WrongCoordsException
     */
    public MovementsMap getAvailableWalkMovements(int[] fromSquareCoords) throws WrongCoordsException {
        Square currentSquare = this.getSquare(fromSquareCoords[0], fromSquareCoords[1]);
        MovementsMap availableSquares = new MovementsMap(new HashMap<>());
        if (currentSquare.hasPawn()) {
            availableSquares.setAllMovementInformation(getAvailableWalkSquares(currentSquare.getPawn(), currentSquare));
        }
        return availableSquares;
    }

    /**
     * Déplace le pion du jouer passé en paramètre
     * @param player
     * @param fromSquareCoords index 0 = x, index 1 = y
     * @param toSquareCoords index 0 = x, index 1 = y
     * @return Un tuple associant la case du pion mangé s'il y en a avec la liste des cases parcourues
     * @throws NoPawnException
     * @throws MoveEnemyPawnException
     * @throws WrongMovementException
     * @throws WrongCoordsException
     */
    public MovementsInformation movePawn(Player player, int[] fromSquareCoords, int[] toSquareCoords) throws NoPawnException, MoveEnemyPawnException, WrongMovementException, WrongCoordsException, OtherPawnAlreadyMovingException, PawnAlreadyMovedInRoundException {
        Square fromSquare = this.getSquare(fromSquareCoords[0],fromSquareCoords[1]);
        Square toSquare = this.getSquare(toSquareCoords[0],toSquareCoords[1]);
        Pawn pawnToMove = fromSquare.getPawn();

        if (pawnToMove == null){
            throw new NoPawnException("Pas de pion sur la case à déplacer.");
        }else if (pawnToMove.getTeamId() != player.getTeamId()){
            throw new MoveEnemyPawnException("Interdit de déplacer un pion adverse.");
        }else if(this.movingPawn != null && this.movingPawn != pawnToMove){
            throw new OtherPawnAlreadyMovingException("Un autre pion est déjà en mouvement");
        }else if(pawnToMove.hasMove()){
            throw new PawnAlreadyMovedInRoundException("Ce pion a déjà été joué durant ce tour");
        }

        MovementsInformation movementsInformation = new MovementsInformation(Movements.WALK, null);
        if (!pawnToMove.canGo(getSquaresBetween(fromSquare,toSquare))) {
            Square betweenSquare = this.getSquare((fromSquare.getX()+toSquare.getX())/2, (fromSquare.getY()+toSquare.getY())/2);
            if(!pawnToMove.canJump(fromSquare, betweenSquare, toSquare)){
                throw new WrongMovementException("Déplacement impossible");
            }
            if (betweenSquare.getPawn().getTeamId() != player.getTeamId()){
                movementsInformation.setCapturedPawnSquare(betweenSquare);
            }
            movementsInformation.setMovements(Movements.JUMP);
        }

        this.movingPawn = pawnToMove;
        this.setPawnOnSquare(toSquare,fromSquare.getPawn());
        this.setPawnOnSquare(fromSquare, null);
        return movementsInformation;
    }

    /**
     * Récupère toutes les cases situées entre deux positions
     * @param fromSquare
     * @param toSquare
     * @return
     * @throws WrongCoordsException
     */
    private LinkedList<Square> getSquaresBetween(Square fromSquare, Square toSquare) throws WrongCoordsException {
        int xVector = (toSquare.getX()-fromSquare.getX()) == 0 ? 0 : (toSquare.getX()-fromSquare.getX()) / Math.abs(toSquare.getX()-fromSquare.getX());
        int yVector = (toSquare.getY()-fromSquare.getY()) == 0 ? 0 : (toSquare.getY()-fromSquare.getY()) / Math.abs(toSquare.getY()-fromSquare.getY());
        int distance = (int) Math.sqrt(Math.pow(fromSquare.getY()-toSquare.getY(), 2)+Math.pow(fromSquare.getX()-toSquare.getX(),2));
        LinkedList<Square> squares = new LinkedList<>();
        squares.add(fromSquare);
        for (int i = 0; i < distance; i++){
            squares.add(getSquare(squares.get(i).getX()+xVector, squares.get(i).getY()+yVector));
        }
        return squares;
    }

    /**
     * Déclare la fin de déplacement du pion actuellement en déplacement
     * @throws PlayerNotPlayingException
     */
    public void finishPawnMove() throws PlayerNotPlayingException{
        if (this.movingPawn == null){
            throw new PlayerNotPlayingException("Le joueur n'a pas joué.");
        }

        this.movingPawn.setHasMove(true);
        this.movedPawnsInRound.add(this.movingPawn);
        this.movingPawn = null;
    }

    /**
     * Remet tous les pions déplacés durant le tour au statut non déplacé
     */
    public void resetMovedPawnsStatus(){
        for (Pawn pawn : this.movedPawnsInRound){
            pawn.setHasMove(false);
        }
    }

    /**
     * Capture un pion du plateau
     * @param square
     */
    public void deletePawnOnSquare(Square square){
        IPawn pawnToCapture = square.getPawn();
        if (pawnToCapture == null){
            throw new NullPointerException("Pas de pion à capturer sur cette case");
        }

        square.setPawn(null);
    }

    /**
     * retourne l'identifiant de l'équipe gagnant est Board.NO_TEAM_ID si non
     * @return
     */
    public int winnerOnPortal(){
        Square[] portalsFirstTeam = new Square[2];
        Square[] portalsSecondTeam = new Square[2];
        int looser = NO_TEAM_ID;

        try{
            portalsFirstTeam[0] = this.getSquare(Board.firstPlayerPortals[0][0],Board.firstPlayerPortals[0][1]);
            portalsFirstTeam[1] = this.getSquare(Board.firstPlayerPortals[1][0],Board.firstPlayerPortals[1][1]);
            portalsSecondTeam[0] = this.getSquare(Board.secondPlayerPortals[0][0],Board.secondPlayerPortals[0][1]);
            portalsSecondTeam[1] = this.getSquare(Board.secondPlayerPortals[1][0],Board.secondPlayerPortals[1][1]);
        }catch (WrongCoordsException exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }

        for (Square portal : portalsFirstTeam){
            if (portal.hasPawn()){
                if (portal.getPawn() instanceof Dragon && portal.getPawn().getTeamId() == SECOND_TEAM_ID){
                    looser = SECOND_TEAM_ID;
                }
            }
        }
        for (Square portal : portalsSecondTeam){
            if (portal.hasPawn()){
                if (portal.getPawn() instanceof Dragon && portal.getPawn().getTeamId() == FIRST_TEAM_ID){
                    looser = FIRST_TEAM_ID;
                }
            }
        }

        return looser;
    }

    private boolean validCoords(int x , int y){
        return x < ROWS && x >= 0 && y < COLS && y >= 0;
    }

    @Override
    public String toString() {
        try{
            return this.representBoard(false, null);
        }catch (WrongCoordsException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Visualise le board avec les prédications de déplacement d'un pion situé sur les coordonnées passées en paramètre
     * @param fromSquare
     * @return
     * @throws WrongCoordsException
     */
    public String toStringMovements(int[] fromSquare) throws WrongCoordsException {
        return this.representBoard(true, fromSquare);
    }

    /**
     * Visualise le board
     * @param withMovements active la prédication des déplacements
     * @param fromSquare coordonnées du pion à analyser
     * @return
     * @throws WrongCoordsException
     */
    private String representBoard(boolean withMovements, int[] fromSquare) throws WrongCoordsException{
        Set<Square> squareSet = new HashSet<>();
        if (withMovements){
            squareSet.addAll(getAvailableMovements(fromSquare).getHashMap().keySet());
        }
        StringBuilder boardString = new StringBuilder();
        boardString.append("    \u001B[32m 0    1    2    3    4    5    6    7    8    9 \u001B[0m\n");
        boardString.append("    ---  ---  ---  ---  ---  ---  ---  ---  ---  ---\n");
        try{
            for (int i = 0; i < COLS; i++){
                boardString.append("\u001B[32m").append(i).append(" \u001B[0m|");
                for (int j = 0; j < ROWS; j++){
                    Square square = this.getSquare(j,i);
                    if(squareSet.contains(square)){
                        boardString.append("| \u001B[31mX\u001B[0m |");
                    }else{
                        boardString.append(square.toString());
                    }
                }
                boardString.append("| \u001B[32m").append(i).append("\u001B[0m\n");
                boardString.append("    ---  ---  ---  ---  ---  ---  ---  ---  ---  ---\n");
            }
        }catch (WrongCoordsException exception){
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        boardString.append("    \u001B[32m 0    1    2    3    4    5    6    7    8    9 \u001B[0m\n");
        return boardString.append("}").toString();
    }

    public ArrayList<ArrayList<Square>> getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return COLS == board1.COLS &&
                ROWS == board1.ROWS &&
                Objects.equals(board, board1.board) &&
                Arrays.equals(players, board1.players) &&
                Objects.equals(movingPawn, board1.movingPawn) &&
                Objects.equals(movedPawnsInRound, board1.movedPawnsInRound);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(COLS, ROWS, board, movingPawn, movedPawnsInRound);
        result = 31 * result + Arrays.hashCode(players);
        return result;
    }
}
