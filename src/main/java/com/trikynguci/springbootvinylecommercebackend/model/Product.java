package com.trikynguci.springbootvinylecommercebackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String title;
	private Long price;
	private Long stockQuantity;
	private String posterUrl;
	private String region;
	private String artist;
	private String releaseYear;
	private String status;
	private String platform;
	private String set;
	private String demoAudioUrl;
	private String studioName;
	private String manufactureYear;
	private String stockStatus;
	private String description;
	private String mood;
	private Instant createdAt;
	private Instant updatedAt;
	private Long tracklistId;
	private List<Category> categories;
	// optimistic locking version
	private Long version;
}
