package com.carlos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TennisMatchTest {

    static final Player player1 = new Player("test");
    static final Player player2 = new Player("test2");
    static TennisMatch testMatchWithTieBreak1;
    static TennisMatch testMatchWithoutTieBreak1;

    //Player
    @Test
    public void getName() throws Exception {
        Player test = new Player("test");
        assertEquals("test", test.getName());
    }

    //Tennis Match
    @Before
    public void initMatch(){
        testMatchWithTieBreak1 = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, true);
        testMatchWithoutTieBreak1 = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);
    }

    @Test
    public void player1Player2NotEquals() {
        assertFalse(player1.equals(player2));
    }

    @Test
    public void player1ScoresFirstPoint(){
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        assertEquals("15", testMatchWithTieBreak1.pointsForPlayer(player1));
    }
    @Test
    public void player1ScoresSecondPoint(){
        testMatchWithoutTieBreak1.updateWithPointWonBy(player1);
        testMatchWithoutTieBreak1.updateWithPointWonBy(player1);

        assertEquals("30", testMatchWithoutTieBreak1.pointsForPlayer(player1));
    }
    @Test
    public void player1ScoresThirdPoint(){
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        assertEquals("40", testMatchWithTieBreak1.pointsForPlayer(player1));
    }

    @Test
    public void player1AndPlayer2HaveDifferentScores(){
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        testMatchWithTieBreak1.updateWithPointWonBy(player2);


        assertEquals("40", testMatchWithTieBreak1.pointsForPlayer(player1));
        assertEquals("15", testMatchWithTieBreak1.pointsForPlayer(player2));
    }
    @Test
    public void player1AndPlayer2Have40Points(){
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        assertEquals("40", testMatchWithTieBreak1.pointsForPlayer(player1));
        assertEquals("40", testMatchWithTieBreak1.pointsForPlayer(player2));
    }

    @Test
    public void player1TakeTheAdvantage(){
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        assertEquals("A", testMatchWithTieBreak1.pointsForPlayer(player1));
        assertEquals("40", testMatchWithTieBreak1.pointsForPlayer(player2));
    }

    @Test
    public void player1WinTheFirstGame(){
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        assertEquals(1, testMatchWithTieBreak1.gamesInCurrentSetForPlayer(player1));
        assertEquals(0, testMatchWithTieBreak1.gamesInCurrentSetForPlayer(player2));
    }

    @Test
    public void isLastSet(){

        winASet(player1, testMatchWithTieBreak1);

        assertEquals(false, testMatchWithTieBreak1.isFinished());

        winASet(player1, testMatchWithTieBreak1);

        assertEquals(true, testMatchWithTieBreak1.isFinished());
    }


    @Test
    public void MatchWithTieBreakInLastSetThenPlayer2WinTheMatch(){
        winASet(player1, testMatchWithTieBreak1);
        winASet(player2, testMatchWithTieBreak1);

        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);

        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);

        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);

        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        assertEquals("4", testMatchWithTieBreak1.pointsForPlayer(player1));
        assertEquals("6", testMatchWithTieBreak1.pointsForPlayer(player2));

        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        assertEquals(6, testMatchWithTieBreak1.gamesInSetForPlayer(3, player1));
        assertEquals(7, testMatchWithTieBreak1.gamesInSetForPlayer(3, player2));

        assertEquals(true, testMatchWithTieBreak1.isFinished());
    }

    @Test
    public void player1WinFirstSetWithTieBreak(){

        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player1, testMatchWithTieBreak1);

        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);

        winAGame(player1, testMatchWithTieBreak1);
        winAGame(player2, testMatchWithTieBreak1);

        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);
        testMatchWithTieBreak1.updateWithPointWonBy(player1);

        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);
        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        assertEquals("4", testMatchWithTieBreak1.pointsForPlayer(player1));
        assertEquals("6", testMatchWithTieBreak1.pointsForPlayer(player2));

        testMatchWithTieBreak1.updateWithPointWonBy(player2);

        assertEquals(6, testMatchWithTieBreak1.gamesInSetForPlayer(1, player1));
        assertEquals(7, testMatchWithTieBreak1.gamesInSetForPlayer(1, player2));

        assertEquals(false, testMatchWithTieBreak1.isFinished());
    }

    public void winAGame(Player p, TennisMatch m){
        m.updateWithPointWonBy(p);
        m.updateWithPointWonBy(p);
        m.updateWithPointWonBy(p);
        m.updateWithPointWonBy(p);
    }

    public void winASet(Player p, TennisMatch m){
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
    }

}