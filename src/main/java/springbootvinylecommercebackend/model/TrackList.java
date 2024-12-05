package springbootvinylecommercebackend.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrackList {
    private Long id;
    private Long vinylId;
    private List<Song> songs;
}
