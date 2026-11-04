package ares.poc3;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin {
    public static List<Match> generateMatches(List<Bot> bots) {
        List<Match> matches = new ArrayList<>();

        for (int first = 0; first < bots.size(); first++) {
            for (int second = first + 1; second < bots.size(); second++) {
                matches.add(new Match(bots.get(first), bots.get(second)));
            }
        }

        return matches;
    }
}
