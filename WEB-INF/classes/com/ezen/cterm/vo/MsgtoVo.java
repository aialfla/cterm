package com.ezen.cterm.vo;

public class MsgtoVo {
	private int    msgtoNO;  // 수신자관리번호
	private int    msgNO;    // 쪽지관리번호
	private int    id;       // 수신자
	private int    state;    // 상태 ( 0:읽지않음 / 1:읽음 )
	
	public int getMsgtoNO() {
		return msgtoNO;
	}
	public void setMsgtoNO(int msgtoNO) {
		this.msgtoNO = msgtoNO;
	}
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
