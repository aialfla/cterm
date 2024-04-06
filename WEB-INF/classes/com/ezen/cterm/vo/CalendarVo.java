package com.ezen.cterm.vo;

public class CalendarVo {
	private String title;		// 캘린더 일정 이름
	private String start;	// 캘린더 일정 날짜


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	
	private int count;
	
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.title = "연차"+count+"건";
		this.count = count;
	}
	
}
