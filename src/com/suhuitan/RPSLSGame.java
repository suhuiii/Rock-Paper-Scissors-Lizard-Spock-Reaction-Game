package com.suhuitan;


import java.util.Random;

public class RPSLSGame {
    public enum Choices{
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    private int playerScore, roundsPlayed;
    private Choices generatedChoice;

    public Choices getGeneratedChoice() {
        return generatedChoice;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getNoOfRoundsPlayed() {
        return roundsPlayed;
    }

    public RPSLSGame(){
        playerScore = 0;
        roundsPlayed = 0;
        generatedChoice = getRandomChoice();
    }

    public boolean playRound(Choices PlayerChoice){
        boolean won = false;
        if(playerWins(PlayerChoice, generatedChoice)) {
            playerScore++;
            won = true;
        }

        roundsPlayed++;
        generatedChoice = getRandomChoice();
        return won;
    }

    Choices getRandomChoice(){
        return Choices.values()[new Random().nextInt(Choices.values().length)];
    }

    boolean playerWins(Choices playerChoice, Choices computerChoice){
        switch(playerChoice){
            case ROCK:  if(computerChoice == Choices.SCISSORS || computerChoice == Choices.LIZARD)
                            return true;
                        break;
            case PAPER: if(computerChoice == Choices.ROCK || computerChoice == Choices.SPOCK)
                            return true;
                        break;
            case SCISSORS: if(computerChoice == Choices.PAPER || computerChoice == Choices.LIZARD)
                            return true;
                        break;
            case LIZARD: if(computerChoice == Choices.PAPER || computerChoice == Choices.SPOCK)
                            return true;
                        break;
            case SPOCK: if(computerChoice == Choices.SCISSORS || computerChoice == Choices.ROCK)
                            return true;
                        break;
            default: break;
        }
        return false;
    }

}
