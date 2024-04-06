package com.ezen.cterm.vo;

public class DocuAttachVo {
	private String newName;  // 새파일명
	private String orgName;  // 원본파일명
	private int    docuNO;   // 기안 관리번호
	
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
	public int getDocuNO() {
		return docuNO;
	}
	public void setDocuNO(int docuNO) {
		this.docuNO = docuNO;
	}
	
}
