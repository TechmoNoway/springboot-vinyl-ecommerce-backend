package springbootvinylecommercebackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disc {
	private Long id;
	private String albumName;
	private Long price;
	private String image;
	private String country;
	private String artist;
	private String releaseYear;
	private String status;
	private String platform;
	private String category;
	private String demoAudio;
	private String studio;
	private String manufactureYear;
	private String stockStatus;
	private String description;
	private String mood;
	private Integer quantity;
	private Integer stockQuantity;
}
