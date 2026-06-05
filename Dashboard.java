package com.mycompany.groupproject;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class Dashboard {
    
    public CalorieTracker tracker;
    public String username;
    public int target;
    
    // Constructor
    public Dashboard(String username, int target) {
        this.username = username;
        this.target = target;
        this.tracker = new CalorieTracker(username);
    }
    
    // Dashboard
    public Scene getScene() {
        
        // Dashboard UI
        Label userLabel = new Label("User: " + username);
        Label title = new Label("Calorie Tracker");
        // Total Calories
        Label total = new Label();
        // Status message for if total calorie is under, exactly at or exceed target calorie
        Label status = new Label();
        // To display entire list of items with their respective calories
        ListView<String> list = new ListView<>();
        
        // Add food and drink items with their respective calories
        TextField food = new TextField();
        food.setPromptText("Food name");
        
        TextField foodCal = new TextField();
        foodCal.setPromptText("Calories");
        
        TextField drink = new TextField();
        drink.setPromptText("Drink name");
        
        TextField drinkCal = new TextField();
        drinkCal.setPromptText("Calories");
        
        Button addFood = new Button("Add Food");
        Button addDrink = new Button("Add Drink");
        Button deleteBtn = new Button("Delete Selected");
        
        // Loads existing data from text file if it exists
        refresh(list, total, status);
        
        // When "Add Food" is pressed
        addFood.setOnAction(e -> {
            try {
                // Adds food item into ArrayList and text file
                tracker.addFood(new FoodItem(
                        food.getText(),
                        Integer.parseInt(foodCal.getText())
                ));
                
                // Clears input box after button pressed
                food.clear();
                foodCal.clear();
                // Updates item list after new food is added
                refresh(list, total, status);
                
            }
            // If invalid input, shows error message
            catch (Exception ex) {
                status.setText("Invalid food input!");
            }
        });
        
        // When "Add Drink" is pressed
        addDrink.setOnAction(e -> {
            try {
                // Adds drink item into ArrayList and text file
                tracker.addDrink(new DrinkItem(
                        drink.getText(),
                        // Converts integer String value into int
                        Integer.parseInt(drinkCal.getText())
                ));
                
                // Clears input box after button pressed
                drink.clear();
                drinkCal.clear();
                // Updates item list after new food is added
                refresh(list, total, status);
                
            }
            // If invalid input, shows error message
            catch (Exception ex) {
                status.setText("Invalid drink input!");
            }
        });
        
        // When "Delete Selected" is pressed
        deleteBtn.setOnAction(e -> {
            
            // Variable to store the selected item from the list
            String selected = list.getSelectionModel().getSelectedItem();
            // If nothing is selected, ignore entire block
            if (selected == null) return;
            
            // Remove food or drink item from list
            if (selected.startsWith("Food:")) {
                // Extract only the food item from the text display
                // e.g. "Food: Rice - 400"
                // Splits into "Food: Rice" and "400" and takes index [0] which is only "Food: Rice"
                // Removes "Food: " by replacing with ""
                // Now variable 'name' has only "Rice"
                String name = selected.split(" - ")[0].replace("Food: ", "");
                // Removes the item from the ArrayList
                tracker.getFoods().removeIf(f -> f.getName().equals(name));
                
            }
            // Same logic as above
            else if (selected.startsWith("Drink:")) {
                String name = selected.split(" - ")[0].replace("Drink: ", "");
                tracker.getDrinks().removeIf(d -> d.getName().equals(name));
            }
            
            // Ensure update process of file
            trackerSaveFix();
            // Updates item list again after deletion
            refresh(list, total, status);
        });
        
        // Window Layout
        VBox root = new VBox(10,
                userLabel,
                title,
                food, foodCal, addFood,
                drink, drinkCal, addDrink,
                deleteBtn,
                list,
                total,
                status
        );
        
        // Dashboard Window
        return new Scene(root, 500, 500);
    }
    
    // Updates everything in Dashboard display
    public void refresh(ListView<String> list, Label totalLabel, Label status) {
        
        // Removes old entries
        list.getItems().clear();
        
        // Loop through each item in the food ArrayList
        for (FoodItem f : tracker.getFoods())
            // Adds food item and calorie into the list
            // Format: "Food: FoodName - CalorieAmount"
            list.getItems().add("Food: " + f.getName() + " - " + f.getCalories());
        
        // Same logic as above
        for (DrinkItem d : tracker.getDrinks())
            list.getItems().add("Drink: " + d.getName() + " - " + d.getCalories());
        
        // Get total calories from ArrayLists
        int total = tracker.totalCalories();
        
        // Shows total calorie and target calorie
        totalLabel.setText("Total: " + total + " / Target: " + target);
        
        // Show different message regarding total calorie and target calorie comparison
        if (total > target) {
            status.setText("⚠ You exceeded your calorie target!");
        }
        else if (total < target) {
            status.setText("✔ You are under your target.");
        }
        else {
            status.setText("✔ You hit your target exactly!");
        }
    }
    
    // Method to make sure file is updated
    public void trackerSaveFix() {
        try {
            java.lang.reflect.Method m = tracker.getClass().getDeclaredMethod("save");
            m.setAccessible(true);
            m.invoke(tracker);
        }
        catch (Exception ignored) {}
    }
}