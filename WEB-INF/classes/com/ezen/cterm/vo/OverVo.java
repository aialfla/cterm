package com.ezen.cterm.vo;

public class OverVo {
	private int    overNO;  // 초과근무관리번호
	private int    id;      // 작성자
	private String date;    // 날짜
	private String start;   // 시작시간
	private String end;     // 종료시간
	private String wdate;   // 작성일
	private int    state;   // 상태 ( 0:대기 / 1: 진행중 / 2:승인 / 8:반려 / 9:철회 )
	private String title;   // 제목
	private String note;    // 내용
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getOverNO() {
		return overNO;
	}
	public void setOverNO(int overNO) {
		this.overNO = overNO;
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
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
