package com.carlos;

public class GameTieBreak extends Game {

    public GameTieBreak(Player player1, Player player2) {
        super(player1, player2);

    }


    @Override
    public void addScore(Player player) {
        String actualScore = Integer.toString(Integer.valueOf(this.getPoints(player)) + 1);
        gameScore.put(player, actualScore);

    }

    @Override
    public boolean isGameFinished(Player player) {

        Player otherPlayer;

        otherPlayer = player.equals(player1) ? player2 : player1;

        return Integer.valueOf(this.getPoints(player)) >= 7 && Integer.valueOf(this.getPoints(player)) - Integer.valueOf(gameScore.get(otherPlayer)) >= 2;

    }
}
