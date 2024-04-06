package com.ezen.cterm.vo;

public class MsgVo {
	private int    msgNO;  // 쪽지관리번호
	private int    id;     // 발신자
	private String note;   // 내용
	private String wdate;  // 작성일
	
	public int getMsgNO() {
		return msgNO;
	}
	public void setMsgNO(int msgNO) {
		this.msgNO = msgNO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getWdate() {
		return wdate;
	}
	public void setWdate(String wdate) {
		this.wdate = wdate;
	}
}
