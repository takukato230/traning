package jp.co.rakus.merucariTraining.repository;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.rakus.merucariTraining.domain.Category;

@RunWith(SpringRunner.class)
@SpringBootTest	
public class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository repository;
	
	Integer id1 = 1;
	Integer id2 = 120;
	String name1= "Women";
	String name2= "Team";

	@Test
	public void test1() {
		List<Category> categoryList = repository.findCategoryNameAll();
		assertThat(categoryList, hasSize(1436));
	}
	
	@Test
	public void test2() {
		List<Category> categoryList = repository.findNameAllSplit1();
		assertThat(categoryList, hasSize(10));
	}
	
	@Test
	public void test3() {
		List<Category> categoryList = repository.findNameAllSplit2ByName(id1);
		assertThat(categoryList, hasSize(17));
	}
	
	@Test
	public void test4() {
		List<Category> categoryList = repository.findNameAllSplit3ByName(id2);
		assertThat(categoryList, hasSize(10));
	}
	
	@Test
	public void test5() {
		List<Category> categoryList = repository.findNameAllSplit2ForNameAll(name1);
		assertThat(categoryList, hasSize(153));
	}
	
	@Test
	public void test6() {
		List<Category> categoryList = repository.findNameAllSplit3ForNameAll(name2);
		assertThat(categoryList, hasSize(10));
	}

}
