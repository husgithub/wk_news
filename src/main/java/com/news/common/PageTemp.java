package com.news.common;

public class PageTemp {
	
	private int pageIndex = 1;    //当前页
	private int pageSize = 10;    //每页数据条数
	private int total = 0;        //数据总条数
	private int totalPage = 0;        //总页数
	
	
	public PageTemp(int pageIndex, int pageSize, int total) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.total = total;
		totalPage = (int) Math.ceil(total/pageSize);
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * 根据页码获取该条记录所在行
	 * @return
	 */
	public int getRowIndex(){
		if(totalPage == 0){
			return 0;
		}
		return (pageIndex - 1)*pageSize;
	}
	
}
