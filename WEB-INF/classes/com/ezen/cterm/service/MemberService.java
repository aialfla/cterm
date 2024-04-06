package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import com.ezen.cterm.vo.*;

public interface MemberService {
	
	// 로그인
	MemberVo login(MemberVo mv);	
	// 로그아웃(홈 컨트롤러)
	
	// 사원 목록 조회
	List<Map<String, Object>> list(Map<String, Object> map);
	List<Map<String, Object>> olist(MemberSearchVo msv);
	
	// 총 사원 수 조회
	int count(Map<String, Object> map);
	
	// 사원 정보 조회
	Map<String, String> view(int id);
	
	// 내 정보 조회
	Map<String, Object> viewMine(MemberVo loginVo);
	
	// 글쓴이 정보 조회
	MemberVo writer(int id);
	
	
	// 사원등록(관리자)
	int join(MemberVo mv);
	
	// 사원정보 수정(관리자)
	int modify_admin(MemberVo mv);
	
	// 내 정보 수정
	int modify_mine(MemberVo loginVo);
	
}
