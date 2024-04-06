package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import com.ezen.cterm.vo.*;

public interface WorkService {
	// 총 근무 일 수 세기
	int countDay();
	
	// 총 근무 갯수 세기
	int count(Map<String, Object> tmp);
	
	// 근무 내용 조회
	List<WorkVo> list_mine(Map<String, Object> tmp);
	
	// 근무 내용 조회
	List<Map<String,String>> list(Map<String, Object> tmp);
	
	// 출근 시간 등록
	int addStart(MemberVo loginVo);
	
	// 출근 기록이 있는지 체크
	int checkStart(MemberVo loginVo);
	
	// 퇴근 시간 등록
	int addEnd(MemberVo loginVo);
	
	// 퇴근 기록 체크
	int checkEnd(MemberVo loginVo);
	
	// 출퇴근 시간 조회
	WorkVo checkTime(MemberVo loginVo);
	
	// 1일 총 근무시간 조회
	String dailyWork(MemberVo loginVo);
	
	// 주단위 총 근무시간 조회
	Map<String, Object> weeklyWork(MemberVo loginVo);
	
	// 퇴근시간 강제 입력 (관리자)
	int setEndtimeByAdmin(WorkVo wv);
	
}
