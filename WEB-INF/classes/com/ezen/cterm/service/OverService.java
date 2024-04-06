package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import com.ezen.cterm.vo.*;

public interface OverService {
	// 내 초과 근무 조회
	List<OverVo> list_mine (Map<String, Object> tmp);
	
	// 내 초과 근무 조회
	List<Map<String, Object>> list (Map<String, Object> tmp);
	
	// 초과 근무 내용 조회
	Map<String, Object> view (int OverNO);
	
	// 초과 근무 신청
	int write(OverVo ov);
	
	// 초과 근무 철회
	int delete(OverVo ov, int id);
	
	// 초과 근무 승인 여부 조회
	String checkState(OverVo ov);
	
	// 초과 근무 신청 내역 조회
	List<Map<String, String>> list (OverVo ov, MemberVo mv);
	
	// 주간 초과 근무
	Map<String, Object> weeklyOver(MemberVo loginVo);
	
	// 초과 근무 승인
	int approve(AuthOverVo aov);
	int stateTwo(int vacaNO);
	int stateOne(int vacaNO);
	
	// 초과 근무 반려
	int reject(AuthOverVo aov);
	int stateNine(int overNO);
	
	// 연차 결재자 등록 checkAdd
	int checkAdd(AuthOverVo aov);
	
	// 연차 결재자 및 승인 여부 조회
	List<AuthOverVo> checklist(int overNO);
	
	// 연차 결재자 인원 수 조회
	int checkCount(int overNO);
	
	// (대기 제외) 결재자 인원 수 조회 checkOkCount
	int checkOkCount(int overNO);
	
	// <결재PAGE> 초과근무 신청 리스트 조회 acceptList
	List<Map<String, Object>> acceptList(Map<String, Object> tmp);
	
	// accept List count
	int count_accept(Map<String, Object> tmp);
	
	// 로그인유저 승인 상태 selectCheck
	AuthOverVo selectCheck(AuthOverVo aov);
	
	// (연차 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	int changeOne(Map<String, Object> map);
	int changeEight(Map<String, Object> map);
	
	// 연차 결재자 삭제 <철회 시> checkDelete
	int checkDelete(int overNO);
	
	// 연차 철회
	int overBack(int overNO);
	
	// 이미 초과근무가 작성된 날짜인지 체크
	int already(OverVo ov);

	// 초과근무 갯수세기
	int count(Map<String, Object> tmp);
	
	
	// 미결재 초과근무 수 조회
	int countOver(MemberVo loginVo);
}
