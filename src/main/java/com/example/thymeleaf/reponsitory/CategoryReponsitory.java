package com.example.thymeleaf.reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.thymeleaf.entity.Category;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category, Integer> {
	List<Category> findByCategoryname(String categoryname);

	Page<Category> findByCategorynameContaining(String categoryname, Pageable pageable);
}
