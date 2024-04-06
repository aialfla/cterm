package com.ezen.cterm.vo;

public class AuthOverVo {
	private int    auOverNO;  // 초과근무결재관리번호
	private int    overNO;    // 초과근무관리번호
	private int    id;        // 결재권자
	private int    state;     // 결재상태 ( 0:대기 / 1:결재대기 / 2:결재승인 / 9:결재반려 / 8:반려시 - )
	
	public int getAuOverNO() {
		return auOverNO;
	}
	public void setAuOverNO(int auOverNO) {
		this.auOverNO = auOverNO;
	}
	public int getId() {
		return id;
	}
	public int getOverNO() {
		return overNO;
	}
	public void setOverNO(int overNO) {
		this.overNO = overNO;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
