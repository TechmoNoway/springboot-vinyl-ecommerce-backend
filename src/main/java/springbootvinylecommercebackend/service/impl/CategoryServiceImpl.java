package springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import springbootvinylecommercebackend.mapper.CategoryMapper;
import springbootvinylecommercebackend.model.Category;
import springbootvinylecommercebackend.service.CategoryService;

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
