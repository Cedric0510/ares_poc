package ares.poc3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Bot> bots = new ArrayList<>();

        for (int number = 1; number <= 16; number++) {
            bots.add(new Bot("B" + number));
        }

        for (Bot bot : bots) {
            System.out.println(bot.getId() + " : " + bot.getElo());
        }
    }
}
