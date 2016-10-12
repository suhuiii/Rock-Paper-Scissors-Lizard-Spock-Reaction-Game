package com.suhuitan.ui;


import com.suhuitan.RPSLSGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import com.suhuitan.RPSLSGame.Choices;

class RPSLSFrame extends JFrame {

    private RPSLSGame rpsls;

    private JPanel computerPanel;
    private JPanel scorePanel;
    private JPanel titlePanel;
    private JPanel userPanel;

    private JLabel currentScore;
    private JLabel computerChoiceLabel;
    private JLabel timerLabel;

    private Timer timer;
    private long startTime;
    private long bestTime;
    private JLabel bestTimesLabel;
    private JLabel scoreLabel;

    RPSLSFrame(){
        setVisible(true);
        setResizable(true);
        setTitle("Rock Paper Scissors Lizard Spock");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new Color(255,255,0));
    }
    public static void main(String[] args){
        new RPSLSFrame();
    }
    private void updateScores(){
        currentScore.setText(String.format("Won %d out of %d games", rpsls.getPlayerScore(), rpsls.getNoOfRoundsPlayed()));
    }
    private void updateComputerChoice(){
        Choices choice = rpsls.getGeneratedChoice();
        computerChoiceLabel.setText(choice.toString());
        computerChoiceLabel.setIcon(getIconForChoice(choice));
    }

    private void updateTimes(long currentTime) {
        long timeTaken = currentTime - startTime;
        if(bestTime == 0 || timeTaken < bestTime)
        {
            bestTime = currentTime - startTime;
        }

        bestTimesLabel.setText("Best: " + new SimpleDateFormat("mm:ss:SSS").format(bestTime) + " Last Successful: " + new SimpleDateFormat("mm:ss:SSS").format(timeTaken));

        startTime = System.currentTimeMillis();
    }

    @Override
    protected void frameInit(){
        super.frameInit();

        rpsls = new RPSLSGame();
        initializePanels();

        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(250,250,250));

        addContainer(titlePanel, 0, 0, 3, 1, GridBagConstraints.BOTH);
        addContainer(userPanel, 0, 1, 1, 1, GridBagConstraints.BOTH);
        addContainer(scorePanel, 1, 1, 1, 1,GridBagConstraints.BOTH);
        addContainer(computerPanel, 2, 1, 1, 1, GridBagConstraints.BOTH);

        timer = new Timer(10, new timerListener());
        startTime = -1;
        bestTime = 0;
        pack();

    }

    private void initializePanels(){
        initComputerPanel();
        initScorePanel();
        initTitlePanel();
        initUserPanel();
    }

    private void initUserPanel() {
        userPanel = new JPanel();
        userPanel.setOpaque(false);
        userPanel.setLayout(new GridLayout(5,1,5,5));

        for(int i = 0; i< Choices.values().length; i++){
            Choices choice = Choices.values()[i];
            JButton input = new JButton(choice.toString(), getIconForChoice(choice));

            input.addActionListener(new inputButtonListener());
            userPanel.add(input);
        }

        userPanel.setPreferredSize(new Dimension(200, 300));
        userPanel.setBorder(new EmptyBorder(0,20,20,0));
    }

    private void initTitlePanel() {
        JLabel title = new JLabel("Rock Paper Scissors Lizard Spock", JLabel.CENTER);
        title.setBorder(new EmptyBorder(20,20,10,20));
        title.setFont(new Font("Serif", Font.BOLD, 32));

        JLabel subtitle = new JLabel("Test your reaction time! Pick the winning hand!", JLabel.CENTER);
        subtitle.setBorder(new EmptyBorder(0,20,20,20));
        subtitle.setFont(new Font("Serif", Font.ROMAN_BASELINE, 21));

        titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(title,BorderLayout.CENTER);
        titlePanel.add(subtitle,BorderLayout.SOUTH);
    }

    private void initScorePanel() {
        currentScore = new JLabel();
        currentScore.setHorizontalAlignment(JLabel.CENTER);
        updateScores();

        timerLabel = new JLabel("Click any button to start", JLabel.CENTER);
        bestTimesLabel = new JLabel("", JLabel.CENTER);
        updateTimes(0);

        scoreLabel = new JLabel("SCORE", JLabel.CENTER);
        scoreLabel.setFont(new Font("Sans-Serif", Font.BOLD, 15));

        scorePanel = new JPanel();
        scorePanel.setOpaque(false);
        scorePanel.setLayout(new GridLayout(5,1));
        scorePanel.setPreferredSize(new Dimension(300,300));
        scorePanel.setBorder(new EmptyBorder(0,20,0,20));

        scorePanel.add(scoreLabel);
        scorePanel.add(currentScore);
        scorePanel.add(timerLabel);
        scorePanel.add(bestTimesLabel);

        JButton restartBtn = new JButton("Restart");
        restartBtn.addActionListener(new restartActionListener());
        restartBtn.setOpaque(false);
        restartBtn.setBackground(Color.WHITE);
        restartBtn.setBorder(new EmptyBorder(5,5,5,5));
        restartBtn.setForeground(Color.LIGHT_GRAY);
        scorePanel.add(restartBtn);

    }

    private void initComputerPanel() {
        computerChoiceLabel = new JLabel();
        computerChoiceLabel.setHorizontalAlignment(JLabel.CENTER);
        updateComputerChoice();

        computerPanel = new JPanel();
        computerPanel.setOpaque(false);
        computerPanel.setLayout(new BorderLayout());
        computerPanel.add(computerChoiceLabel, BorderLayout.CENTER);
        computerPanel.setPreferredSize(new Dimension(200,300));
        computerPanel.setBorder(new EmptyBorder(0,0,20,20));
    }

    private Icon getIconForChoice(Choices choice) {
        switch (choice){
            case ROCK: return new ImageIcon("res/rock.png");
            case PAPER: return new ImageIcon("res/paper.png");
            case SCISSORS: return new ImageIcon("res/scissors.png");
            case LIZARD: return new ImageIcon("res/lizard.png");
            case SPOCK: return new ImageIcon("res/spock.png");
            default: return new ImageIcon();
        }
    }

    private void addContainer(Component component, int gridx, int gridy, int gridwidth, int gridheight, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridheight = gridheight;
        constraints.gridwidth = gridwidth;
        constraints.fill = fill;
        add(component, constraints);
    }

    private void endGame(){
        this.dispose();
        new RPSLSFrame();
    }

    private class timerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (startTime < 0){
                startTime = System.currentTimeMillis();
            }
            long now = System.currentTimeMillis();
            long clockTime = now-startTime;

            timerLabel.setText(new SimpleDateFormat("mm:ss:SSS").format(clockTime));
        }
    }

    private class restartActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            endGame();
        }
    }

    private class inputButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(timer.isRunning()) {
                JButton button = (JButton) actionEvent.getSource();
                boolean roundWasSuccessful = rpsls.playRound(Choices.valueOf(button.getText()));
                scoreLabel.setText("WRONG!");
                if(roundWasSuccessful) {
                    updateTimes(System.currentTimeMillis());
                    scoreLabel.setText("CORRECT!");
                }
                updateComputerChoice();
                updateScores();
            }
            else
                timer.start();

        }
    }
}
