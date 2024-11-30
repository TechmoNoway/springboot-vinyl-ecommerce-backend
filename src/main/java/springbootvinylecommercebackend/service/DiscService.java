package springbootvinylecommercebackend.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import springbootvinylecommercebackend.model.Disc;

public interface DiscService {
	
	List<Disc> getAllDisc();
	
	List<Disc> getLessDiscByName(@Param("searchParam") String searchParam);
	
	List<Disc> getBestDiscs();
	
	List<Disc> getMoreDiscByName(@Param("searchParam") String searchParam);
	
	List<Disc> getDiscByNameASC(@Param("searchParam") String searchParam);
	
	List<Disc> getDiscByNameDESC(@Param("searchParam") String searchParam);
	
	List<Disc> getDiscByNameFiltered(String searchParam, String categoryName, String moodName, String releaseYear, String stockStatus);
          
}
