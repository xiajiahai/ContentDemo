package com.example.xiaa_20150623.cursordemo;

/**
 * Created by xiaa_20150623 on 2015/8/8.
 */
public class PersonItem {
	private Integer id;
	private String name;
	private Integer amount;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public PersonItem(){}

	public PersonItem(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
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

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", amount=" + amount
				+ "]";
	}
}

