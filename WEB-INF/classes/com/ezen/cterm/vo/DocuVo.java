package com.ezen.cterm.vo;

public class DocuVo {
	private int    docuNO;  // 기안번호
	private String title;   // 제목
	private String note;    // 내용
	private int    id;      // 작성자
	private String wdate;   // 작성일
	private int    state;   // 기안상태 ( 0:대기 / 1: 진행중 / 2:승인 / 8:반려 / 9:철회 )
	
	public int getDocuNO() {
		return docuNO;
	}
	public void setDocuNO(int docuNO) {
		this.docuNO = docuNO;
	}
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
