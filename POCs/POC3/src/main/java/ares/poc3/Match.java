package ares.poc3;

public class Match {
    private final Bot firstBot;
    private final Bot secondBot;

    public Match(Bot firstBot, Bot secondBot) {
        this.firstBot = firstBot;
        this.secondBot = secondBot;
    }

    public Bot getFirstBot() {
        return firstBot;
    }

    public Bot getSecondBot() {
        return secondBot;
    }

    public Bot getWinner() {
        if (firstBot.getNumber() < secondBot.getNumber()) {
            return firstBot;
        }

        return secondBot;
    }
}
