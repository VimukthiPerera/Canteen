package com.rome.canteen.controller;

import com.rome.canteen.model.FoodItem;
import com.rome.canteen.service.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;


import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/fooditems")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    // GET all food items
    @GetMapping
    public List<FoodItem> getAllFoodItems() {
        return foodItemService.getAllFoodItems();
    }

    // GET a specific food item by ID
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable String id) {
        Optional<FoodItem> foodItem = foodItemService.getFoodItemById(id);
        if (foodItem.isPresent()) {
            return ResponseEntity.ok(foodItem.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST a new food item

    @PostMapping("/add")
    public ResponseEntity<String> addFoodItem(@RequestParam("name") String name,
                                              @RequestParam("description") String description,
                                              @RequestParam("price") double price,
                                              @RequestParam("availability") String availability,
                                              @RequestParam("mealType") String mealType,
                                              @RequestParam("image") MultipartFile image) {
        try {
            // Convert image to Base64 string
            String imageBase64 = Base64.getEncoder().encodeToString(image.getBytes());

            // Create new FoodItem with the correct constructor
            FoodItem foodItem = new FoodItem(null, name, description, price, availability, mealType, imageBase64);
            foodItemService.save(foodItem);

            return ResponseEntity.ok("Food item added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }


    // DELETE a food item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable String id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.noContent().build();
    }
}