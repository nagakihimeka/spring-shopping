package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Item;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.model.Cart;

@Controller
public class CartController {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	Cart cart;

	@GetMapping("/cart")
	public String index() {
		return "cart";
	}

	//	指定した商品をカートに追加
	//	数量未定の場合は１	
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam(name = "cartId") Integer cartId,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
			Model model) {
		//		//		商品コードをキーに商品情報を取得する
		Item item = itemRepository.findById(cartId).get();
		//		//		商品オブジェクトを個数にセット
		item.setQuantity(quantity);
		//		追加
		cart.add(item);
		//		/cartにリダイレクト
		model.addAttribute(cartId);
		return "redirect:/cart";
	}

	//	指定した商品をカートから削除
	@PostMapping("/cart/delete")
	public String deleteCart(
			@RequestParam(name = "itemId") int itemId) {
		//削除処理 モデルでメソッド作り使う
		cart.delete(itemId);
		return "redirect:/cart";
	}
}
