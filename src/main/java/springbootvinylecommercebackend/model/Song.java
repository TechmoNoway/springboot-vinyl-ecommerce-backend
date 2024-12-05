package springbootvinylecommercebackend.model;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Song {
    private Long id;
    private Long trackListId;
    private String title;
    private int trackNumber;
}
