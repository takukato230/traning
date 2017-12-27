package jp.co.rakus.merucariTraining.domain;

public class Category {

	private Integer id;
	private Integer parent;
	private String name;
	private String nameAll;
	private String parentName;
	private String childName;
	private String grandChildName;
	private Integer parentId;
	private Integer childId;
	private Integer grandChildId;
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getChildId() {
		return childId;
	}
	public void setChildId(Integer childId) {
		this.childId = childId;
	}
	public Integer getGrandChildId() {
		return grandChildId;
	}
	public void setGrandChildId(Integer grandChildId) {
		this.grandChildId = grandChildId;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getGrandChildName() {
		return grandChildName;
	}
	public void setGrandChildName(String grandChildName) {
		this.grandChildName = grandChildName;
	}
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
