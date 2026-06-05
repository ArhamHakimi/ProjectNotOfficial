package com.mycompany.groupproject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CalorieTracker {
    
    // Create ArrayLists to store list of foods and drinks
    public List<FoodItem> foods = new ArrayList<>();
    public List<DrinkItem> drinks = new ArrayList<>();
    // Stores username
    public String username;
    
    // Constructor
    public CalorieTracker(String username) {
        this.username = username;
        // Load data from text file into ArrayLists
        load();
    }
    
    // Get method to get the proper file name format
    public String getFileName() {
        return "calories_" + username + ".txt";
    }
    
    // Adds food item and calorie into "foods" ArrayList when called
    public void addFood(FoodItem f) {
        foods.add(f);
        // Saves data into text file
        save();
    }
    
    // Same logic as above
    public void addDrink(DrinkItem d) {
        drinks.add(d);
        save();
    }
    
    // Get method for both ArrayLists
    public List<FoodItem> getFoods() { return foods; }
    public List<DrinkItem> getDrinks() { return drinks; }
    
    // Counts total calories
    public int totalCalories() {
        int total = 0;
        
        // Loops through each item in both ArrayLists and add up the calories into "total"
        for (FoodItem f : foods) total += f.getCalories();
        for (DrinkItem d : drinks) total += d.getCalories();
        
        return total;
    }
    
    // Saves data into text file
    public void save() {
        try (PrintWriter w = new PrintWriter(new FileWriter(getFileName()))) {
            // Loop through each item in both ArrayLists and saves them into the text file
            //Format: "(F/D),ItemName,CalorieValue"
            for (FoodItem f : foods)
                w.println("F," + f.getName() + "," + f.getCalories());
            
            for (DrinkItem d : drinks)
                w.println("D," + d.getName() + "," + d.getCalories());
            
        }
        // If saving error occurs, show error message
        catch (Exception e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }
    
    // Load data from text file into ArrayLists
    public void load() {
        // Stops loading if file doesn't exist
        File f = new File(getFileName());
        if (!f.exists()) return;
        
        // Read file
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            
            // Read text file until no more lines are left
            while ((line = r.readLine()) != null) {
                // Stores string values from the line into array, separated by commas
                String[] p = line.split(",");
                
                if (p.length != 3) continue; // Stops here and repeats loop if data format is not correct
                
                // Store item name and calorie into ArrayLists
                if (p[0].equals("F"))
                    foods.add(new FoodItem(p[1], Integer.parseInt(p[2])));
                else
                    drinks.add(new DrinkItem(p[1], Integer.parseInt(p[2])));
            }
        }
        // // If loading error occurs, show error message
        catch (Exception e) {
            System.out.println("Load error: " + e.getMessage());
        }
    }
}