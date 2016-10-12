package com.suhuitan;

import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static com.suhuitan.RPSLSGame.Choices.*;

public class RPSLSTest {
    RPSLSGame rpsls;

    @Before
    public void setup(){
        rpsls = new RPSLSGame();
    }

    @Test
    public void canary(){
        assertTrue(true);
    }

    @Test
    public void playerOutcomesAreCorrectForChoosingRock(){
        assertTrue(rpsls.playerWins(ROCK,SCISSORS));
        assertFalse(rpsls.playerWins(ROCK, ROCK));
        assertFalse(rpsls.playerWins(ROCK, PAPER));
        assertTrue(rpsls.playerWins(ROCK, LIZARD));
        assertFalse(rpsls.playerWins(ROCK, SPOCK));
    }

    @Test
    public void playerOutcomesAreCorrectForChoosingPaper(){
        assertTrue(rpsls.playerWins(PAPER, ROCK));
        assertFalse(rpsls.playerWins(PAPER, PAPER));
        assertFalse(rpsls.playerWins(PAPER, SCISSORS));
        assertTrue(rpsls.playerWins(PAPER, SPOCK));
        assertFalse(rpsls.playerWins(PAPER,LIZARD));
    }

    @Test
    public void playerOutcomesAreCorrectForChoosingScissors(){
        assertTrue(rpsls.playerWins(SCISSORS, PAPER));
        assertFalse(rpsls.playerWins(SCISSORS, ROCK));
        assertFalse(rpsls.playerWins(SCISSORS, SCISSORS));
        assertTrue(rpsls.playerWins(SCISSORS, LIZARD));
        assertFalse(rpsls.playerWins(SCISSORS, SPOCK));
    }
    @Test
    public void playerOutcomesAreCorrectForChoosingLizard(){
        assertTrue(rpsls.playerWins(LIZARD, SPOCK));
        assertFalse(rpsls.playerWins(LIZARD, LIZARD));
        assertFalse(rpsls.playerWins(LIZARD, SCISSORS));
        assertTrue(rpsls.playerWins(LIZARD, PAPER));
        assertFalse(rpsls.playerWins(LIZARD, ROCK));
    }
    @Test
    public void playerOutcomesAreCorrectForChoosingSpock(){
        assertTrue(rpsls.playerWins(SPOCK, ROCK));
        assertTrue(rpsls.playerWins(SPOCK, SCISSORS));
        assertFalse(rpsls.playerWins(SPOCK, PAPER));
        assertFalse(rpsls.playerWins(SPOCK, SPOCK));
        assertFalse(rpsls.playerWins(SPOCK, LIZARD));
    }

    @Test
    public void randomChoiceIsValid(){
        assertTrue(asList(RPSLSGame.Choices.values()).contains(rpsls.getRandomChoice()));
    }

    @Test
    public void scoresAndRoundsStartOffAs0(){
        assertEquals(0, rpsls.getPlayerScore());
        assertEquals(0, rpsls.getNoOfRoundsPlayed());
    }

    @Test
    public void win1RoundUpdatesScore(){
        rpsls = new RPSLSGame(){
            @Override
            Choices getRandomChoice(){
              return PAPER;
            }
        };
        assertTrue(rpsls.playRound(SCISSORS));
        assertEquals(1, rpsls.getPlayerScore());
    }

    @Test
    public void lose1RoundDoesNotUpdateScore(){
        rpsls = new RPSLSGame(){
            @Override
            Choices getRandomChoice(){
                return PAPER;
            }
        };
        assertFalse(rpsls.playRound(ROCK));
        assertEquals(0, rpsls.getPlayerScore());
    }

    @Test
    public void roundCounterUpdates(){
        for (int i = 0; i <5 ; i++) {
            rpsls.playRound(ROCK);
        }

        assertEquals(5, rpsls.getNoOfRoundsPlayed());
    }

    @Test
    public void choicePopulatedOnStart(){
        assertFalse(rpsls.getGeneratedChoice()== null);
    }
}
