package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import com.ezen.cterm.vo.*;

public interface MsgService {
	// 받은 쪽지 수 총 조회
	int countReceive(Map<String, Object> tmp);
	
	// 보낸 쪽지 수 총 조회
	int countSend(Map<String, Object> tmp);
	
	// 쪽지 읽기
	Map<String, Object> view(int msgNO);

	// 발신 쪽지 읽기
	Map<String, Object> view_s(Map<String, Object> tmp);
	
	// 쪽지 읽음상태 전환
	int toggle(int msgNO);
	
	// 쪽지 쓰기
	int write(MsgVo msgv);
	
	// 수신자 설정하기
	int setReciever(MsgtoVo msgtv);
	
	// 쪽지 상태 조회
	String msgState(MsgtoVo msgtv);
	
	// 안읽은 쪽지 수 조회
	int countNotRead(MemberVo loginVo);
	
	// 받은 쪽지 목록 조회
	public List<Map<String, Object>> list_r(Map<String, Object> tmp);
	
	// 보낸 쪽지 목록 조회
	public List<Map<String, Object>> list_s(Map<String, Object> tmp);
	
	// 수신자 등록 checkAdd
	public int checkAdd(MsgtoVo msgtv);
	
	// 
	public MsgtoVo getMsgtoVo(MsgtoVo msgtv);
	
	// 미수신 쪽지 삭제
	int delete(int msgtoNO);
	
}
