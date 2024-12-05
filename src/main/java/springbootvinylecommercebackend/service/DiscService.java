package springbootvinylecommercebackend.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Product;

public interface DiscService {
	
	List<Product> getAllDisc();
	
	List<Product> getLessDiscByName(@Param("searchParam") String searchParam);
	
	List<Product> getBestDiscs();
	
	List<Product> getMoreDiscByName(@Param("searchParam") String searchParam);
	
	List<Product> getDiscByNameASC(@Param("searchParam") String searchParam);
	
	List<Product> getDiscByNameDESC(@Param("searchParam") String searchParam);
	
	List<Product> getDiscByNameFiltered(String searchParam, String categoryName, String moodName, String releaseYear, String stockStatus);
          
}
