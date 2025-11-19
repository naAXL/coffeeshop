package com.service;

import com.dto.*;
import com.entity.*;
import com.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final CategoryRepository categoryRepo;
    private final CategoryTranslationRepository categoryTransRepo;
    private final MenuItemRepository menuItemRepo;
    private final MenuItemTranslationRepository menuItemTransRepo;

    public List<CategoryDTO> getAllCategories() {
        return categoryRepo.findAll().stream()
                .map(this::convertToCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepo.findById(id)
                .map(this::convertToCategoryDTO)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Transactional
    public CategoryDTO createCategory(CategoryRequest req) {
        Category cat = new Category();
        cat.setImageUrl(req.getImageUrl());
        cat = categoryRepo.save(cat);

        for (Map.Entry<String, String> e : req.getNames().entrySet()) {
            CategoryTranslation t = new CategoryTranslation();
            t.setCategory(cat);
            t.setLanguage(e.getKey());
            t.setName(e.getValue());
            categoryTransRepo.save(t);
        }

        return getCategoryById(cat.getId());
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryRequest req) {
        Category cat = categoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        cat.setImageUrl(req.getImageUrl());
        categoryRepo.save(cat);

        categoryTransRepo.deleteAll(categoryTransRepo.findByCategoryId(id));

        for (Map.Entry<String, String> e : req.getNames().entrySet()) {
            CategoryTranslation t = new CategoryTranslation();
            t.setCategory(cat);
            t.setLanguage(e.getKey());
            t.setName(e.getValue());
            categoryTransRepo.save(t);
        }

        return getCategoryById(id);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }

    public List<MenuItemDTO> getItemsByCategory(Long categoryId) {
        return menuItemRepo.findByCategoryId(categoryId).stream()
                .map(this::convertToMenuItemDTO)
                .collect(Collectors.toList());
    }

    public MenuItemDTO getItemById(Long id) {
        return menuItemRepo.findById(id)
                .map(this::convertToMenuItemDTO)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @Transactional
    public MenuItemDTO createMenuItem(MenuItemRequest req) {
        Category cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        MenuItem item = new MenuItem();
        item.setCategory(cat);
        item.setImageUrl(req.getImageUrl());
        item.setPrice(req.getPrice());
        item = menuItemRepo.save(item);

        for (Map.Entry<String, String> e : req.getNames().entrySet()) {
            MenuItemTranslation t = new MenuItemTranslation();
            t.setMenuItem(item);
            t.setLanguage(e.getKey());
            t.setName(e.getValue());
            t.setDescription(req.getDescriptions().get(e.getKey()));
            menuItemTransRepo.save(t);
        }

        return getItemById(item.getId());
    }

    @Transactional
    public MenuItemDTO updateMenuItem(Long id, MenuItemRequest req) {
        MenuItem item = menuItemRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Category cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        item.setCategory(cat);
        item.setImageUrl(req.getImageUrl());
        item.setPrice(req.getPrice());
        menuItemRepo.save(item);

        menuItemTransRepo.deleteAll(menuItemTransRepo.findByMenuItemId(id));

        for (Map.Entry<String, String> e : req.getNames().entrySet()) {
            MenuItemTranslation t = new MenuItemTranslation();
            t.setMenuItem(item);
            t.setLanguage(e.getKey());
            t.setName(e.getValue());
            t.setDescription(req.getDescriptions().get(e.getKey()));
            menuItemTransRepo.save(t);
        }

        return getItemById(id);
    }

    @Transactional
    public void deleteMenuItem(Long id) {
        menuItemRepo.deleteById(id);
    }

    private CategoryDTO convertToCategoryDTO(Category cat) {
        Map<String, String> names = categoryTransRepo.findByCategoryId(cat.getId())
                .stream()
                .collect(Collectors.toMap(
                        CategoryTranslation::getLanguage,
                        CategoryTranslation::getName
                ));
        return new CategoryDTO(cat.getId(), cat.getImageUrl(), names);
    }

    private MenuItemDTO convertToMenuItemDTO(MenuItem item) {
        List<MenuItemTranslation> trans = menuItemTransRepo.findByMenuItemId(item.getId());

        Map<String, String> names = trans.stream()
                .collect(Collectors.toMap(
                        MenuItemTranslation::getLanguage,
                        MenuItemTranslation::getName
                ));

        Map<String, String> descs = trans.stream()
                .collect(Collectors.toMap(
                        MenuItemTranslation::getLanguage,
                        t -> t.getDescription() != null ? t.getDescription() : ""
                ));

        return new MenuItemDTO(
                item.getId(),
                item.getCategory().getId(),
                item.getImageUrl(),
                item.getPrice(),
                names,
                descs
        );
    }
}