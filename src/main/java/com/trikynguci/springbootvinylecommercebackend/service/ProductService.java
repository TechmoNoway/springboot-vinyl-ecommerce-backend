package com.trikynguci.springbootvinylecommercebackend.service;

import java.util.List;

import com.trikynguci.springbootvinylecommercebackend.model.Product;

public interface ProductService {
	List<Product> getAllProducts(String title, String category, String platform, String stockStatus, String studioName, String manufactureYear, String status, String sortType, Integer limit, Integer page);
	void addProduct(Product product);
	void deleteProduct(Long id);
}
