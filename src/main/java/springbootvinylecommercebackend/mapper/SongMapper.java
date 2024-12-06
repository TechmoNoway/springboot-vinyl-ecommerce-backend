package springbootvinylecommercebackend.mapper;

import org.apache.ibatis.annotations.Mapper;
import springbootvinylecommercebackend.model.Song;

import java.util.List;

@Mapper
public interface SongMapper {
    List<Song> findByTracklistId(Long tracklistId);
    void insert(Song song);
    void deleteByTracklistId(Long tracklistId);
}
