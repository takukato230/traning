package jp.co.rakus.merucariTraining.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.rakus.merucariTraining.domain.Item;
import jp.co.rakus.merucariTraining.service.ItemService;

@Controller
@RequestMapping("/")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public AddItemForm setUpAddItemForm() {
		return new AddItemForm();
	}
	@ModelAttribute
	public ItemEditForm setUpItemEditForm() {
		return new ItemEditForm();
	}
	
	@ModelAttribute
	public UpdateForm setUpUpdateForm() {
		return new UpdateForm();
	}



/*-----------------------------------------------------------------	
				　　　　itemフォルダ(一覧画面)
------------------------------------------------------------------*/	
	/**
	 * itemを取得し商品一覧のindexページに飛ぶ
	 * @param model
	 * @return　/item/list.html
	 */
	@RequestMapping("/")
	public String index(String pageValue, Model model) {
		Integer pageNation = 0;
		List<Item> itemList = new ArrayList<Item>();
		if(session.getAttribute("page")==null) {
			itemList = itemService.displayFirstTime();
			pageNation = itemService.culcPage(itemList.size());
			session.setAttribute("page", 1);		
		}else if(session.getAttribute("page")!=null && pageValue==null) {
			itemList = itemService.displayFirstTime();
			pageNation = itemService.culcPage(itemList.size());
			itemList = itemService.findItemByPage((Integer)session.getAttribute("page"), 0);
			session.setAttribute("page", (Integer)session.getAttribute("page"));		
		}else {
			Integer pageNext = (Integer)session.getAttribute("page")+ Integer.parseInt(pageValue);
			itemList = itemService.findItemByPage((Integer)session.getAttribute("page"), Integer.parseInt(pageValue));
			List<Item> culcPageList = itemService.displayFirstTime();
			pageNation = itemService.culcPage(culcPageList.size());
			session.setAttribute("page", pageNext);
		}
			model.addAttribute("pageNation", pageNation);
			model.addAttribute("itemList", itemList);
			return "/item/list";
	}
	/**
	 * 入力されたページ数の受取メソッド
	 * @param pageNumber　(String)
	 * @param model
	 * @return　indexメソッド
	 */
	@RequestMapping(value="/movePage")
	public String movePage(String pageNumber, Model model) {
		session.setAttribute("page", Integer.parseInt(pageNumber));
		String pageValue = itemService.changePageNumberTo0(pageNumber);
		return index(pageValue, model);
	}
	/**
	 * 商品追加に飛ぶメソッド
	 * @return /itemEdit/add.html
	 */
	@RequestMapping(value="/addItem")
	public String addItem() {
		return "/itemEdit/add";
	}
	
	
/*-----------------------------------------------------------------------	
							itemフォルダ（詳細画面）
------------------------------------------------------------------------*/
	/**
	 * item詳細画面表示メソッド
	 * @param id
	 * @param model
	 * @return /item/detail.html
	 */
	@RequestMapping(value="/itemDetail")
	public String itemDetail(String id, Model model) {
		Item item = itemService.findItembyId(Integer.parseInt(id));
		model.addAttribute("item", item);
		return "/item/detail";
	}
	
/*------------------------------------------------------------------------	
							itemEditフォルダ(add画面)
------------------------------------------------------------------------*/
	@RequestMapping(value="/insertItem")
	public String addItem(@Validated AddItemForm form, BindingResult result, Model model, String pageValue) {
		if(result.hasErrors()) {
			model.addAttribute("validationError", "不正な値が入力されました。");
			return "/itemEdit/add";
		}
		System.out.println("categoryId:"+form.getGrandChild());
		Item item = new Item();
		item.setName(form.getName());
		item.setCategory(Integer.parseInt(form.getGrandChild()));
		item.setPrice(form.getPrice());
		item.setCondition(form.getCondition());
		item.setBrand(form.getBrand());
		item.setDescription(form.getDescription());
		Item newItem = itemService.insertItem(item);
		model.addAttribute("newItem",newItem);
		return index(pageValue, model);
	}
/*--------------------------------------------------------------------------	
							itemEditフォルダ（edit画面）※詳細画面から推移
---------------------------------------------------------------------------*/
	/**
	 * 商品編集値受取~ページ推移メソッド
	 * @param id : Integer (商品id)
	 * @param model
	 * @return edit.html
	 */
	@RequestMapping(value = "/editItem")
	public String editItem(ItemEditForm form, Model model) {
		Item item = itemService.findItembyId(form.getId());
		model.addAttribute("item", item);
		return "/itemEdit/edit";
	}
	
	/**
	 * 商品情報編集メソッド
	 * @param form
	 * @param result
	 * @param model
	 * @param pageValue
	 * @return　indexメソッド
	 */
	@RequestMapping(value="/updateItem")
	public String updateItem(@Validated UpdateForm form, BindingResult result, Model model, String pageValue){
		System.out.println("ここで値とれている？？商品編集のitemId:"+form.getId());
		System.out.println("ここで値とれている？？商品編集のitemName:"+form.getName());
		/*if(result.hasErrors()) {
			model.addAttribute("validationError", "不正な値が入力されました。");
			return "/itemEdit/edit";
		}*/
		Item item = new Item();
		BeanUtils.copyProperties(form, item);
		item.setId(form.getId());
		item.setName(form.getName());
		item.setCategory(Integer.parseInt(form.getGrandChild()));
		item.setPrice(form.getPrice());
		item.setCondition(form.getCondition());
		item.setBrand(form.getBrand());
		item.setDescription(form.getDescription());
		Item newItem = itemService.updateItem(item);
		itemService.updateItem(item);
		System.out.println("ここまで来ていたらupdate完了："+newItem.getName());
		return index(pageValue, model);
	}

	
}
