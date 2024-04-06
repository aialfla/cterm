package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import com.ezen.cterm.vo.*;

public interface VacaService {
	
	// 캘린더
	List<CalendarVo> event();
	List<Map<String, Object>> todayVaca(String day);
	
	// main total d-day
	int dday();
	
	// 오늘 날짜 출력 <today>
	Map<String, Object> today();
		
	// 전 사원 연차 부여 <입사일 ~ 현재일>
	int addVacation();
	
	
	// <loginUser> 최근 연차 신청 날짜 조회
	Map<String, Object> checkLatestVaca(int id);
	
	// <loginUser> 입사일 ~ TODAY 사용 연차 수 총 조회
	List<Map<String, Object>> allCount(int id);
	
	// <loginUser> 현재 날짜 기준 잔여 연차(총 연차 - 사용 연차) 수 조회
	int countUsed(VacaVo vv);
	
	// <loginUser> 연차 신청 목록 조회
	List<Map<String, Object>> list(Map<String, Object> map);
	
	// histroy, 연차 승인 목록 조회
	List<Map<String, Object>> history(int id);
	
	// 연차 신청 글 조회
	Map<String, Object> view(int vacaNO);
	
	// <loginUser> 연차 신청일자 中 <state:사용> 수 조회
	int count(int state);
	
	// 전 사원 연차 목록 조회
	List<Map<String, Object>> Alist(); 
	
	
	// 연차 신청
	int write(VacaVo vv);
	
	// 연차일자 등록
	int vacaAdd(DateVo dv);
	
	// 토·일 연차 삭제 weekend
	int weekend(int vacaNO);
	
	// 연차 결재자 등록 checkAdd
	int checkAdd(AuthVacaVo avv);
	
	// 연차 결재자 삭제 <철회 시> checkDelete
	int checkDelete(int vacaNO);
	
	// 연차 신청일자 조회
	List<DateVo> daylist(int vacaNO);
	
	// 연차 철회
	int vacaBack(int vacaNO);
	
	// 연차 결재자 및 승인 여부 조회
	List<AuthVacaVo> checklist(int vacaNO);
	
	// 연차 결재자 인원 수 조회
	int checkCount(int vacaNO);
	
	// (대기 제외) 결재자 인원 수 조회 checkOkCount
	int checkOkCount(int vacaNO);

		
	// <결재자> 연차 승인
	int approve(AuthVacaVo avv);
	int stateTwo(int vacaNO);
	int stateOne(int vacaNO);
	
	// <결재자> 연차 반려
	int reject(AuthVacaVo avv);
	int stateNine(int vacaNO);
	
	// <결재PAGE> 연차 신청 리스트 조회 acceptList
	List<Map<String, Object>> acceptList(Map<String, Object> map);
	
	// (연차 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	int changeOne(Map<String, Object> map);
	int changeEight(Map<String, Object> map);
	
	// 로그인유저 승인 상태 selectCheck
	AuthVacaVo selectCheck(AuthVacaVo avv);
	
	// <loginUser> 연차 신청 글 수 조회
	int paging(Map<String, Object> map);
	int avpaging(Map<String, Object> map);
	int mpaging(Map<String, Object> map);
	
	
	// 미결재 연차 수 조회
	int countVaca(MemberVo loginVo);
}

