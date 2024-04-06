package com.ezen.cterm.vo;

public class VacaVo {
	private int    vacaNO;  // 연차관리번호
	private String why;     // 사유
	private String wdate;   // 작성일
	private int    state;   // 연차상태 ( 0:대기 / 1: 진행중 / 2:승인 / 8:반려 / 9:철회 )
	private int    id;      // 작성자
	
	public int getVacaNO() {
		return vacaNO;
	}
	public void setVacaNO(int vacaNO) {
		this.vacaNO = vacaNO;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
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
	public String getStateName() {
		String result = "";
		switch (state) {
		case 1 : result = "승인"; break;
		case 2 : result = "사용완료"; break;
		case 8 : result = "반려"; break;
		case 9 : result = "철회"; break;
		}
		return result;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
