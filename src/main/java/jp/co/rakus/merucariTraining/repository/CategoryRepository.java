package jp.co.rakus.merucariTraining.repository;



import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.rakus.merucariTraining.domain.Category;

@Repository
public class CategoryRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("items");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}

	private static final RowMapper<Category> categoryRowMapper = (rs, i) -> {
		Category category = new Category();
		category.setName(rs.getString("name"));
		return category;
	};
	
	private static final RowMapper<Category> categoryNameAllRowMapper = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setName(rs.getString("name"));
		return category;
	};
	
	/**
	 * categoryのnameを全て取り出す
	 * @return
	 */
	public List<Category> findCategoryNameAll(){
		String sql = "select name from category order by id";
		List<Category> categoryList = template.query(sql, categoryRowMapper);
		return categoryList;
	}
	
	/**
	 * splitで区切った一番目のnameをListで拾う
	 * @return　List<Category>
	 */
	public List<Category> findNameAllSplit1(){
		String sql = "select name, id from category where parent is null";
		List<Category> nameList = template.query(sql, categoryNameAllRowMapper);
		return nameList;
	}
	
	/**
	 * nameで検索したsplit区切り２番目のnameをlistで拾う
	 * @param name　(String)
	 * @return　List<Category>
	 */
	public List<Category> findNameAllSplit2ByName(Integer id){
		String sql = "select name, id from category where parent = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Category> nameList = template.query(sql, param, categoryNameAllRowMapper);
		return nameList;
	}
	
	/**
	 * nameで検索したsplit区切り3,4,5番目のnameをlistで拾う
	 * @param name　(String)
	 * @return　List<Category>
	 */
	public List<Category> findNameAllSplit3ByName(Integer id){
		String sql = "select name, id from category where parent = :id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Category> nameList = template.query(sql, param, categoryNameAllRowMapper);
		return nameList;
	}
	
/*-----------------------------------------------------------------------------------------------------------------------------------------*/	
	
	public List<Category> findNameAllSplit2ForNameAll(String name){
		String sql = "select distinct split_part(name_all, '/', 2 ) as name, id from category where split_part(name_all, '/', 1 ) like :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", "%"+name+"%");
		List<Category> nameList = template.query(sql,param, categoryNameAllRowMapper);
		return nameList;
	}
	
	public List<Category> findNameAllSplit3ForNameAll(String name){
		String sql = "select (split_part(name_all, '/', 3 )|| split_part(name_all, '/', 4 )|| split_part(name_all, '/', 5 )) as name, id from category where split_part(name_all, '/', 2 ) like :name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name","%"+name+"%");
		List<Category> nameList = template.query(sql,param, categoryNameAllRowMapper);
		return nameList;
	}

}
