package com.mycompany.groupproject;

public class FoodItem {
    
    public String name;
    public int calories;
    
    //Construtor
    public FoodItem(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }
    
    // Get method for item name
    public String getName() {
        return name;
    }
    
    // Get method for item calories
    public int getCalories() {
        return calories;
    }
}