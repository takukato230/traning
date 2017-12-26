package jp.co.rakus.merucariTraining.repository;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.rakus.merucariTraining.domain.Item;


@RunWith(SpringRunner.class)
@SpringBootTest	
public class ItemRepositoryTest extends ItemRepository {
	
	@Autowired
	private ItemRepository repository;
	
	String name = "Bu";
	String parant2  = "Kids";
	String parent = "Sports";
	String child = "Team";
	Integer grandChild = 1279;
	String brand2 = "Bandai";
	String brand = "Nike";
	
	@Test
	public void test1And2() {
		List<Item> itemList = repository.findLimit20ByOffset(1);
		List<Item> newItemList = repository.findLimit20ByOffset(5000000);
		assertThat(itemList, hasSize(30));
		assertThat(newItemList, is(empty()));
	}
	
	@Test
	public void test3And4() {
		Item item = repository.findItemById(1);
		/*Item newItem = repository.findItemById(5000000);*/
		assertThat(item.getId(), is(1));
		assertThat(item.getName(), containsString("FREE SHIP! Brand New Starbucks Apron"));
		/*assertThat(newItem, is(notNullValue()));*/
	}
	
	@Test
	public void test5() {
		List<Item> itemList = repository.findAllBrand();
		assertThat(itemList, hasSize(4810));
	}
	
	@Test
	public void testSave6And7() {
		Item newItem = new Item();
		newItem.setName("test_insert");
		newItem.setPrice(30);
		newItem.setCondition(2);
		newItem.setCategory(150);
		newItem.setBrand("Nike");
		newItem.setShipping(1);
		newItem.setDescription("testInsert");
		Item itemInsert = repository.insertItem(newItem);
		List<Item> itemList = repository.findItemAllOnlyId();
		assertThat(itemList, hasSize(1482556));
		Item nextItem  = new Item();
		nextItem.setId(1482551);
		nextItem.setName("test_update");
		Item itemUpdate = repository.updateItem(nextItem);
		List<Item> newItemList = repository.findItemAllOnlyId();
		assertThat(newItemList, hasSize(1482556));
	}
	
	@Test
	public void test8() {
		List<Item> itemList = repository.findItemByCategoryOnStringAndBrand1(parant2, brand2);
		assertThat(itemList, hasSize(157));
	}
	
	@Test
	public void test9() {
		List<Item> itemList = repository.findItemByCategoryOnStringAndBrand2(child, brand);
		assertThat(itemList, hasSize(477));
	}
	
	@Test
	public void test10() {
		List<Item> itemList = repository.findItemByCategoryAndBrand(grandChild, brand);
		assertThat(itemList, hasSize(43));
	}
	
	@Test
	public void test11() {
		List<Item> itemList = repository.findItemByNameAndCategoryOnStringAndBrand1(name, parent, brand);
		assertThat(itemList, hasSize(45));
	}
	
	@Test
	public void test12() {
		List<Item> itemList = repository.findItemByNameAndCategoryOnStringAndBrand2(name,child, brand);
		assertThat(itemList, hasSize(7));
	}
	
	@Test
	public void test13() {
		List<Item> itemList = repository.findItemByNameAndCategoryOnIntegerAndBrand(name, grandChild, brand);
		assertThat(itemList, hasSize(4));
	}
	
	@Test
	public void test14() {
		List<Item> itemList = repository.findItemByNameAndBrand(name, brand);
		assertThat(itemList, hasSize(1189));
	}
	
	@Test
	public void test15() {
		List<Item> itemList = repository.findItemByNameAndCategoryOnString1(name, parent);
		assertThat(itemList, hasSize(782));
	}
	
	@Test
	public void test16() {
		List<Item> itemList = repository.findItemByNameAndCategoryOnString2(name, child);
		assertThat(itemList, hasSize(32));
	}
	
	@Test
	public void test17() {
		List<Item> itemList = repository.findItemByNameAndCategory(name, grandChild);
		assertThat(itemList, hasSize(5));
	}
	
	@Test
	public void test18() {
		List<Item> itemList = repository.findItemByName(name);
		assertThat(itemList, hasSize(65252));
	}
	
	@Test
	public void test19() {
		List<Item> itemList = repository.findItemByCategoryOnString1(parent);
		assertThat(itemList, hasSize(25342));
	}
	
	@Test
	public void test20() {
		List<Item> itemList = repository.findItemByCategoryOnString2(child);
		assertThat(itemList, hasSize(2121));
	}
	
	@Test
	public void test21() {
		List<Item> itemList = repository.findItemByCategory(grandChild);
		assertThat(itemList, hasSize(127));
	}
	
	@Test
	public void test22() {
		List<Item> itemList = repository.findItemByBrand(brand);
		assertThat(itemList, hasSize(54046));
	}

}
