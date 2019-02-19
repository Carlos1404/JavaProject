package com.carlos;

import java.util.HashMap;
import java.util.Map;

public class TennisSet {

    private Map<Player, Integer> numberGamesWonInSet;

    private Player player1;
    private Player player2;

    private Game currentGame;

    private Player winner = null;

    private boolean isTieBreak;

    public TennisSet(Player player1, Player player2, boolean isTieBreak) {
        this.player1 = player1;
        this.player2 = player2;

        numberGamesWonInSet = new HashMap<>();
        numberGamesWonInSet.put(player1, 0);
        numberGamesWonInSet.put(player2, 0);

        this.isTieBreak = isTieBreak;
        resetGame(Game.class);
    }

    public void addSetPoint(Player player) {
        numberGamesWonInSet.put(player, getGamesWonInSet(player) + 1);
        currentGame.resetMatch();
    }

    public int getGamesWonInSet(Player player) {
        return numberGamesWonInSet.get(player);
    }

    private void resetGame(Class<?> typeOfGame) {
        if (typeOfGame == GameTieBreak.class)
            this.currentGame = new GameTieBreak(player1, player2);
        else
            this.currentGame = new GameNoTieBreak(player1, player2);
        currentGame.resetMatch();
    }

    public boolean addGamePoint(Player player) {

        boolean isSetFinished = false;

        Player otherPlayer;

        otherPlayer = player.equals(player1) ? player2 : player1;

        currentGame.addScore(player);

        if (currentGame.isGameFinished(player)) {
            addSetPoint(player);
            int scorePlayerWinner = getGamesWonInSet(player);
            int scoreOtherPlayer = getGamesWonInSet(otherPlayer);
            if ((scorePlayerWinner == 6 && scoreOtherPlayer == 6) && isTieBreak) {
                resetGame(GameTieBreak.class);
            } else if ((scorePlayerWinner >= 6 && scorePlayerWinner - scoreOtherPlayer >= 2) || (isTieBreak && scorePlayerWinner == 7)) {
                this.winner = player;
                isSetFinished = true;
            } else {
                resetGame(Game.class);
            }
        }

        return isSetFinished;
    }


    public String getGamePoints(Player player) {
        return currentGame.getPoints(player);
    }

    public boolean isWin() {
        return winner != null;
    }

    public Player getWinner() {
        return winner;
    }
}