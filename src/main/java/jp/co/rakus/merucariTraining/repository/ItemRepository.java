package jp.co.rakus.merucariTraining.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.rakus.merucariTraining.domain.Category;
import jp.co.rakus.merucariTraining.domain.Item;

@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	private static final RowMapper<Item> itemRowMapper = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		return item;
	};

	private static final RowMapper<Item> itemListRowMapper = (rs, i) -> {
		Category category = new Category();
		category.setName(rs.getString("category_name"));
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		/* item.setCategory(rs.getInt("category")); */
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		item.setCategories(category);
		return item;// itemにセットする値はselect文に記載されたもののみ
	};

	private static final RowMapper<Item> itemCulcRowMapper = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		return item;
	};

	private static final RowMapper<Item> brandOnlyRowMapper = (rs, i) -> {
		Item item = new Item();
		item.setBrand(rs.getString("brand"));
		return item;
	};

	private static final RowMapper<Item> nameOnlyRowMapper = (rs, i) -> {
		Item item = new Item();
		item.setName(rs.getString("name"));
		return item;
	};

	private static final RowMapper<Item> searchItemListRowMapper = (rs, i) -> {
		Category category = new Category();
		category.setName(rs.getString("category_name"));
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		/* item.setCategory(rs.getInt("category")); */
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getInt("price"));
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
/*		item.setCount(rs.getInt("count"));*/
		item.setCategories(category);
		return item;// itemにセットする値はselect文に記載されたもののみ
	};

	/**
	 * 指定したoffsetから30個分のItemを拾う
	 * 
	 * @param offset
	 * @return List<Item>
	 */
	public List<Item> findLimit20ByOffset(Integer offset) {
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id order by id desc limit 30 offset :offset";
		SqlParameterSource param = new MapSqlParameterSource().addValue("offset", offset);
		List<Item> itemList = template.query(sql, param, itemListRowMapper);
		return itemList;
	}

	/**
	 * itemを全て取得する
	 * 
	 * @return
	 */
	/*public List<Item> findItemAll() {
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id order by id desc ";
		List<Item> itemList = template.query(sql, itemListRowMapper);
		return itemList;
	}*/

	/**
	 * itemのidのみを全て取得する count使う
	 * 
	 * @return
	 */
	public List<Item> findItemAllOnlyId() {
		String sql = "select id from items order by id";
		List<Item> itemList = template.query(sql, itemCulcRowMapper);
		return itemList;
	}

	/**
	 * idで検索し、itemを探し出す
	 * 
	 * @param id
	 * @return
	 */
	public Item findItemById(Integer id) {
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.id = :id order by id ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Item item = template.queryForObject(sql, param, itemListRowMapper);
		return item;
	}

	/**
	 * categoryのname_allからitemsのcategoryを探し出してくる
	 * 
	 * @param categoryName
	 * @return
	 */
	/*public Integer findItemCategoryByCategoryName(String categoryName) {
		String sql = "select distinct i.category from items as i join category as c on i.category = c.id where c.name_all = :name_all";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name_all", categoryName);
		Integer category = template.queryForObject(sql, param, Integer.class);
		return category;
	}*/

	/**
	 * ブランドの曖昧検索
	 * 
	 * @return List<Item>
	 */
	public List<Item> findAllBrand() {
		String sql = "select distinct brand from items where brand is not null order by brand";
		/*
		 * SqlParameterSource param = new MapSqlParameterSource().addValue("brand",
		 * "%"+brand+"%");
		 */
		List<Item> brandList = template.query(sql, /* param, */ brandOnlyRowMapper);
		return brandList;
	}

	/**
	 * categoryNameで検索をかけcategoryを拾ってくる
	 * 
	 * @param name
	 *            String
	 * @return Integer
	 */
	/*public Integer findCategoryByCategoryName(String name) {
		String sql = "select i.category from items as i join category as c on i.category = c.id where c.name_all = :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		Integer id = template.queryForObject(sql, param, Integer.class);
		return id;
	}*/

	/**
	 * itemsテーブルのinsert
	 * 
	 * @param item
	 * @return Item
	 */
	public Item insertItem(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "insert into items(name, price, category, brand, condition, description) "
				+ "values(:name, :price, :category, :brand, :condition, :description)";
		template.update(sql, param);
		return item;
	}

	/**
	 * itemsテーブルのupdate
	 * 
	 * @param item
	 * @return Item
	 */
	public Item updateItem(Item item) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		String sql = "update items set name = :name, price = :price, category = :category, brand = :brand, condition = :condition, description = :description where id = :id";
		template.update(sql, param);
		return item;
	}

	/**
	 * itemsテーブルのnameを全て拾う
	 * 
	 * @return List<Item> itemRepository.findItemNameAll();
	 */
	/*public List<Item> findItemNameAll() {
		String sql = "select name from items where name is not null order by name";
		List<Item> itemList = template.query(sql, nameOnlyRowMapper);
		return itemList;
	}*/
/*------------------------------------------------------------------------------------------------------------------------------------	
													検索をかける
-------------------------------------------------------------------------------------------------------------------------------------*/
	/**
	 * name,category,brandで検索をかけitemを探しだす。
	 * @param name
	 * @param category
	 * @param brand
	 * @return
	 */
	public List<Item> findItemByNameAndCategoryOnStringAndBrand1(String name, String category, String brand) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and c.name_all like :category and i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("category", category+"%").addValue("brand",
				brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	
	public List<Item> findItemByNameAndCategoryOnStringAndBrand2(String name, String category, String brand) {	
		String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and c.name_all like :category and i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("category", "%"+category+"%").addValue("brand",
				brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByNameAndCategoryOnIntegerAndBrand(String name, Integer category, String brand) {	
		String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and i.category = :category and i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("category", category).addValue("brand",
				brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	/**
	 * category,brandで検索をかけitemを探す
	 * @param category
	 * @param brand
	 * @return
	 */
	public List<Item> findItemByCategoryOnStringAndBrand1(String category, String brand) {	
		String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where c.name_all like :category and i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("category", category+"%").addValue("brand",
				brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByCategoryOnStringAndBrand2(String category, String brand) {	
		String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where c.name_all like :category and i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("category", "%"+category+"%").addValue("brand",
				brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByCategoryAndBrand(Integer category, String brand) {	
			String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
					+ " from items as i join category as c on i.category = c.id where i.category = :category and i.brand = :brand order by i.id desc";
			SqlParameterSource param = new MapSqlParameterSource().addValue("category", category).addValue("brand",
					brand);
			List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
			return itemList;
		}
	/**
	 * name,brandで検索をかけitemを探す
	 * @param name
	 * @param brand
	 * @return
	 */
	public List<Item> findItemByNameAndBrand(String name, String brand) {	
		String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%").addValue("brand",
				brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	/**
	 * name,categoryで検索をかけitemを探す ※後からwhere句のみつける
	 * @param name
	 * @param category
	 * @return
	 */
	public List<Item> findItemByNameAndCategoryOnString1(String name, String category) {	
		String sql = "select  i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and c.name_all like :category order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%"+name + "%")
				.addValue("category", category+"%");
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByNameAndCategoryOnString2(String name, String category) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and c.name_all like :category order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
				.addValue("category", "%"+category+"%");
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByNameAndCategory(String name, Integer category) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name and i.category = :category order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%")
				.addValue("category", category);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}

	/**
	 * nameで検索をかけitemを探す
	 * @param name
	 * @return
	 */
	public List<Item> findItemByName(String name) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.name like :name order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%" + name + "%");
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	/**
	 * categoryで検索をかけitemを探す
	 * @param category
	 * @return
	 */
	public List<Item> findItemByCategoryOnString1(String category) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where c.name_all like :category order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("category", category+"%");
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByCategoryOnString2(String category) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where c.name_all like :category order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("category", "%"+category+"%");
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	public List<Item> findItemByCategory(Integer category) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.category = :category order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("category", category);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}
	/**
	 * brandで検索をかけitemを探す
	 * @param brand
	 * @return
	 */
	public List<Item> findItemByBrand(String brand) {	
		String sql = "select i.id, i.name, i.condition, c.name as category_name, i.brand, i.price, i.shipping, i.description"
				+ " from items as i join category as c on i.category = c.id where i.brand = :brand order by i.id desc";
		SqlParameterSource param = new MapSqlParameterSource().addValue("brand",brand);
		List<Item> itemList = template.query(sql, param, searchItemListRowMapper);
		return itemList;
	}

}
