package com.example.thymeleaf.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class CategoryModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CategoryId")
	private int categoryid;

	@Column(name = "CategoryName", columnDefinition = "nvarchar(50) not null")
	@NotEmpty(message = "Không được phép rỗng")
	private String categoryname;

	@Column(name = "Images", columnDefinition = "nvarchar(50) null")
	private String images;
	private int status;

	@OneToMany(mappedBy = "category")

	private List<VideoModel> videos;

	public VideoModel addVideo(VideoModel video) {

		getVideos().add(video);

		video.setCategory(this);

		return video;

	}

	public VideoModel removeVideo(VideoModel video) {

		getVideos().remove(video);

		video.setCategory(null);

		return video;

	}

	private Boolean isEdit = false;
}