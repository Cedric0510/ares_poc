package ares.poc3;

public class Bot {
    private final int number;
    private double elo;

    public Bot(int number) {
        this.number = number;
        this.elo = 1000;
    }

    public String getId() {
        return "B" + number;
    }

    public int getNumber() {
        return number;
    }

    public double getElo() {
        return elo;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }
}
