package com.carlos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TennisMatchTest {

    static final Player player1 = new Player("test");
    static final Player player2 = new Player("test2");
    static TennisMatch testBO3TieBreak;
    static TennisMatch testBO5TieBreak;
    static TennisMatch testBO3NoTieBreak;
    static TennisMatch testBO5NoTieBreak;

    //Player
    @Test
    public void getName() throws Exception {
        Player test = new Player("test");
        assertEquals("test", test.getName());
    }

    //Tennis Match
    @Before
    public void initMatch(){
        testBO3TieBreak = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, true);
        testBO5TieBreak = new TennisMatch(player1, player2, MatchType.BEST_OF_FIVE, true);
        testBO3NoTieBreak = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);
        testBO5NoTieBreak = new TennisMatch(player1, player2, MatchType.BEST_OF_THREE, false);
    }

    @Test
    public void player1Player2NotEquals() {
        assertFalse(player1.equals(player2));
    }

    @Test
    public void nullPlayerWin(){
        testBO3TieBreak.updateWithPointWonBy(null);

        assertEquals("0", testBO3TieBreak.pointsForPlayer(player1));
        assertEquals("0", testBO3TieBreak.pointsForPlayer(player2));
    }

    @Test
    public void player1ScoresFirstPoint(){
        testBO3TieBreak.updateWithPointWonBy(player1);

        assertEquals("15", testBO3TieBreak.pointsForPlayer(player1));
    }
    @Test
    public void player1ScoresSecondPoint(){
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        assertEquals("30", testBO3TieBreak.pointsForPlayer(player1));
    }
    @Test
    public void player1ScoresThirdPoint(){
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        assertEquals("40", testBO3TieBreak.pointsForPlayer(player1));
    }

    @Test
    public void player1AndPlayer2HaveDifferentScores(){
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        testBO3TieBreak.updateWithPointWonBy(player2);


        assertEquals("40", testBO3TieBreak.pointsForPlayer(player1));
        assertEquals("15", testBO3TieBreak.pointsForPlayer(player2));
    }
    @Test
    public void player1AndPlayer2Have40Points(){
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);

        assertEquals("40", testBO3TieBreak.pointsForPlayer(player1));
        assertEquals("40", testBO3TieBreak.pointsForPlayer(player2));
    }

    @Test
    public void player1TakeTheAdvantage(){
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);

        testBO3TieBreak.updateWithPointWonBy(player1);

        assertEquals("A", testBO3TieBreak.pointsForPlayer(player1));
        assertEquals("40", testBO3TieBreak.pointsForPlayer(player2));
    }

    @Test
    public void player1WinTheFirstGame(){
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);

        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);



        assertEquals(1, testBO3TieBreak.gamesInCurrentSetForPlayer(player1));
    }



    @Test
    public void tieBreakInLastSetThenPlayer2WinTheMatch(){
        winASet(player1, testBO3TieBreak);
        winASet(player2, testBO3TieBreak);

        winAGame(player1, testBO3TieBreak);
        winAGame(player1, testBO3TieBreak);
        winAGame(player1, testBO3TieBreak);
        winAGame(player1, testBO3TieBreak);
        winAGame(player1, testBO3TieBreak);

        winAGame(player2, testBO3TieBreak);
        winAGame(player2, testBO3TieBreak);
        winAGame(player2, testBO3TieBreak);
        winAGame(player2, testBO3TieBreak);
        winAGame(player2, testBO3TieBreak);

        winAGame(player1, testBO3TieBreak);
        winAGame(player2, testBO3TieBreak);

        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);
        testBO3TieBreak.updateWithPointWonBy(player1);

        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);
        testBO3TieBreak.updateWithPointWonBy(player2);

        assertEquals("4", testBO3TieBreak.pointsForPlayer(player1));
        assertEquals("6", testBO3TieBreak.pointsForPlayer(player2));

        testBO3TieBreak.updateWithPointWonBy(player2);

        assertEquals(6, testBO3TieBreak.gamesInSetForPlayer(3, player1));
        assertEquals(7, testBO3TieBreak.gamesInSetForPlayer(3, player2));

        assertEquals(true,testBO3TieBreak.isFinished());
    }

    //Methode pour alleger le code
    public void winAGame(Player p, TennisMatch m){
        m.updateWithPointWonBy(p);
        m.updateWithPointWonBy(p);
        m.updateWithPointWonBy(p);
        m.updateWithPointWonBy(p);
    }
    //Methode pour alleger le code
    public void winASet(Player p, TennisMatch m){
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
        winAGame(p, m);
    }

}