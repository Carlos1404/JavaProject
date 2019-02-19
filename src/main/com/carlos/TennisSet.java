package com.carlos;

import java.util.HashMap;
import java.util.Map;

public class TennisSet {

    private Map<Player, Integer> gamesSet;

    private Player player1;
    private Player player2;

    private Game currentGame;

    private Player winner = null;

    private boolean isTieBreak;

    public TennisSet(Player player1, Player player2, boolean isTieBreak) {
        this.player1 = player1;
        this.player2 = player2;

        gamesSet = new HashMap<>();
        gamesSet.put(player1, 0);
        gamesSet.put(player2, 0);

        this.isTieBreak = isTieBreak;
        resetGame(Game.class);
    }

    public void addSetPoint(Player player) {
        gamesSet.put(player, getGamesInSet(player) + 1);
        currentGame.resetMatch();
    }

    public int getGamesInSet(Player player) {
        return gamesSet.get(player);
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

        if (player.equals(player1))
            otherPlayer = player2;
        else
            otherPlayer = player1;

        currentGame.addScore(player);

        if (currentGame.isGameFinished(player)) {
            addSetPoint(player);
            int scorePlayerWinner = getGamesInSet(player);
            int scoreOtherPlayer = getGamesInSet(otherPlayer);
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