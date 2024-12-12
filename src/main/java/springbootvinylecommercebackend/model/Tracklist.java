package springbootvinylecommercebackend.model;

import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tracklist {
    private Long id;
    private List<Song> songs;
}
