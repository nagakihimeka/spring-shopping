package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Item;
import com.example.demo.Repository.CategoryRepository;
import com.example.demo.Repository.ItemRepository;

@Controller
public class ItemController {

	@Autowired
	CategoryRepository categiryRepository;

	@Autowired
	ItemRepository itemRepository;

	@GetMapping("/items")
	public String index(
			@RequestParam(name = "categiryId", defaultValue = "") Integer categiryId,
			Model model) {

		//		カテゴリーを取得
		List<Category> categoryList = categiryRepository.findAll();
		model.addAttribute("categories", categoryList);

		//				カテゴリーごとに絞る
		List<Item> itemList = null;
		if (categiryId == null) {
			//			全商品取得
			itemList = itemRepository.findAll();
		} else {
			//			カテゴリーの商品を取得
			itemList = itemRepository.findByCategoryId(categiryId);
		}

		model.addAttribute("items", itemList);
		return "item";
	}
}
