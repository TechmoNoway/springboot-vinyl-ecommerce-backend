package springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Product;

@Mapper
public interface ProductMapper {
	
	List<Product> getAllProduct();
		
	List<Product> getLessProductByName(@Param("searchParam") String searchParam);
	
	List<Product> getBestProducts();
	
	List<Product> getMoreProductByName(@Param("searchParam") String searchParam);
	
	List<Product> getProductByNameASC(@Param("searchParam") String searchParam);
	
	List<Product> getProductByNameDESC(@Param("searchParam") String searchParam);
	
	List<Product> getProductByNameFiltered(@Param("searchParam") String searchParam, @Param("categoryName") String categoryName, @Param("moodName") String moodName, @Param("releaseYear") String releaseYear, @Param("stockStatus") String stockStatus);
}
