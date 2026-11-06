package ares.poc3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Bot> bots = createBots();
        List<Match> matches = RoundRobin.generateMatches(bots);

        System.out.println("Round-robin :");
        for (Match match : matches) {
            Elo.updateScores(match);
            System.out.println(match.getFirstBot().getId() + " contre "
                    + match.getSecondBot().getId() + " : "
                    + match.getWinner().getId() + " gagne");
        }

        printRanking("Classement round-robin :", bots);

        List<Bot> eliminationBots = createBots();
        List<Match> eliminationMatches = SingleElimination.generateMatches(eliminationBots);

        System.out.println("Elimination directe :");
        for (Match match : eliminationMatches) {
            Elo.updateScores(match);
            System.out.println(match.getFirstBot().getId() + " contre "
                    + match.getSecondBot().getId() + " : "
                    + match.getWinner().getId() + " gagne");
        }

        Match finalMatch = eliminationMatches.get(eliminationMatches.size() - 1);
        System.out.println("Vainqueur : " + finalMatch.getWinner().getId());
        printRanking("Classement elimination directe :", eliminationBots);
    }

    private static List<Bot> createBots() {
        List<Bot> bots = new ArrayList<>();

        for (int number = 1; number <= 16; number++) {
            bots.add(new Bot(number));
        }

        return bots;
    }

    private static void printRanking(String title, List<Bot> bots) {
        bots.sort((firstBot, secondBot) -> Double.compare(secondBot.getElo(), firstBot.getElo()));

        System.out.println(title);
        for (int index = 0; index < bots.size(); index++) {
            Bot bot = bots.get(index);
            System.out.println("#" + (index + 1) + " " + bot.getId() + " " + Math.round(bot.getElo()));
        }
    }
}
