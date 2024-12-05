package springbootvinylecommercebackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private long id;
	private String title;
	private long price;
	private String posterUrl;
	private String region;
	private String artist;
	private String releaseYear;
	private String status;
	private String platform;
	private String category;
	private String demoAudioUrl;
	private String studioName;
	private String manufactureYear;
	private String stockStatus;
	private String description;
	private String mood;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private TrackList trackList;
}
