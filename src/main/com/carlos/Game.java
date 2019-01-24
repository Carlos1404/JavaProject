package com.carlos;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {

    protected Map<Player, String> gameScore;

    protected Player player1;
    protected Player player2;

    public Game(Player player1, Player player2) {
        this.gameScore = new HashMap<>();

        this.player1 = player1;
        this.player2 = player2;

        resetMatch();
    }

    public void resetMatch() {
        this.gameScore.put(player1, "0");
        this.gameScore.put(player2, "0");
    }

    public abstract boolean addScore(Player player);

    public String getPoints(Player player) {
        return gameScore.get(player);
    }

}