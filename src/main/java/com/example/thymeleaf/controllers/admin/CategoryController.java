package com.example.thymeleaf.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.thymeleaf.entity.Category;
import com.example.thymeleaf.model.CategoryModel;
import com.example.thymeleaf.services.CategoryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("")
	public String list(Model model) {
		List<Category> listcate = categoryService.findAll();
		model.addAttribute("listcate", listcate);
		return "admin/categories/list";
	}

	@GetMapping("add")
	public String add(ModelMap model) {
		CategoryModel categoryModel = new CategoryModel();
		categoryModel.setIsEdit(false);
		model.addAttribute("category", categoryModel);
		return "admin/categories/edit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryModel categoryModel,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/edit");
		}
		Category entity = new Category();
		BeanUtils.copyProperties(categoryModel, entity);
		categoryService.save(entity);
		String message = "";
		if (categoryModel.getIsEdit() == true) {
			message = "Category is Edited!!!";
		} else {
			message = "Category is saved!!!";
		}
		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("edit/{categoryid}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryid") Integer categoryid) {
		Optional<Category> optional = categoryService.findById(categoryid);
		CategoryModel categoryModel = new CategoryModel();
		if (optional.isPresent()) {
			Category entity = optional.get();
			BeanUtils.copyProperties(entity, categoryModel);
			categoryModel.setIsEdit(true);
			model.addAttribute("category", categoryModel);
			return new ModelAndView("admin/categories/edit");
		}
		model.addAttribute("message", "Category is not existed!!!");
		return new ModelAndView("forward:/admin/categories", model);
	}

	@GetMapping("delete/{categoryid}")
	public ModelAndView delet(ModelMap model, @PathVariable("categoryid") Integer categoryid) {
		categoryService.deleteById(categoryid);
		model.addAttribute("message", "Category is deleted !!!! ");
		return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "categoryname", required = false) String categoryname) {
		List<Category> list = null;
		// co nội dung truyền ve không, name là tuy chon khi required=false
		if (StringUtils.hasText(categoryname)) {
			list = categoryService.findByCategoryname(categoryname);
		} else {
			list = categoryService.findAll();
		}
		model.addAttribute("categories", list);
		return "admin/categories/search";
	}

	@RequestMapping("searchpaginated")
	public String search(ModelMap model, @RequestParam(name = "categoryname", required = false) String categoryname,
			@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
		int count = (int) categoryService.count();
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(3);
		Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by("categoryname"));
		Page<Category> resultPage = null;
		if (StringUtils.hasText(categoryname)) {
			resultPage = categoryService.findByCategorynameContaining(categoryname, pageable);
			model.addAttribute("categoryname", categoryname);
		} else {
			resultPage = categoryService.findAll(pageable);
		}

		int totalPages = resultPage.getTotalPages();
		if (totalPages > 0) {
			int start = Math.max(1, currentPage - 2);
			int end = Math.min(currentPage + 2, totalPages);
			if (totalPages > count) {
				if (end == totalPages)
					start = end - count;
				else if (start == 1)
					end = start + count;
			}

			List<Integer> pageNumbers = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("categoryPage", resultPage);
		return "admin/categories/searchpaginated";

	}
}
