package com.ezen.cterm.vo;

public class SearchVo {
	// 검색 조건
	private String searchType;   // 검색 카테고리
	private String searchVal;    // 검색어
	
	// 정렬 조건
	private String sortCol;      // 정렬 기준
	private String sortOrder;    // 정렬 순서
	
	// 페이징 조건
	private int    pageNum = 1;  // 페이지 번호
	private int    perPage = 10; // 페이지당 게시글
	private int    limitOffset;  // 
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getSortCol() {
		return sortCol;
	}
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		if (pageNum < 1) {
			this.pageNum = 1;
			this.limitOffset = 0;
		} else {
			this.pageNum = pageNum;
			this.limitOffset = (pageNum -1)* this.perPage;
		}
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	@Override
	public String toString() {
		return "SearchVo [searchType=" + searchType + ", searchVal=" + searchVal + ", sortCol=" + sortCol
				+ ", sortOrder=" + sortOrder + ", pageNum=" + pageNum + ", perPage=" + perPage + "]";
	}
}
