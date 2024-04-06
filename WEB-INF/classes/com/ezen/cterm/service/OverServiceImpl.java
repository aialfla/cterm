package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.OverDao;
import com.ezen.cterm.vo.AuthOverVo;
import com.ezen.cterm.vo.AuthVacaVo;
import com.ezen.cterm.vo.MemberSearchVo;
import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.OverVo;

@Service
public class OverServiceImpl implements OverService {
	
	@Autowired
	private OverDao od;
	
	@Override
	public List<OverVo> list_mine(Map<String, Object> tmp) {
		return od.list_mine(tmp);
	}
	
	@Override
	public List<Map<String, Object>> list(Map<String, Object> tmp) {
		return od.list(tmp);
	}

	@Override
	public Map<String, Object> view (int overNO) {
		return od.view(overNO);
	}
	
	@Override
	public int count(Map<String, Object> tmp) {
		return od.count(tmp);
	}

	@Override
	public AuthOverVo selectCheck(AuthOverVo aov) {
		return od.selectCheck(aov);
	}

	@Override
	public int checkDelete(int overNO) {
		return od.checkDelete(overNO);
	}

	@Override
	public int overBack(int overNO) {
		return od.overBack(overNO);
	}

	@Override
	public int write(OverVo ov) {
		return od.write(ov);
	}

	@Override
	public int delete(OverVo ov, int id) {
		return od.delete(ov, id);
	}

	@Override
	public String checkState(OverVo ov) {
		return od.checkState(ov);
	}

	@Override
	public List<Map<String, String>> list(OverVo ov, MemberVo mv) {
		return od.list(ov, mv);
	}
	
	@Override
	public Map<String, Object> weeklyOver(MemberVo loginVo) {
		return od.weeklyOver(loginVo);
	}

	@Override
	public int approve(AuthOverVo aov) {
		return od.approve(aov);
	}
	
	@Override
	public int stateTwo(int overNO) {
		return od.stateTwo(overNO);
	}

	@Override
	public int stateOne(int overNO) {
		return od.stateOne(overNO);
	}

	@Override
	public int reject(AuthOverVo aov) {
		return od.reject(aov);
	}
	
	@Override
	public int stateNine(int overNO) {
		return od.stateNine(overNO);
	}
	
	@Override
	public int checkAdd(AuthOverVo aov) {
		return od.checkAdd(aov);
	}
	
	@Override
	public List<AuthOverVo> checklist(int overNO) {
		return od.checklist(overNO);
	}

	@Override
	public int checkCount(int overNO) {
		return od.checkCount(overNO);
	}
	
	@Override
	public int checkOkCount(int overNO) {
		return od.checkOkCount(overNO);
	}

	@Override
	public List<Map<String, Object>> acceptList(Map<String, Object> tmp) {
		return od.acceptList(tmp);
	}
	
	@Override
	public int count_accept(Map<String, Object> tmp) {
		return od.count_accept(tmp);
	}

	@Override
	public int changeOne(Map<String, Object> map) {
		return od.changeOne(map);
	}

	@Override
	public int already(OverVo ov) {
		return od.already(ov);
	}

	@Override
	public int changeEight(Map<String, Object> map) {
		return od.changeEight(map);
	}
	
	@Override
	public int countOver(MemberVo loginVo) {
		return od.countOver(loginVo);
	}
}
