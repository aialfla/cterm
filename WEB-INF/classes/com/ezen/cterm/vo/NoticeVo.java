package com.ezen.cterm.vo;

public class NoticeVo {
	
	private int nNO;		// 관리번호
	private String nTitle;  // 제목
	private String nNote;   // 내용
	private String wdate;   // 작성일
	private int    id;      // 사원번호
	
	public int getnNO() {
		return nNO;
	}
	public void setnNO(int nNO) {
		this.nNO = nNO;
	}
	public String getnTitle() {
		return nTitle;
	}
	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}
	public String getnNote() {
		return nNote;
	}
	public void setnNote(String nNote) {
		this.nNote = nNote;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
