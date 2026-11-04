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

        System.out.println("Scores Elo :");
        for (Bot bot : bots) {
            System.out.println(bot.getId() + " : " + bot.getElo());
        }

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
    }

    private static List<Bot> createBots() {
        List<Bot> bots = new ArrayList<>();

        for (int number = 1; number <= 16; number++) {
            bots.add(new Bot(number));
        }

        return bots;
    }
}
