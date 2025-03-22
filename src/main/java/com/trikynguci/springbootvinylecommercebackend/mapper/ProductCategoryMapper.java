package com.trikynguci.springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.trikynguci.springbootvinylecommercebackend.model.ProductCategory;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {


    List<ProductCategory> getAllCategoriesByProductId(Long productId);


}
