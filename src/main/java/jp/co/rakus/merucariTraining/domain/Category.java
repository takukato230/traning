package jp.co.rakus.merucariTraining.domain;

public class Category {

	private Integer id;
	private Integer parent;
	private String name;
	private String nameAll;
	
	
	public String getNameAll() {
		return nameAll;
	}
	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
