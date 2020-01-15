package com.buckvid.utils;

import java.util.List;

/**
 * @Description: Format of being paged
 */
public class PagedResult {
	
	private int page;			// current page number
	private int total;			// number of pages
	private long records;		// number of records
	private List<?> rows;		// content in each row
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
