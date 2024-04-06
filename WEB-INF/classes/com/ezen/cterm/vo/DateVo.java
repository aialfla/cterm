package com.ezen.cterm.vo;

public class DateVo {
	private int    dateNO;  // 연차 일자 관리번호
	private String date;	// 연차 날짜
	private int    vacaNO;	// 연차 관리번호
	private int    state;   // 연차상태 <1: 사용 0: 미사용>
	
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getDateNO() {
		return dateNO;
	}
	public void setDateNO(int dateNO) {
		this.dateNO = dateNO;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getVacaNO() {
		return vacaNO;
	}
	public void setVacaNO(int vacaNO) {
		this.vacaNO = vacaNO;
	}

}
