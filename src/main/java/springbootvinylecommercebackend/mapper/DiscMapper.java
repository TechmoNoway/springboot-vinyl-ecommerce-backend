package springbootvinylecommercebackend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Disc;

@Mapper
public interface DiscMapper {
	
	List<Disc> getAllDisc();
		
	List<Disc> getLessDiscByName(@Param("searchParam") String searchParam);
	
	List<Disc> getBestDiscs();
	
	List<Disc> getMoreDiscByName(@Param("searchParam") String searchParam);
	
	List<Disc> getDiscByNameASC(@Param("searchParam") String searchParam);
	
	List<Disc> getDiscByNameDESC(@Param("searchParam") String searchParam);
	
	List<Disc> getDiscByNameFiltered(@Param("searchParam") String searchParam, @Param("categoryName") String categoryName, @Param("moodName") String moodName, @Param("releaseYear") String releaseYear, @Param("stockStatus") String stockStatus);
}
