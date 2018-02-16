package jp.co.rakus.merucariTraining.web;

import java.sql.SQLException;
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

	@ModelAttribute
	public SearchItemForm setUpSearchItemForm() {
		return new SearchItemForm();
	}

	/*-----------------------------------------------------------------	
					　　　　itemフォルダ(一覧画面)
	------------------------------------------------------------------*/
	/**
	 * itemを取得し商品一覧のindexページに飛ぶ
	 * 
	 * @param model
	 * @return /item/list.html
	 */
	@RequestMapping("/")
	public String index(String pageValue, Model model) {
		Integer pageNation = 0;
		List<Item> itemList = new ArrayList<Item>();
		if (session.getAttribute("page") == null) {
			itemList = itemService.displayFirstTime();
			pageNation = itemService.culcPage(itemList.size());
			session.setAttribute("page", 1);
		} else if (session.getAttribute("page") != null && pageValue == null) {
			itemList = itemService.displayFirstTime();
			pageNation = itemService.culcPage(itemList.size());
			itemList = itemService.findItemByPage((Integer) session.getAttribute("page"), 0);
			session.setAttribute("page", (Integer) session.getAttribute("page"));
		} else {
			Integer pageNext = (Integer) session.getAttribute("page") + Integer.parseInt(pageValue);
			itemList = itemService.findItemByPage((Integer) session.getAttribute("page"), Integer.parseInt(pageValue));
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
	 * 
	 * @param pageNumber
	 *            (String)
	 * @param model
	 * @return indexメソッド
	 */
	@RequestMapping(value = "/movePage")
	public String movePage(String pageNumber, Model model) {
		session.setAttribute("page", Integer.parseInt(pageNumber));
		String pageValue = itemService.changePageNumberTo0(pageNumber);// このメッソド
		return index(pageValue, model);
	}

	/**
	 * 商品追加に飛ぶメソッド
	 * 
	 * @return /itemEdit/add.html
	 */
	@RequestMapping(value = "/addItem")
	public String addItem() {
		return "/itemEdit/add";
	}

	@RequestMapping(value = "/searchItem")
	public String searchItem(SearchItemForm form, Model model, String pageValue) {
		System.out.println("親～～～～"+form.getParant());
		List<Item> itemList = new ArrayList<>();
		String name = form.getName();
		String brand = form.getBrand();
		String parent ;
		String child;
		Integer grandChild;
		if(form.getParant()==null) {
			parent="";
		}else {
			parent = form.getParant();
		}
		
		if(form.getChild()==null) {
			child = "";
		}else {
			child = form.getChild();
		}
		
		if(form.getGrandChild()==null) {
			grandChild=0;
		}else {
			grandChild = Integer.parseInt(form.getGrandChild());
		}
		
		System.out.println("名前に値入ってたりする？？" + name);
		System.out.println("ブランドは入っている？？" + brand);
		System.out.println("parentには何入るんだろ？？" + parent);
		System.out.println("てか親は"+form.getParant());
		System.out.println("子はなんなの？？" + form.getChild());
		System.out.println("childには何入ってるの？？" + child);
		System.out.println("孫には何入ってるの？？" + form.getGrandChild());
		System.out.println("grandChildの変数には何入ってるの？？" + grandChild);
		System.out.println("そもそも親はいるVer2？？" + form.getParant());
		if (form.getGrandChild() == null || form.getGrandChild()=="") {
			if (form.getChild()== null || form.getChild()=="") {
				if (form.getParant() == null || form.getParant()=="") {
					if (name == "") {
						if (brand == "") {
							System.out.println("エラーになってない？？");
							model.addAttribute("errorMessage", "検索項目を入力して下さい");
							return index(pageValue, model);
						} else {
							System.out.println("メソッドチェック【５】");
							itemList = itemService.findItemByBrand(brand);
						}
					} else {
						if (brand == "") {
							System.out.println("メソッドチェック【新規2：名前のみ】");
							itemList = itemService.findItemByName(name);
						} else {
							System.out.println("メソッドチェック【１０】");
							itemList = itemService.findItemByNameAndBrand(name, brand);
						}
					}
				}else /*if(form.getParant()!=null) */{
					if (name == "") {
						if (brand == "") {
							System.out.println("メソッドチェック【１】親だけはここに来て！！");
							itemList = itemService.findItemByCategoryOnString1(form.getParant());
						} else {
							System.out.println("メソッドチェック【２】");
							itemList = itemService.findItemByCategoryOnStringAndBrand1(form.getParant(), brand);
						}
					} else {
						if (brand == "") {
							System.out.println("メソッドチェック【３】");
							itemList = itemService.findItemByNameAndCategoryOnString1(name, form.getParant());
						} else {
							System.out.println("メソッドチェック【４】");
							itemList = itemService.findItemByNameAndCategoryOnStringAndBrand1(name, form.getParant(), brand);
						}
					}
				}
			} else /*if(form.getChild()!=null) */{
				if (name == "") {
					if (brand == "") {
						System.out.println("メソッドチェック【７】");
						itemList = itemService.findItemByCategoryOnString2(form.getChild());
					} else {
						System.out.println("メソッドチェック【８】");
						itemList = itemService.findItemByCategoryOnStringAndBrand2(form.getChild(), brand);
					}
				} else {
					if (brand == "") {
						System.out.println("メソッドチェック【９】");
						itemList = itemService.findItemByNameAndCategoryOnString2(name, form.getChild());
					} else {
						System.out.println("メソッドチェック新規【３】名、子、ブランド");
						itemList = itemService.findItemByNameAndCategoryOnStringAndBrand2(name, form.getChild(), brand);
					}
				}
			}
		} else /*if(form.getGrandChild()!=null) */{
			if (name == "") {
				if (brand == "") {
					System.out.println("メソッドチェック【１１】");
					itemList = itemService.findItemByCategoryOnInteger(grandChild);
				} else {
					System.out.println("メソッドチェック【１２】");
					itemList = itemService.findItemByCategoryOnIntegerAndBrand(grandChild, brand);
				}
			} else {
				if (brand == "") {
					System.out.println("メソッドチェック【１３】");
					itemList = itemService.findItemByNameAndCategoryOnInteger(name, grandChild);
				} else {
					System.out.println("メソッドチェック【１４】");
					itemList = itemService.findItemByNameAndCategoryOnIntegerAndBrand(name, grandChild, brand);
				}
			}
		}
		System.out.println("検索値チェックnullになってない？？：" + itemList.size());
		model.addAttribute("itemList", itemList);
		session.setAttribute("page", 1);
		return "/item/list";

	}

	/*-----------------------------------------------------------------------	
								itemフォルダ（詳細画面）
	------------------------------------------------------------------------*/
	/**
	 * item詳細画面表示メソッド
	 * 
	 * @param id
	 * @param model
	 * @return /item/detail.html
	 */
	@RequestMapping(value = "/itemDetail")
	public String itemDetail(String id, Model model) {
		Item item = itemService.findItembyId(Integer.parseInt(id));
		model.addAttribute("item", item);
		return "/item/detail";
	}

	/*------------------------------------------------------------------------	
								itemEditフォルダ(add画面)
	------------------------------------------------------------------------*/
	@RequestMapping(value = "/insertItem")
	public String addItem(@Validated AddItemForm form, BindingResult result, Model model, String pageValue) {
		if (result.hasErrors()) {
			model.addAttribute("validationError", "不正な値が入力されました。");
			return "/itemEdit/add";
		}
		System.out.println("categoryId:" + form.getGrandChild());
		Item item = new Item();
		item.setName(form.getName());
		item.setCategory(Integer.parseInt(form.getGrandChild()));
		item.setPrice(form.getPrice());
		item.setCondition(form.getCondition());
		item.setBrand(form.getBrand());
		item.setDescription(form.getDescription());
		Item newItem = itemService.insertItem(item);
		model.addAttribute("newItem", newItem);
		return index(pageValue, model);
	}

	/*--------------------------------------------------------------------------	
								itemEditフォルダ（edit画面）※詳細画面から推移
	---------------------------------------------------------------------------*/
	/**
	 * 商品編集値受取~ページ推移メソッド
	 * 
	 * @param id
	 *            : Integer (商品id)
	 * @param model
	 * @return edit.html
	 */
	@RequestMapping(value = "/editItem")
	public String editItem(ItemEditForm form, Model model) {
		Item item = itemService.findItemByIdNew(form.getId());
		model.addAttribute("item", item);
		return "/itemEdit/edit";
	}

	/**
	 * 商品情報編集メソッド
	 * 
	 * @param form
	 * @param result
	 * @param model
	 * @param pageValue
	 * @return indexメソッド
	 */
	@RequestMapping(value = "/updateItem")
	public String updateItem(@Validated UpdateForm form, BindingResult result, Model model, String pageValue) {
		System.out.println("ここで値とれている？？商品編集のitemId:" + form.getId());
		System.out.println("ここで値とれている？？商品編集のitemName:" + form.getName());
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
		System.out.println("ここまで来ていたらupdate完了：" + newItem.getName());
		return index(pageValue, model);
	}

}
