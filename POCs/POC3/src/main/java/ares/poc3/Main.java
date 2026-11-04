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
            System.out.println(match.getFirstBot().getId() + " contre "
                    + match.getSecondBot().getId() + " : "
                    + match.getWinner().getId() + " gagne");
        }
    }
}
