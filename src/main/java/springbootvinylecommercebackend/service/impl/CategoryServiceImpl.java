package springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;
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
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @Override
    public void addCategory(Category category) {

    }
}
