package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.*;

@Repository
public class DocuDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 기안 내 목록
	public List<Map<String, Object>> list_mine(Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.docuMapper.list_mine", tmp);
	}
	
	// 기안 전체 목록
	public List<Map<String, Object>> list(MemberSearchVo msv) {
		return sqlSession.selectList("com.ezen.cterm.mapper.docuMapper.list", msv);
	}
	
	// 기안 내 목록 수 조회
	public int count_mine(Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.count_mine", tmp);
	}
	
	// 기안 전체 목록 수 조회
	public int count(MemberSearchVo msv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.count", msv);
	}
	
	// 기안 내용 조회
	public Map<String, Object> view(int docuNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.view", docuNO);
	}
	
	// 기안 작성
	public int write(DocuVo dv) {
		return sqlSession.insert("com.ezen.cterm.mapper.docuMapper.write", dv);
	}
	
	
//	첨부파일 ===================================================================================	
	// 파일 등록
	public int fileAdd(DocuAttachVo dav) {
		return sqlSession.insert("com.ezen.cterm.mapper.docuMapper.fileAdd", dav);
	}
	
	// 파일 삭제
	public int fileDelete(String newName) {
		return sqlSession.delete("com.ezen.cterm.mapper.docuMapper.fileDelete", newName);
	}
	
	// 파일 조회
	public List<DocuAttachVo> Alist(int docuNO) {
		return sqlSession.selectList("com.ezen.cterm.mapper.docuMapper.Alist", docuNO);
	}
	
	
	
	
//	결재_기안 ===================================================================================	
	// 결재 목록 조회
	public List<Map<String, Object>> authList(Map<String, Object> map) {
		return sqlSession.selectList("com.ezen.cterm.mapper.docuMapper.authList", map);
	}
	
	// 결재 기안 게시글 수 조회
	public int count_authList(Map<String, Object> map) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.count_authList", map);
	}
	
//	결재자 ===================================================================================	
	// 기안 결재자 등록
	public int checkAdd(AuthDocuVo adv) {
		return sqlSession.insert("com.ezen.cterm.mapper.docuMapper.checkAdd", adv);
	}
		
	// 기안 결재자 및 승인 여부 조회
	public List<AuthDocuVo> checklist(int docuNO) {
		return sqlSession.selectList("com.ezen.cterm.mapper.docuMapper.checklist", docuNO);
	}
	
	// 기안 결재자 인원 수 조회
	public int checkCount(int docuNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.checkCount", docuNO);
	}
	
	// 결재자 인원 수 조회 (대기 제외)
	public int checkOkCount(int docuNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.checkOkCount", docuNO);
	}
		
	// <결재자> 기안 승인 state:1
	public int approve(AuthDocuVo adv) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.approve", adv);
	}
	public int stateTwo(int docuNO) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.stateTwo", docuNO);
	}
	public int stateOne(int docuNO) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.stateOne", docuNO);
	}

	// <결재자> 기안 반려 state:9
	public int reject(AuthDocuVo adv) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.reject", adv);
	}
	public int stateNine(int docuNO) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.stateNine", docuNO);
	}	
	
	// (기안 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	public int changeOne(Map<String, Object> map) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.changeOne", map);
	}
	public int changeEight(Map<String, Object> map) {
		return sqlSession.update("com.ezen.cterm.mapper.docuMapper.changeEight", map);
	}
	
	// 로그인 유저 승인 상태
	public AuthDocuVo selectCheck(AuthDocuVo adv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.selectCheck", adv);
	}
	
	// 기안 결재자 삭제 <철회 시>
	public int checkDelete(int docuNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.docuMapper.checkDelete", docuNO);
	}
	
	// 기안 철회
	public int docuBack(int docuNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.docuMapper.docuBack", docuNO);
	}
	

	// 미결재 기안 수 조회
	public int countDocu(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.docuMapper.countDocu", loginVo);
	}

}
