package ares.poc1;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Path file = Path.of("poc1-init.json");

        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            GameState state = new Gson().fromJson(reader, GameState.class);
            System.err.println("Etat initial charge : " + state.getRobots().size() + " robots");
        }
    }
}
