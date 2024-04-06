package com.ezen.cterm.vo;

public class MemberSearchVo {
	// 검색 조건
	private int    dept;		//부서
	private int    duty;		//직급
	private int    state;		//문서 결재상태
	private int    astate;		//결재자 결재상태
	private String name;		//이름
	private String startday;
	private String endday;
	
	// 페이징 조건
	private int    pageNO = 1;		// 페이지 번호
	private int    perPage = 10; 	// 페이지당 게시글 표출 수
	private int    limitOffset;		// 페이지당 게시글 시작 범위
	
	
	public int getDept() {
		return dept;
	}
	public void setDept(int dept) {
		this.dept = dept;
	}
	public int getDuty() {
		return duty;
	}
	public void setDuty(int duty) {
		this.duty = duty;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if (name == null) name = "";
		this.name = name;
	}
	public int getPageNO() {
		return pageNO;
	}
	
	public void setPageNO(int pageNO) {
		if (pageNO < 1) {
			this.pageNO = 1;
			this.limitOffset = 0;
		} else {
			this.pageNO = pageNO;
			this.limitOffset = (pageNO -1)* this.perPage;
		}
	}
	public int getLimitOffset() {
		return limitOffset;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	
	public String getStartday() {
		return startday;
	}
	public void setStartday(String startday) {
		this.startday = startday;
	}
	public String getEndday() {
		return endday;
	}
	public void setEndday(String endday) {
		this.endday = endday;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getAstate() {
		return astate;
	}
	public void setAstate(int astate) {
		this.astate = astate;
	}
	
	@Override
	public String toString() {
		return "MemberSearchVo "
			+ "[ dept=" + dept + ", duty=" + duty + ", astate=" + astate + ", name=" + name + ", pageNO=" + pageNO + ", perPage=" + perPage + ", limitOffset=" + limitOffset + "]";
	}
	
}
