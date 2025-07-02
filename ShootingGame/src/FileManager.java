import java.io.*;
import java.nio.file.*;

public class FileManager {
    public static void saveHighScore(int score) {
        Path path = Paths.get("highscore.txt");
        try {
            Files.write(path, String.valueOf(score).getBytes());
        } catch (IOException e) {
            System.err.println("Error saving high score: " + e.getMessage());
        }
    }

    public static int loadHighScore() {
        Path path = Paths.get("highscore.txt");
        if (!Files.exists(path)) return 0;

        try {
            String content = Files.readString(path);
            return Integer.parseInt(content.trim());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }
}