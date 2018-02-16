package jp.co.rakus.merucariTraining.web;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateForm {
	
	private Integer id;
	@NotNull
	private String name;
	@Min(1)
	@Max(999)
	private Integer price;
	@NotNull
	private String parent;
	@NotNull
	private String child;
	@NotNull
	private String grandChild;
	@NotNull
	private String brand;
	@NotNull
	private Integer condition;
	@NotNull
	private String description;


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getChild() {
		return child;
	}
	public void setChild(String child) {
		this.child = child;
	}
	public String getGrandChild() {
		return grandChild;
	}
	public void setGrandChild(String grandChild) {
		this.grandChild = grandChild;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getCondition() {
		return condition;
	}
	public void setCondition(Integer condition) {
		this.condition = condition;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
