package com.ezen.cterm.vo;

public class NoticeAttachVo {
	private String newName;  // 새파일명
	private String orgName;  // 원본파일명
	private int    nNO;      // 공지사항 관리번호
	
	public String getNewName() {
		return newName;
	}
	public void setNewName(String newName) {
		this.newName = newName;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public int getnNO() {
		return nNO;
	}
	public void setnNO(int nNO) {
		this.nNO = nNO;
	}
	
}
