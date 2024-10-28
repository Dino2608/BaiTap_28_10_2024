package com.example.thymeleaf.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.thymeleaf.entity.Category;
import com.example.thymeleaf.reponsitory.CategoryReponsitory;

import io.micrometer.common.util.StringUtils;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryReponsitory categoryReponsitory;

	public CategoryServiceImpl(CategoryReponsitory categoryReponsitory) {
		super();
		this.categoryReponsitory = categoryReponsitory;
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryReponsitory.findAll(pageable);
	}

	@Override
	public <S extends Category> S save(S entity) {
		if (entity.getCategoryid() == null) {
			return categoryReponsitory.save(entity);
		} else {
			Optional<Category> optional = findById(entity.getCategoryid());
			if (optional.isPresent()) {
				if (StringUtils.isEmpty(entity.getCategoryname())) {
					entity.setCategoryname(optional.get().getCategoryname());
					;
				} else
					entity.setCategoryname(entity.getCategoryname());
			}
		}
		return categoryReponsitory.save(entity);
	}

	@Override
	public List<Category> findAll() {
		return categoryReponsitory.findAll();
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryReponsitory.findById(id);
	}

	@Override
	public long count() {
		return categoryReponsitory.count();
	}

	@Override
	public void deleteById(Integer id) {
		categoryReponsitory.deleteById(id);
	}

	@Override
	public List<Category> findByCategoryname(String categoryname) {
		return categoryReponsitory.findByCategoryname(categoryname);
	}

	@Override
	public Page<Category> findByCategorynameContaining(String categoryname, Pageable pageable) {
		return categoryReponsitory.findByCategorynameContaining(categoryname, pageable);
	}

}
