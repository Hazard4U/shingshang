import exceptions.*;
import model.game.Game;
import model.board.Board;
import observers.EndGameObserver;
import observers.PlayerRoundObserver;
import observers.PlayerScoreObserver;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner( System.in );
            Game game = new Game();
            FirstPlayerObserver firstPlayerObserver = new FirstPlayerObserver(game);
            SecondPlayerObserver secondPlayerObserver = new SecondPlayerObserver(game);
            game.addFirstPlayerRoundObservers(firstPlayerObserver);
            game.addFirstPlayerScoreObservers(firstPlayerObserver);
            game.addEndGameObservers(firstPlayerObserver);
            game.addSecondPlayerRoundObservers(secondPlayerObserver);
            game.addSecondPlayerScoreObservers(secondPlayerObserver);
            game.addEndGameObservers(secondPlayerObserver);
            game.start();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static class FirstPlayerObserver implements PlayerRoundObserver, PlayerScoreObserver, EndGameObserver{
        private Game game;
        FirstPlayerObserver(Game game){
            this.game = game;
        }
        @Override
        public void nextRoundHandler(Board board) {
            boolean repeat = true;
            while (repeat){
                repeat = false;
                game.printBoard();
                Scanner scanner = new Scanner( System.in );
                int coordsDepX = scanner.nextInt();
                int coordsDepY = scanner.nextInt();
                int coordsArrX = scanner.nextInt();
                int coordsArrY = scanner.nextInt();
                System.out.println(coordsArrX);
                try{
                    if (coordsDepX == 0 && coordsDepY == 0 && coordsArrX == 0 && coordsArrY == 0){
                        game.firstPlayerMove(true,null, null);
                    }else{
                        System.out.println(board.toStringMovements(new int[]{coordsDepX, coordsDepY}));
                        game.firstPlayerMove(false,new int[]{coordsDepX, coordsDepY}, new int[]{coordsArrX, coordsArrY});
                    }
                }catch (MoveEnemyPawnException | NoPawnException | WrongCoordsException | WrongMovementException | PawnAlreadyMovedInRoundException | OtherPawnAlreadyMovingException | PlayerNotPlayingException e){
                    repeat = true;
                    System.out.println(e.getMessage());
                } catch (WrongRoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void newScoreHandler(int score) {
            System.out.println("First NOUVEAU SCORE: "+score);
        }


        @Override
        public void endGameHandler(int teamWinnerID) {
            System.out.println("FIN de partie");
            if (teamWinnerID == Board.SECOND_TEAM_ID){
                System.out.println("Premier joueur tu es PERDANT");
            }else if(teamWinnerID == Board.FIRST_TEAM_ID){
                System.out.println("Premier joueur tu es GAGNANT");
            }
        }
    }

    public static class SecondPlayerObserver implements PlayerRoundObserver, PlayerScoreObserver, EndGameObserver {
        private Game game;
        SecondPlayerObserver(Game game){
            this.game = game;
        }
        @Override
        public void nextRoundHandler(Board board) {
            boolean repeat = true;
            while (repeat){
                repeat = false;
                game.printBoard();
                Scanner scanner = new Scanner( System.in );
                int coordsDepX = scanner.nextInt();
                int coordsDepY = scanner.nextInt();
                int coordsArrX = scanner.nextInt();
                int coordsArrY = scanner.nextInt();
                try{
                    if (coordsDepX == 0 && coordsDepY == 0 && coordsArrX == 0 && coordsArrY == 0){
                        game.secondPlayerMove(true,null, null);
                    }else{
                        System.out.println(board.toStringMovements(new int[]{coordsDepX, coordsDepY}));
                        board.reset();
                        game.secondPlayerMove(false,new int[]{coordsDepX, coordsDepY}, new int[]{coordsArrX, coordsArrY});
                    }
                }catch (MoveEnemyPawnException | NoPawnException | WrongCoordsException | WrongMovementException | PawnAlreadyMovedInRoundException | OtherPawnAlreadyMovingException | PlayerNotPlayingException e){
                    repeat = true;
                    System.out.println(e.getMessage());
                } catch (WrongRoundException e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void newScoreHandler(int score) {
            System.out.println("Second NOUVEAU SCORE: "+score);
        }

        @Override
        public void endGameHandler(int teamWinnerID) {
            System.out.println("FIN de partie");
            if (teamWinnerID == Board.SECOND_TEAM_ID){
                System.out.println("Second joueur tu es GAGNANT");
            }else if(teamWinnerID == Board.FIRST_TEAM_ID){
                System.out.println("Second joueur tu es PERDANT");
            }
        }
    }
}
