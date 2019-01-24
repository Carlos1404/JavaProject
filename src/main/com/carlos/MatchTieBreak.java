package com.carlos;

public class MatchTieBreak extends Game {

    public MatchTieBreak(Player player1, Player player2) {

        super(player1, player2);

    }


    @Override
    public boolean addScore(Player player) {

        boolean isGameFinished = false;

        String previousScore = this.getPoints(player);

        Player otherPlayer;
        if (player.equals(player1))
            otherPlayer = player2;
        else
            otherPlayer = player1;

        String actualScore = Integer.toString(Integer.valueOf(previousScore) + 1);
        gameScore.put(player, actualScore);

        if (Integer.valueOf(actualScore) >= 7 && Integer.valueOf(actualScore) - Integer.valueOf(gameScore.get(otherPlayer)) >= 2) {
            isGameFinished = true;
        }

        return isGameFinished;
    }
}
