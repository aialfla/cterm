package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.AuthOverVo;
import com.ezen.cterm.vo.AuthVacaVo;
import com.ezen.cterm.vo.DateVo;
import com.ezen.cterm.vo.MemberSearchVo;
import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.OverVo;

@Repository
public class OverDao {
	@Autowired
	private SqlSession sqlSession;
	
	// 전체 초과 근무 갯수 세기
	public int count (Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.count", tmp);
	}
	
	// 내 초과 근무 조회
	public List<OverVo> list_mine (Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.overMapper.list_mine", tmp);
	}
	
	// 전체 초과 근무 조회
	public List<Map<String, Object>> list (Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.overMapper.list", tmp);
	}
	
	// 초과 근무 신청
	public int write(OverVo ov) {
		return sqlSession.insert("com.ezen.cterm.mapper.overMapper.write", ov);
	}
	
	// 초과근무 내용 조회
	public Map<String, Object> view(int overNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.view", overNO);
	}
	
	// 주간 초과 근무
	public Map<String, Object> weeklyOver (MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.weeklyOver", loginVo);
	}
	
	// 초과 근무 철회
	public int delete(OverVo ov, int id) {
		return 0;
	}
	
	// 초과 근무 승인 여부 조회
	public String checkState(OverVo ov) {
		return null;
	}
	
	// 초과 근무 신청 내역 조회
	public List<Map<String, String>> list (OverVo ov, MemberVo mv) {
		return null;
	}
	
	// 초과 근무 승인 : state 1
	public int approve(AuthOverVo aov) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.approve", aov);
	}
	public int stateTwo(int overNO) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.stateTwo", overNO);
	}
	public int stateOne(int overNO) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.stateOne", overNO);
	}
	
	
	// 초과 근무 반려 state:9
	public int reject(AuthOverVo aov) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.reject", aov);
	}
	public int stateNine(int overNO) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.stateNine", overNO);
	}
	
	// 연차 결재자 등록 checkAdd
	public int checkAdd(AuthOverVo aov) {
		return sqlSession.insert("com.ezen.cterm.mapper.overMapper.checkAdd", aov);
	}
	
	// 연차 결재자 및 승인 여부 조회
	public List<AuthOverVo> checklist(int overNO) {
		return sqlSession.selectList("com.ezen.cterm.mapper.overMapper.checklist", overNO);
	}
	
	// 연차 결재자 인원 수 조회
	public int checkCount(int overNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.checkCount", overNO);
	}
	
	// (대기 제외) 결재자 인원 수 조회 
	public int checkOkCount(int overNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.checkOkCount", overNO);
	}
		
	// <결재PAGE> 연차 신청 리스트 조회 acceptList
	public List<Map<String, Object>> acceptList(Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.overMapper.acceptList", tmp);
	}
	
	public int count_accept(Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.count_accept", tmp);
	}
	
	// 로그인유저 승인 상태 selectCheck
	public AuthOverVo selectCheck(AuthOverVo aov) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.selectCheck", aov);
	}

	// (연차 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	public int changeOne(Map<String, Object> map) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.changeOne", map);
	}
	public int changeEight(Map<String, Object> map) {
		return sqlSession.update("com.ezen.cterm.mapper.overMapper.changeEight", map);
	}
	
	// 연차 결재자 삭제 <철회 시> checkDelete
	public int checkDelete(int overNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.overMapper.checkDelete", overNO);
	}
	
	// 연차 철회
	public int overBack(int overNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.overMapper.overBack", overNO);
	}
	
	// 이미 초과근무가 신청된 날짜인지 체크
	public int already(OverVo ov) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.already", ov);
	}
	
	// 미결재 기안 수 조회
	public int countOver(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.overMapper.countOver", loginVo);
	}
}
