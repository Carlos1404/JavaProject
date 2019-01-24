package com.carlos;

public class MatchNoTieBreak extends Game {


    public MatchNoTieBreak(Player player1, Player player2) {

        super(player1, player2);

    }

    @Override
    public boolean addScore(Player player){
        boolean isGameFinished = false;
        Player otherPlayer;
        if(player.equals(player1))
            otherPlayer = player2;
        else
            otherPlayer = player1;

        switch (this.getPoints(player)){
            case "0":
                gameScore.put(player, "15");
                break;
            case "15":
                gameScore.put(player, "30");
                break;
            case "30":
                gameScore.put(player, "40");
                break;
            case "40":
                switch (this.getPoints(otherPlayer)){
                    case "40":
                        gameScore.put(player, "A");
                        break;
                    case "A":
                        gameScore.put(otherPlayer, "40");
                        break;
                    default:
                        isGameFinished = true;
                        break;
                }
                break;
            case "A":
                isGameFinished = true;
                break;
        }

        return isGameFinished;
    }

}
