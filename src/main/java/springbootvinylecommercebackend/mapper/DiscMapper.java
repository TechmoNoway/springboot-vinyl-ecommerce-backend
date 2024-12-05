package springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Product;

@Mapper
public interface DiscMapper {
	
	List<Product> getAllDisc();
		
	List<Product> getLessDiscByName(@Param("searchParam") String searchParam);
	
	List<Product> getBestDiscs();
	
	List<Product> getMoreDiscByName(@Param("searchParam") String searchParam);
	
	List<Product> getDiscByNameASC(@Param("searchParam") String searchParam);
	
	List<Product> getDiscByNameDESC(@Param("searchParam") String searchParam);
	
	List<Product> getDiscByNameFiltered(@Param("searchParam") String searchParam, @Param("categoryName") String categoryName, @Param("moodName") String moodName, @Param("releaseYear") String releaseYear, @Param("stockStatus") String stockStatus);
}
