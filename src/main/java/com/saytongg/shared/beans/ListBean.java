package com.saytongg.shared.beans;

import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "list")
public class ListBean<T> {

	private List<T> items;
	
	public ListBean() {}

	public ListBean(List<T> items) {
		super();
		this.items = items;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
