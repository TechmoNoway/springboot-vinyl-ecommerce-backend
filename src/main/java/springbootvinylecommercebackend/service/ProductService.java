package springbootvinylecommercebackend.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Product;

public interface ProductService {
	
	List<Product> getAllProduct();
	
	List<Product> getReadyProducts();

	Product getProductByTitle(@Param("title") String title);

	List<Product> getLessProductByName(@Param("searchParam") String searchParam);


	List<Product> getMoreProductByName(@Param("searchParam") String searchParam);
	
	List<Product> getProductByNameASC(@Param("searchParam") String searchParam);
	
	List<Product> getProductByNameDESC(@Param("searchParam") String searchParam);
	
	List<Product> getProductByNameFiltered(String searchParam, String categoryName, String moodName, String releaseYear, String stockStatus);
          
}
