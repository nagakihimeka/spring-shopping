package com.example.demo.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Item;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.model.Cart;

@Controller
public class OrderController {

	@Autowired
	Cart cart;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	//	顧客情報入力画面
	@GetMapping("/order")
	public String index() {
		return "customerForm";
	}

	//	確認画面を表示
	@PostMapping("/order/confirm")
	public String confirm(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			Model model) {

		//お客様情報を受け取り、まとめる
		Customer customer = new Customer(name, address, tel, email);
		model.addAttribute("customer", customer);

		return "orderConfirm";
	}

	@PostMapping("/order")
	public String doOrder(
			@RequestParam(name = "name") String name,
			@RequestParam(name = "address") String address,
			@RequestParam(name = "tel") String tel,
			@RequestParam(name = "email") String email,
			Model model) {

		//お客様情報をDBに格納
		Customer customer = new Customer(name, address, tel, email);
		customerRepository.save(customer);

		//		注文情報をDBに格納
		Order order = new Order(
				customer.getId(),
				LocalDate.now(),
				cart.getTotalPraice());
		orderRepository.save(order);

		//		注文詳細情報をDBに格納
		List<Item> itemList = cart.getItemList();
		List<OrderDetail> orderDetails = new ArrayList<>();
		for (Item item : itemList) {
			orderDetails.add(
					new OrderDetail(
							order.getId(),
							item.getId(),
							item.getQuantity()));
		}
		orderDetailRepository.saveAll(orderDetails);

		//		セッションスコープの格納情報をクリア
		cart.clear();

		//		画面返却用注文番号を設定
		model.addAttribute("orderNumber", order.getId());
		model.addAttribute("cart", cart);

		return "ordered";
	}

}
