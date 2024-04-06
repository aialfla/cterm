package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.WorkDao;
import com.ezen.cterm.vo.WorkVo;
import com.ezen.cterm.vo.MemberSearchVo;
import com.ezen.cterm.vo.MemberVo;

@Service
public class WorkServiceImpl implements WorkService {

	@Autowired
	private WorkDao wd;
	
	@Override
	public int countDay() {
		return wd.countDay();
	}
	
	@Override
	public int count(Map<String, Object> tmp) {
		return wd.count(tmp);
	}

	@Override
	public List<WorkVo> list_mine(Map<String, Object> tmp) {
		return wd.list_mine(tmp);
	}
	
	@Override
	public List<Map<String,String>> list(Map<String, Object> tmp) {
		return wd.list(tmp);
	}

	@Override
	public int addStart(MemberVo loginVo) {
		return wd.addStart(loginVo);
	}
	
	@Override
	public int checkStart(MemberVo loginVo) {
		return wd.checkStart(loginVo);
	}

	@Override
	public int addEnd(MemberVo loginVo) {
		return wd.addEnd(loginVo);
	}

	@Override
	public int checkEnd(MemberVo loginVo) {
		return wd.checkEnd(loginVo);
	}
	
	@Override
	public int setEndtimeByAdmin(WorkVo wv) {
		return wd.setEndtimeByAdmin(wv);
	}

	@Override
	public WorkVo checkTime(MemberVo loginVo) {
		return wd.checkTime(loginVo);
	}
	
	@Override
	public String dailyWork(MemberVo loginVo) {
		return wd.dailyWork(loginVo);
	}

	@Override
	public Map<String, Object> weeklyWork(MemberVo loginVo) {
		return wd.weeklyWork(loginVo);
	}
}
