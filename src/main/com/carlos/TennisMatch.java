package com.carlos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TennisMatch {

    private final Player player1;
    private final Player player2;
    private MatchType matchType;
    private boolean tieBreakInLastSet;

    private ArrayList<TennisSet> tennisSets;
    private TennisSet actualTennisSet;
    private int setsCpt = -1;

    private Map<Player, Integer> setsWon;

    private boolean isFinished;


    public TennisMatch(Player player1, Player player2, MatchType matchType, boolean tieBreakInLastSet){

        this.player1 = player1;
        this.player2 = player2;
        this.matchType = matchType;
        this.tieBreakInLastSet = tieBreakInLastSet;

        this.tennisSets = new ArrayList<>();
        this.setsWon = new HashMap<>();
        setsWon.put(player1, 0);
        setsWon.put(player2, 0);

        this.isFinished = nextSet();
    }

    public void updateWithPointWonBy(Player player){
        if(isFinished){
            return;
        }

        if(player != player1 && player != player2){
            return;
        }

        boolean isSetWin = actualTennisSet.addGamePoint(player);
        if(isSetWin){
            isFinished = nextSet();
        }
    }

    public String pointsForPlayer(Player player){
        return actualTennisSet.getGamePoints(player);
    }

    public int currentSetNumber(){
        return setsCpt +1;
    }

    public int gamesInCurrentSetForPlayer(Player player){
        if(player != player1 && player != player2){
            return -1;
        }

        return gamesInSetForPlayer(currentSetNumber(), player);
    }

    public int gamesInSetForPlayer(int setId, Player player){
        setId -= 1;
        if(setId > setsCpt){
            return -1;
        }

        TennisSet tennisSet = tennisSets.get(setId);
        return tennisSet.getGamesInSet(player);
    }

    public boolean isFinished(){
        return isFinished;
    }

    private boolean nextSet() {
        if(!isFinished && needMoreSet()){
            setsCpt++;
            TennisSet tennisSet;
            if(isLastSet() && !tieBreakInLastSet){
                tennisSet = new TennisSet(player1, player2, false);
            } else {
                tennisSet = new TennisSet(player1, player2, true);
            }

            tennisSets.add(tennisSet);
            actualTennisSet = tennisSets.get(setsCpt);
            return false;
        } else {
            return true;
        }
    }

    private boolean isLastSet() {
        return currentSetNumber() == matchType.numberOfSetsToWin();
    }

    private boolean needMoreSet() {

        Map<Player, Integer> setsWin = new HashMap<>();

        for(TennisSet set : tennisSets){
            if(set.isWin()){
                if(setsWin.containsKey(set.getWinner())){
                    setsWin.put(set.getWinner(), setsWin.get(set.getWinner())+1);
                } else {
                    setsWin.put(set.getWinner(), 1);
                }
            }
        }

        if(	(setsWin.containsKey(player1) && setsWin.get(player1) >= matchType.numberOfSetsToWin()) || 	(setsWin.containsKey(player2) && setsWin.get(player2) >= matchType.numberOfSetsToWin())){
            return  false;
        }
        else {
            return true;
        }
    }
}
