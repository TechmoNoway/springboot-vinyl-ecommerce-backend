package com.trikynguci.springbootvinylecommercebackend.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.trikynguci.springbootvinylecommercebackend.model.Product;

public interface ProductService {
	
	List<Product> getAllProduct();
	
	List<Product> getReadyProducts();

	Product getProductByTitle(String title);

	List<Product> searchProductsByTitle(@Param("searchParam") String searchParam);

	List<Product> getAllProductsFilteredAndSorted(String title, String category, String platform, String stockStatus, String studioName, String manufactureYear, String status, String sortType);

	void addProduct(Product product);

	void deleteProduct(Long id);

}
