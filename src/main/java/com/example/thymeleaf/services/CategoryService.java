package com.example.thymeleaf.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.thymeleaf.entity.Category;

public interface CategoryService {

	List<Category> findByCategoryname(String categoryname);

	void deleteById(Integer id);

	long count();

	Optional<Category> findById(Integer id);

	List<Category> findAll();

	<S extends Category> S save(S entity);

	Page<Category> findByCategorynameContaining(String categoryname, Pageable pageable);

	Page<Category> findAll(Pageable pageable);
}
