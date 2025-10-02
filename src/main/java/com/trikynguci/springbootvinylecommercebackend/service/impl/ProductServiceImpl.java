package com.trikynguci.springbootvinylecommercebackend.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.trikynguci.springbootvinylecommercebackend.mapper.ProductMapper;
import com.trikynguci.springbootvinylecommercebackend.model.Product;
import com.trikynguci.springbootvinylecommercebackend.service.ProductService;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public List<Product> getAllProducts(String title, String category, String platform, String stockStatus, String studioName, String manufactureYear, String status, String sortType, Integer limit, Integer page) {
        if (title == null) title = "";
        if (category == null) category = "";
        if (platform == null) platform = "";
        if (stockStatus == null) stockStatus = "";
        if (studioName == null) studioName = "";
        if (manufactureYear == null) manufactureYear = "";
        if (status == null) status = "";
        if (sortType == null) sortType = "DEFAULT";
        if (limit == null || limit <= 0) limit = 20;
        if (page == null || page <= 0) page = 1;
        int offset = (page - 1) * limit;
        return productMapper.getAllProducts(title, category, platform, stockStatus, studioName, manufactureYear, status, sortType, limit, offset);
    }

    @Override
    public void addProduct(Product product) {

    }

    @Override
    public void deleteProduct(Long id) {

    }


}
