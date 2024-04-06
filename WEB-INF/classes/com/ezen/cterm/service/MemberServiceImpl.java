package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.MemberDao;
import com.ezen.cterm.vo.*;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao md;

	
	// 로그인
	@Override
	public MemberVo login(MemberVo mv) {
		return md.login(mv);
	}
	
	
	// 사원 목록 조회
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return md.list(map);
	}
	@Override
	public List<Map<String, Object>> olist(MemberSearchVo msv) {
		return md.olist(msv);
	}
	
	// 총 사원 수 조회
	@Override
	public int count(Map<String, Object> map) {
		return md.count(map);
	}

	// 사원 정보 조회
	@Override
	public Map<String, String> view(int id) {
		return md.view(id);
	}

	// 내 정보 조회
	@Override
	public Map<String, Object> viewMine(MemberVo loginVo) {
		return md.viewMine(loginVo);
	}
	
	// 글쓴이 정보 조회
	@Override
	public MemberVo writer(int id) {
		return md.writer(id);
	}
	
	
	// 사원등록(관리자)
	@Override
	public int join(MemberVo mv) {
		return md.join(mv);
	}
	
	// 사원정보 수정(관리자)
	@Override
	public int modify_admin(MemberVo mv) {
		return md.modify_admin(mv);
	}

	// 내 정보 수정
	@Override
	public int modify_mine(MemberVo loginVo) {
		return md.modify_mine(loginVo);
	}

}
