package com.ezen.cterm.vo;

public class AuthDocuVo {
	private int    auDocuNO;  // 기안결재번호
	private int    docuNO;    // 기안번호
	private int    id;        // 작성자
	private int    state;     // 결재상태 ( 0:대기 / 1:결재대기 / 2:결재승인 / 9:결재반려 / 8: 미결재자, 결재불가 )

	public int getAuDocuNO() {
		return auDocuNO;
	}
	public void setAuDocuNO(int auDocuNO) {
		this.auDocuNO = auDocuNO;
	}
	public int getDocuNO() {
		return docuNO;
	}
	public void setDocuNO(int docuNO) {
		this.docuNO = docuNO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public String getStateName() {
		String result="";
		switch (state) {
		case 1 : result = "승인"; break;
		case 2 : result = "반려"; break;
		default : result = "대기";
		}
		return result;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
