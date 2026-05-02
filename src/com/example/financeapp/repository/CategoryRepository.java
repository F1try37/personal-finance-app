package com.example.financeapp.repository;

import com.example.financeapp.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {
    private final List<Category> categories = new ArrayList<>();
    private int nextId = 0;

    public CategoryRepository() {
        categories.add(new Category(nextId, "Транспорт"));
        categories.add(new Category(nextId++, "Супермаркеты"));
        categories.add(new Category(nextId++, "Такси"));
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void addCategory(String name) {
        Category category = new Category(nextId++, name);
        categories.add(category);
    }

    public Category findByName(String name) {
        for (Category c: categories) {
            if (name.trim().equalsIgnoreCase(c.getName())) {
                return c;
            }
        }
        return null;
    }

    public Category findById(String name) {
        for (Category c: categories) {
            while (true) {
                try {
                    if (Integer.parseInt(name) == c.getId()) {
                        return c;
                    }
                } catch (Exception e) {
                    System.out.println("Ошибка, попробуйте ещё раз.");
                }
            }
        }
        return null;
    }
}
