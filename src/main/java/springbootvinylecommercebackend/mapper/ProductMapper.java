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

}
