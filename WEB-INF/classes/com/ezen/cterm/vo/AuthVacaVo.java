package com.ezen.cterm.vo;

public class AuthVacaVo {
	private int    auVacaNO;  // 연차결재관리번호
	private int    id;        // 결재권자
	private int    vacaNO;    // 연차관리번호
	private int    state;     // 결재상태 ( 0:대기 / 1:결재대기 / 2:결재승인 / 9:결재반려 / 8: 미결재자, 결재불가 )
	
	
	
	public int getAuVacaNO() {
		return auVacaNO;
	}
	public void setAuVacaNO(int auVacaNO) {
		this.auVacaNO = auVacaNO;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getVacaNO() {
		return vacaNO;
	}
	public void setVacaNO(int vacaNO) {
		this.vacaNO = vacaNO;
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
