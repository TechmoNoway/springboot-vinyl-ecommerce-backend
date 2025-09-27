package com.trikynguci.springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.trikynguci.springbootvinylecommercebackend.model.Product;

@Mapper
public interface ProductMapper {
	List<Product> getAllProducts(
		@Param("title") String title,
		@Param("category") String category,
		@Param("platform") String platform,
		@Param("stockStatus") String stockStatus,
		@Param("studioName") String studioName,
		@Param("manufactureYear") String manufactureYear,
		@Param("status") String status,
		@Param("sortType") String sortType,
		@Param("limit") Integer limit,
		@Param("offset") Integer offset
	);

	Product getProductById(@Param("id") Long id);

	Product getProductForUpdate(@Param("id") Long id);

	int decrementStockQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
}
