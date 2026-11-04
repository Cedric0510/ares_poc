package ares.poc3;

import java.util.ArrayList;
import java.util.List;

public class SingleElimination {
    public static List<Match> generateMatches(List<Bot> bots) {
        List<Match> matches = new ArrayList<>();
        List<Bot> remainingBots = bots;

        while (remainingBots.size() > 1) {
            List<Bot> winners = new ArrayList<>();

            for (int index = 0; index < remainingBots.size(); index += 2) {
                Match match = new Match(remainingBots.get(index), remainingBots.get(index + 1));
                matches.add(match);
                winners.add(match.getWinner());
            }

            remainingBots = winners;
        }

        return matches;
    }
}
