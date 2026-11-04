package ares.poc3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Bot> bots = new ArrayList<>();

        for (int number = 1; number <= 16; number++) {
            bots.add(new Bot(number));
        }

        List<Match> matches = RoundRobin.generateMatches(bots);

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
    }
}
