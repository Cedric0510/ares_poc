package ares.poc3;

public class Elo {
    private static final int K = 32;

    public static void updateScores(Match match) {
        Bot firstBot = match.getFirstBot();
        Bot secondBot = match.getSecondBot();

        double firstScore = firstBot.getElo();
        double secondScore = secondBot.getElo();

        double expectedFirst = 1 / (1 + Math.pow(10, (secondScore - firstScore) / 400));
        double expectedSecond = 1 - expectedFirst;

        double resultFirst = 0;
        if (match.getWinner() == firstBot) {
            resultFirst = 1;
        }
        double resultSecond = 1 - resultFirst;

        firstBot.setElo(firstScore + K * (resultFirst - expectedFirst));
        secondBot.setElo(secondScore + K * (resultSecond - expectedSecond));
    }
}
