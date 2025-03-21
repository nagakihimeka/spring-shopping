package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.Entity.Item;

@Component
@SessionScope
public class Cart {
	//	商品リスト
	//	空のリストを作成
	private List<Item> itemList = new ArrayList<>();

	//	商品リストゲッター
	public List<Item> getItemList() {
		return itemList;
	}

	//	合計金額ゲッター
	public int getTotalPraice() {
		//合計金額
		int total = 0;
		for (Item item : itemList) {
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}

	//	カート追加メソッド
	public void add(Item newItem) {
		//		現在のカートに同一のIDがないか探す
		Item exisItem = null;
		for (Item item : itemList) {
			if (item.getId() == newItem.getId()) {
				exisItem = item;
				break;
			}
		}
		//		カートに商品が存在しなかったら追加
		if (exisItem == null) {
			itemList.add(newItem);
		} else {
			//			カートに商品が存在した場合は個数を更新する
			exisItem.setQuantity(
					exisItem.getQuantity() + newItem.getQuantity());
		}
	}

	//	カートから商品削除
	public void delete(int itemId) {
		for (Item item : itemList) {
			if (item.getId() == itemId) {
				itemList.remove(item);
				break;
			}
		}

	}

	//	カートから中身全て削除
	public void clear() {
		itemList = new ArrayList<>();
	}
}
