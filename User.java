package com.mycompany.groupproject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class User {
    
    public static final String FILE = "users.txt";
    
    // Create empty HashMap for Key-Value pairs
    public Map<String, String[]> users = new HashMap<>();
    
    // Constructor
    public User() {
        load();
    }
    
    // u = username, p = password, target = target calorie
    public boolean register(String u, String p, int target) {
        // If username already exists, return false
        if (users.containsKey(u)) return false;
        
        // Puts user, password, and target calorie(converted into String value) into hashmap
        // e.g.: "Ahmad", new String[]{"ahmadpassword","2500"}
        users.put(u, new String[]{p, String.valueOf(target)});
        // Runs save() to save hashmap pair into users.txt and returns true
        save();
        return true;
    }
    
    // Returns true if user and password exists in hashmap and false if they don't exist
    public boolean login(String u, String p) {
        return users.containsKey(u) && users.get(u)[0].equals(p);
    }
    
    // Get method that receives user name and returns target calorie which is stored in hashmap
    public int getTarget(String u) {
        // e.g.: "Ahmad" -> {"ahmadpassword", "2500"}
        // users.get(u) returns the array, so the index [1] only returns the target calorie
        // Also converts the String form of target calorie into integer
        return Integer.parseInt(users.get(u)[1]);
    }
    
    // Method to save contents in hashmap into text file
    public void save() {
        try (PrintWriter w = new PrintWriter(new FileWriter(FILE))) {
            // Loops through all users in hashmap
            for (var e : users.entrySet()) {
                // Stores in format "Ahmad,ahmadpassword,2500" for each line
                w.println(e.getKey() + "," + e.getValue()[0] + "," + e.getValue()[1]);
            }
        }
        // If file not found, ignore silently
        catch (Exception ignored) {}
    }
    
    public void load() {
        // Stops loading if users.txt doesn't exist
        File f = new File(FILE);
        if (!f.exists()) return;
        
        // Read File
        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            String line;
            // Reads text file until no more lines are left
            while ((line = r.readLine()) != null) {
                // Stores string values from the line into array, separated by commas
                String[] p = line.split(",");
                // If all values are available, store into hashmap
                if (p.length == 3) {
                    users.put(p[0], new String[]{p[1], p[2]});
                }
            }
        }
        // If error occurs, ignore silently
        catch (Exception ignored) {}
    }
}