package jp.co.rakus.merucariTraining.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.rakus.merucariTraining.domain.Category;
import jp.co.rakus.merucariTraining.domain.Item;
import jp.co.rakus.merucariTraining.service.ItemService;

@Controller
@RequestMapping("ajax")
public class AjaxController {
	
	@Autowired
	private ItemService itemService;
	
	
/*------------------------------------------------------------------
					商品追加のカテゴリークラス
-------------------------------------------------------------------*/
	/**
	 * autocompleteメソッド（商品追加表示用）　
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/autocomplete")
	public List<Category> autocomplete(){
		List<Category> categoryList = itemService.findParent();
		return categoryList;
	}
	
	/**
	 * autocompleteメソッド（商品追加子要素検索用）　
	 * @param name : String
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/autocompleteOfChild")
	public List<Category> autocompleteOfCategory(Integer id) {
		System.out.println("parantId:"+id);
		List<Category> itemList = itemService.findChils(id);
		return itemList;
	}
/*	*//**
	 * autocompleteメソッド（商品追加孫要素検索用）
	 * @param name　: String
	 * @return
	 *//*
	@ResponseBody
	@RequestMapping("/autocompleteOfGrandChild")
	public String[] autocompleteOfGrandChild(Integer id) {
		String[] categoryName = itemService.createArrayForAutoComplete(itemService.findGrandChild(id));
		return categoryName;
	}*/
/*---------------------------------------------------------------------------	
							商品追加のブランドクラス
----------------------------------------------------------------------------*/	
	
	/**
	 * 商品追加をする際にブランドに値を詰めるメソッド
	 * @return　String[]
	 */
	@ResponseBody
	@RequestMapping("/autocompleteForBrand")
	public String[] autocomplateForBrand() {
		String[] brandList = itemService.createArrayForAutoCompleteOfBrand();
		return brandList;
	}
/*--------------------------------------------------------------------------------	
						　	      　商品検索（一覧画面）
---------------------------------------------------------------------------------*/
	/**
	 * 商品検索で使用するitem.nameのautocomplete用メッソド
	 * @return String[]
	 */
	@RequestMapping("/autocompleteOfName")
	@ResponseBody
	public String[] autocompleteOfName() {
		String[] nameArray = itemService.createArrayForAutocompleteOfName();
		return nameArray;
	}
	
	@RequestMapping("/searchCategoryNameSplit2")
	@ResponseBody
	public List<Category> searchCategoryNameSplit2(String name){
		System.out.println("子要素検索カテゴリーの名前は？？"+name);
		List<Category> categoryName = itemService.findChild(name);
		System.out.println("子要素のリストサイズは？？"+categoryName.size());
		return categoryName;
	}
	
	@RequestMapping("/searchCategoryNameSplit3")
	@ResponseBody
	public List<Category> searchCategoryNameSplit3(String name){
		List<Category> categoryName = itemService.findGrandChild(name);
		return categoryName;
	}
}
