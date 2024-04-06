package com.ezen.cterm.vo;

public class WorkVo {
	private int    workNO;  // 근무관리번호
	private int    id;      // 사원번호
	private String date;    // 날짜
	private String start;   // 출근시간
	private String end;     // 퇴근시간
	
	public int getWorkNO() {
		return workNO;
	}
	public void setWorkNO(int workNO) {
		this.workNO = workNO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
