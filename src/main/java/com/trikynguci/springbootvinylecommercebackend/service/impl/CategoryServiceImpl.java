package com.trikynguci.springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.trikynguci.springbootvinylecommercebackend.mapper.CategoryMapper;
import com.trikynguci.springbootvinylecommercebackend.model.Category;
import com.trikynguci.springbootvinylecommercebackend.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    @Cacheable(value = "categories", key = "'allCategories'")
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @Override
    public void addCategory(Category category) {

    }
}
