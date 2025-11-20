package com.controller;

import com.dto.*;
import com.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService service;

    @GetMapping("/categories")
    public List<CategoryDTO> getCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/categories/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        return service.getCategoryById(id);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/categories")
    public CategoryDTO createCategory(@RequestBody CategoryRequest req) {
        return service.createCategory(req);
    }

    @PutMapping("/categories/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryRequest req) {
        return service.updateCategory(id, req);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories/{categoryId}/items")
    public List<MenuItemDTO> getCategoryItems(@PathVariable Long categoryId) {
        return service.getItemsByCategory(categoryId);
    }

    @GetMapping("/items/{id}")
    public MenuItemDTO getItem(@PathVariable Long id) {
        return service.getItemById(id);
    }

    @PostMapping("/items")
    public MenuItemDTO createItem(@RequestBody MenuItemRequest req) {
        return service.createMenuItem(req);
    }

    @PutMapping("/items/{id}")
    public MenuItemDTO updateItem(@PathVariable Long id, @RequestBody MenuItemRequest req) {
        return service.updateMenuItem(id, req);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        service.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }


}
