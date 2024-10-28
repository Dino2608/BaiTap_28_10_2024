package com.example.thymeleaf.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@NamedQuery(name = "Video.findAll", query = "SELECT v FROM Video v")

public class VideoModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VideoId")

	private String videoid;

	@Column(name = "Active")

	private int active;

	@Column(name = "Description", columnDefinition = "nvarchar(500) null")

	private String description;

	@Column(name = "Poster", columnDefinition = "nvarchar(500) null")

	private String poster;

	@Column(name = "Title", columnDefinition = "nvarchar(500) null")

	private String title;

	@Column(name = "Views")

	private int views;

	// bi-directional many-to-one association to Category

	@ManyToOne

	@JoinColumn(name = "CategoryId")

	private CategoryModel category;

}
