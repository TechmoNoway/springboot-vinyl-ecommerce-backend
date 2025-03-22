package com.trikynguci.springbootvinylecommercebackend.service;

import com.trikynguci.springbootvinylecommercebackend.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(Category category);
}
