package com.mycompany.smartrecipebox;

public abstract class Recipe {
    protected String name;
    protected String[] ingredients;
    protected String instructions;

    public Recipe(String name, String[] ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public abstract void displayRecipe();

    public String getName() {
        return name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public abstract String toString();
}
