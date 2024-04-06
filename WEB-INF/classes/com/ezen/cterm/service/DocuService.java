package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import com.ezen.cterm.vo.*;

public interface DocuService {
	
	// 기안 내 목록 조회
	List<Map<String, Object>> list_mine(Map<String, Object> tmp);

	// 기안 전체 목록 조회
	List<Map<String, Object>> list(MemberSearchVo msv);
	
	// 기안 내 목록 수 조회
	int count_mine(Map<String, Object> tmp);
		
	// 기안 전체 목록 수 조회
	int count(MemberSearchVo msv);
	
	// 기안 내용 조회
	Map<String, Object> view(int docuNO);
	
	// 기안 작성
	int write(DocuVo dv);
	
	
	// 파일 등록
	int fileAdd(DocuAttachVo ndv);
	
	// 파일 삭제
	int fileDelete(String newName);
	
	// 파일 조회
	List<DocuAttachVo> Alist(int docuNO);
	
	
	
	
	
	// 결재 목록 조회
	List<Map<String, Object>> authList(Map<String, Object> map);
	
	// 결재 기안 게시글 수 조회
	int count_authList(Map<String, Object> map);
	
	
	// 기안 결재자 등록
	int checkAdd(AuthDocuVo adv);
	
	// 기안 결재자 및 승인 여부 조회
	List<AuthDocuVo> checklist(int docuNO);
	
	// 기안 결재자 인원 수 조회
	int checkCount(int docuNO);
	
	// 결재자 인원 수 조회 (대기 제외) 
	int checkOkCount(int docuNO);
	
	// <결재자> 기안 승인 state:1
	int approve(AuthDocuVo adv);
	int stateTwo(int docuNO);
	int stateOne(int docuNO);
	
	// <결재자> 기안 반려 state:9
	int reject(AuthDocuVo adv);
	int stateNine(int docuNO);
	
	// (기안 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	int changeOne(Map<String, Object> map);
	int changeEight(Map<String, Object> map);
	
	// 로그인유저 승인 상태 selectCheck
	AuthDocuVo selectCheck(AuthDocuVo adv);
	
	// 기안 결재자 삭제 <철회 시>
	int checkDelete(int docuNO);
	
	// 기안 철회
	int docuBack(int docuNO);
	
	
	// 미결재 기안 수 조회
	int countDocu(MemberVo loginVo);
}
