package com.mycompany.smartrecipebox;

import java.io.*;

public class FileManager {
    private static final String FILE_NAME = "recipes01.txt";

    public static void saveRecipe(Recipe recipe) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(recipe.toString());
            writer.write("--------------------------------------------------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadRecipes() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            sb.append("No saved recipes found.\n");
        }
        return sb.toString();
    }
}
