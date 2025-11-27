package com.saytongg.shared.beans;

import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "page")
public class PageBean<T>{
	
	private List<T> items;
    private long totalNumberOfItems;
    private int currentPage;
    private int maxPage;
    private int itemsPerPage;
	
	public PageBean() {}

	public PageBean(List<T> items, long totalNumberOfItems, int currentPage, int maxPage, int itemsPerPage) {
		super();
		
		this.items = items;
		this.totalNumberOfItems = totalNumberOfItems;
		this.currentPage = currentPage;
		this.maxPage = maxPage;
		this.itemsPerPage = itemsPerPage;
	}
	

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public long getTotalNumberOfItems() {
		return totalNumberOfItems;
	}

	public void setTotalNumberOfItems(long totalNumberOfItems) {
		this.totalNumberOfItems = totalNumberOfItems;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
}
