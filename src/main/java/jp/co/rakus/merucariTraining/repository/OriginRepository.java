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

import jp.co.rakus.merucariTraining.domain.Origin;

public class OriginRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate) template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("original");
		insert = withTableName.usingGeneratedKeyColumns("train_id");
	}

	private static final RowMapper<Origin> originRowMapper = (rs, i) -> {
		Origin origin = new Origin();
		origin.setId(rs.getInt("train_id"));
		origin.setName(rs.getString("name"));
		origin.setItemConditionId(rs.getInt("item_condition_id"));
		origin.setCategoryName(rs.getString("category_name"));
		origin.setBrandName(rs.getString("brand_name"));
		origin.setPrice(rs.getDouble("price"));
		origin.setShipping(rs.getInt("shipping"));
		origin.setItemDescription(rs.getString("shipping"));
		return origin;
	};
	
	private static final RowMapper<Origin> findCategoryRowMapper = (rs, i)->{
		Origin origin = new Origin();
		origin.setId(rs.getInt("train_id"));
		origin.setName(rs.getString("category_name"));
		return origin;
	};
	
	public  List<Origin> findCategoryNameAll(){
		String sql = "select distinct id, regexp_split_to_table(category_name,'/') as valiation from original order by id ";
		List<Origin> categoryList = template.query(sql, originRowMapper);
		return categoryList;
	}
	
	public  List<Origin> findCategoryfindCategoryType(String category){
		String sql = "select distinct id, regexp_split_to_table(category_name,'/') as valiation, strpos(category_name, :valiation)  from original order by id , strpos(category_name, :valiation) ";
		SqlParameterSource param = new MapSqlParameterSource().addValue("valiation", category);
		List<Origin> categoryList = template.query(sql, param, originRowMapper);
		return categoryList;
	}

}
