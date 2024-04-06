package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.DocuDao;
import com.ezen.cterm.vo.*;

@Service
public class DocuServiceImpl implements DocuService {
	
	@Autowired
	private DocuDao dd;

	// 기안 내 목록 조회
	@Override
	public List<Map<String, Object>> list_mine(Map<String, Object> tmp) {
		return dd.list_mine(tmp);
	}
	
	// 기안 전체 목록 조회
	@Override
	public List<Map<String, Object>> list(MemberSearchVo msv) {
		return dd.list(msv);
	}
	
	// 기안 내 목록 수 조회
	@Override
	public int count_mine(Map<String, Object> tmp) {
		return dd.count_mine(tmp);
	}
	
	// 기안 전체 목록 수 조회
	@Override
	public int count(MemberSearchVo msv) {
		return dd.count(msv);
	}
	
	// 기안 내용 조회
	@Override
	public Map<String, Object> view(int docuNO) {
		return dd.view(docuNO);
	}
	
	// 기안 작성
	@Override
	public int write(DocuVo dv) {
		return dd.write(dv);
	}
	
	
	
	// 파일 등록
	@Override
	public int fileAdd(DocuAttachVo ndv) {
		return dd.fileAdd(ndv);
	}
	
	// 파일 삭제
	@Override
	public int fileDelete(String newName) { 
		return dd.fileDelete(newName);
	}
	
	// 파일 조회
	@Override public List<DocuAttachVo> Alist(int docuNO) {
		return dd.Alist(docuNO);
	}
	
	
	
	
	// 결재 목록 조회
	@Override
	public List<Map<String, Object>> authList(Map<String, Object> map) {
		return dd.authList(map); 
	}

	// 결재 기안 게시글 수 조회
	@Override
	public int count_authList(Map<String, Object> map) {
		return dd.count_authList(map);
	}
	
	
	// 기안 결재자 등록
	@Override
	public int checkAdd(AuthDocuVo adv) {
		return dd.checkAdd(adv);
	}

	// 기안 결재자 및 승인 여부 조회
	@Override
	public List<AuthDocuVo> checklist(int docuNO) {
		return dd.checklist(docuNO);
	}

	// 기안 결재자 인원 수 조회
	@Override
	public int checkCount(int docuNO) {
		return dd.checkCount(docuNO);
	}
	
	// 기안 결재자 인원 수 조회 (대기 제외) 
	@Override
	public int checkOkCount(int docuNO) {
		return dd.checkOkCount(docuNO);
	}
	
	// <결재자> 기안 승인 state:1
	@Override
	public int approve(AuthDocuVo adv) {
		return dd.approve(adv);
	}
	@Override
	public int stateTwo(int docuNO) {
		return dd.stateTwo(docuNO) ;
	}
	@Override
	public int stateOne(int docuNO) {
		return dd.stateOne(docuNO);
	}

	// <결재자> 기안 반려 state:9
	@Override
	public int reject(AuthDocuVo adv) {
		return dd.reject(adv);
	}
	@Override
	public int stateNine(int docuNO) {
		return dd.stateNine(docuNO);
	}
	
	// (기안 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	@Override
	public int changeOne(Map<String, Object> map) {
		return dd.changeOne(map);
	}
	@Override
	public int changeEight(Map<String, Object> map) {
		return dd.changeEight(map);
	}
	
	// 로그인 유저 승인 상태
	@Override
	public AuthDocuVo selectCheck(AuthDocuVo adv) {
		return dd.selectCheck(adv);
	}

	// 기안 결재자 삭제 <철회 시>
	@Override
	public int checkDelete(int docuNO) {
		return dd.checkDelete(docuNO);
	}
	
	// 기안 철회
	@Override
	public int docuBack(int docuNO) {
		return dd.docuBack(docuNO);
	}

	
	// 미결재 기안 수 조회
	@Override
	public int countDocu(MemberVo loginVo) {
		return dd.countDocu(loginVo);
	}

}
