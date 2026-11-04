package ares.poc3;

public class Bot {
    private final String id;
    private double elo;

    public Bot(String id) {
        this.id = id;
        this.elo = 1000;
    }

    public String getId() {
        return id;
    }

    public double getElo() {
        return elo;
    }
}
