package jp.co.rakus.merucariTraining.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.rakus.merucariTraining.domain.Item;

@RunWith(SpringRunner.class)
@SpringBootTest	
public class ItemServiceTest {
	
	@Autowired
	private ItemService service;
	
	Integer page = 1;
	Integer pageValue = 1;

	/*@Test
	public void test1() {
		List<Item> itemList = service.displayFirstTime();
		assertThat(itemList.get(0).getId(), is(1));
		assertThat(itemList.get(29).getId(), is(30));
	}
	
	@Test
	public void test2() {
		List<Item> list = service.findItemByPage(page, pageValue);
		assertThat(list.get(0).getId(), is(61));
		assertThat(list.get(29).getId(), is(90));
	}*/
	
	@Test
	public void test3() {
		Integer number = service.culcPage(service.displayFirstTime().size());
		assertThat(number, is(49419));
	}
	
	@Test
	public void test4() {
		String pageNumber = service.changePageNumberTo0("100");
		assertThat(pageNumber, is("0"));
	}
	
	@Test
	public void test5() {
		String[] categoryList = service.createArrayForAutoComplete(service.findParent());
		assertThat(categoryList, arrayWithSize(10));
	}
	
	@Test
	public void test6() {
		String[] itemArray = service.createArrayForAutoCompleteOfBrand();
		assertThat(itemArray, arrayWithSize(4810));
	}

}
