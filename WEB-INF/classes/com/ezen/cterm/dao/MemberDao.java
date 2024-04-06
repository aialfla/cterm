package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.*;
import com.mysql.cj.Session;

@Repository
public class MemberDao {
	@Autowired
	private SqlSession sqlSession;
	
	// 로그인 ===================================================================================
	public MemberVo login(MemberVo mv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.memberMapper.login", mv);
	}
	// 로그아웃(컨트롤러에서)
	
	
	// 목록/내용 조회 ===================================================================================
	// 사원 목록 조회
	public List<Map<String, Object>> list(Map<String, Object> map){ 
		return sqlSession.selectList("com.ezen.cterm.mapper.memberMapper.list", map);
	}
	public List<Map<String, Object>> olist(MemberSearchVo msv) {
		return sqlSession.selectList("com.ezen.cterm.mapper.memberMapper.olist", msv);
	}

	// 총 사원 수 조회
	public int count(Map<String, Object> map) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.memberMapper.count", map);
	}
	
	// 사원 정보 조회
	public Map<String, String> view(int id) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.memberMapper.view", id);
	}
	
	// 내 정보 조회
	public Map<String, Object> viewMine(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.memberMapper.viewMine", loginVo);
	}
	
	// 글쓴이 정보 조회
	public MemberVo writer(int id) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.memberMapper.writer", id);
	}

	
	// 등록/수정 ===================================================================================
	// 사원 등록(관리자)
	public int join(MemberVo mv) {
		return sqlSession.insert("com.ezen.cterm.mapper.memberMapper.join", mv);
	}
	
	// 사원 정보 수정(관리자)
	public int modify_admin(MemberVo mv) {
		return sqlSession.update("com.ezen.cterm.mapper.memberMapper.modify_admin", mv);
	}
	
	// 내 정보 수정
	public int modify_mine(MemberVo loginVo) {
		return sqlSession.update("com.ezen.cterm.mapper.memberMapper.modify_mine", loginVo);
	}
}
