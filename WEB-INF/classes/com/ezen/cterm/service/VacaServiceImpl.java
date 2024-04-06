package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.VacaDao;
import com.ezen.cterm.vo.*;


@Service
public class VacaServiceImpl implements VacaService {

	@Autowired
	private VacaDao vd;
	
	@Override
	public List<CalendarVo> event() {
		return vd.event();
	}
	@Override
	public List<Map<String, Object>> todayVaca(String day) {
		return vd.todayVaca(day);
	}
	
	@Override
	public int dday() {
		return vd.dday();
	}
	
	@Override
	public Map<String, Object> today() {
		return vd.today();
	}
	
	@Override
	public int addVacation() {
		return vd.addVacation();
	}

	@Override
	public Map<String, Object> checkLatestVaca(int id) {
		return vd.checkLatestVaca(id);
	}

	@Override
	public List<Map<String, Object>> allCount(int id) {
		return vd.allCount(id);
	}

	@Override
	public int countUsed(VacaVo vv) {
		return vd.countUsed(vv);
	}

	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return vd.list(map);
	}
	
	@Override
	public List<Map<String, Object>> history(int id) {
		return vd.history(id);
	}
	
	@Override
	public Map<String, Object> view(int vacaNO) {
		return vd.view(vacaNO);
	}

	@Override
	public int count(int state) {
		return vd.count(state);
	}

	@Override
	public List<Map<String, Object>> Alist() {
		return vd.Alist();
	}

	@Override
	public int write(VacaVo vv) {
		return vd.write(vv);
	}

	@Override
	public int vacaAdd(DateVo dv) {
		return vd.vacaAdd(dv);
	}

	@Override
	public int weekend(int vacaNO) {
		return vd.weekend(vacaNO);
	}
	
	@Override
	public int checkAdd(AuthVacaVo avv) {
		return vd.checkAdd(avv);
	}
	
	@Override
	public int checkDelete(int vacaNO) {
		return vd.checkDelete(vacaNO);
	}
	
	@Override
	public List<DateVo> daylist(int vacaNO) {
		return vd.daylist(vacaNO);
	}

	@Override
	public int vacaBack(int vacaNO) {
		return vd.vacaBack(vacaNO);
	}

	@Override
	public List<AuthVacaVo> checklist(int vacaNO) {
		return vd.checklist(vacaNO);
	}

	@Override
	public int checkCount(int vacaNO) {
		return vd.checkCount(vacaNO);
	}
	
	@Override
	public int checkOkCount(int vacaNO) {
		return vd.checkOkCount(vacaNO);
	}
	
	@Override
	public int approve(AuthVacaVo avv) {
		return vd.approve(avv);
	}
	
	@Override
	public int stateTwo(int vacaNO) {
		return vd.stateTwo(vacaNO);
	}
	
	@Override
	public int stateOne(int vacaNO) {
		return vd.stateOne(vacaNO);
	}


	@Override
	public int reject(AuthVacaVo avv) {
		return vd.reject(avv);
	}
	
	@Override
	public int stateNine(int vacaNO) {
		return vd.stateNine(vacaNO);
	}

	@Override
	public List<Map<String, Object>> acceptList(Map<String, Object> map) {
		return vd.acceptList(map);
	}

	@Override
	public int changeOne(Map<String, Object> map) {
		return vd.changeOne(map);
	}
	
	@Override
	public int changeEight(Map<String, Object> map) {
		return vd.changeEight(map);
	}

	@Override
	public AuthVacaVo selectCheck(AuthVacaVo avv) {
		return vd.selectCheck(avv);
	}
	
	@Override
	public int paging(Map<String, Object> map) {
		return vd.paging(map);
	}
	
	@Override
	public int avpaging(Map<String, Object> map) {
		return vd.avpaging(map);
	}
	
	@Override
	public int mpaging(Map<String, Object> map) {
		return vd.mpaging(map);
	}
	
	@Override
	public int countVaca(MemberVo loginVo) {
		return vd.countVaca(loginVo);
	}
	
	

}
