package com.trikynguci.springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.trikynguci.springbootvinylecommercebackend.model.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> getAllCategories();

}
