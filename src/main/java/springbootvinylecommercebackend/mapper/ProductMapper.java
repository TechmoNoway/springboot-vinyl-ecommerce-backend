package springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Product;

@Mapper
public interface ProductMapper {
	
	List<Product> getAllProducts();
		
	List<Product> getReadyProducts();

	Product getProductByTitle(@Param("title") String title);

	List<Product> searchProductsByTitle(@Param("searchParam") String searchParam);

	List<Product> getAllProductsFilteredAndSorted(@Param("title") String title,
													@Param("category") String category,
													@Param("platform") String platform,
													@Param("stockStatus") String stockStatus,
													@Param("studioName") String studioName,
													@Param("manufactureYear") String manufactureYear,
													@Param("status") String status,
													@Param("sortType") String sortType);

	List<Product> getMoreProductsByName(@Param("searchParam") String searchParam);
	
	List<Product> getProductsByNameASC(@Param("searchParam") String searchParam);
	
	List<Product> getProductsByNameDESC(@Param("searchParam") String searchParam);
	
	List<Product> getProductsByNameFiltered(@Param("searchParam") String searchParam, @Param("categoryName") String categoryName, @Param("moodName") String moodName, @Param("releaseYear") String releaseYear, @Param("stockStatus") String stockStatus);
}
