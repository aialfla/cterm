package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.VacaVo;
import com.ezen.cterm.vo.DateVo;
import com.ezen.cterm.vo.AuthVacaVo;
import com.ezen.cterm.vo.CalendarVo;

@Repository
public class VacaDao {
	
	@Autowired
	private SqlSession sqlSession;
	// 캘린더
	public List<CalendarVo> event() {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.event");
	}
	public List<Map<String, Object>> todayVaca(String day) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.todayVaca", day);
	}
	
	// main total d-day
	public int dday() {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.dday");
	}
	
	// 오늘 날짜 출력 <today>
	public Map<String, Object> today() {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.today");
	}
	
	// 전 사원 연차 부여 <입사일 ~ 현재일>
	public int addVacation() {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.addVacation");
	}
	
	// <loginUser> 최근 연차 신청 날짜 조회
	public Map<String, Object> checkLatestVaca(int id) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.checkLatestVaca", id);
	}
	
	// <loginUser> 입사일 ~ TODAY 사용 연차 수 총 조회
	public List<Map<String, Object>> allCount(int id) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.allCount", id);
	}
	
	// <loginUser> 현재 날짜 기준 잔여 연차(총 연차 - 사용 연차) 수 조회
	public int countUsed(VacaVo vv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.countUsed", vv);
	}
	
	// <loginUser> 연차 신청 목록 조회
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.list", map);
	}
	
	// histroy, 연차 승인 목록 조회
	public List<Map<String, Object>> history(int id) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.history", id);
	}
	
	// 연차 신청 글 조회
	public Map<String, Object> view(int vacaNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.view", vacaNO);
	}

	
	// <loginUser> 연차 신청일자 中 <state:사용> 수 조회
	public int count(int state) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.count", state);
	}
	
	// 전 사원 연차 목록 조회
	public List<Map<String, Object>> Alist() {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.Alist");
	} 
	
	
	// 연차 신청
	public int write(VacaVo vv) {
		return sqlSession.insert("com.ezen.cterm.mapper.vacaMapper.write", vv);
	}
	
	// 연차일자 등록
	public int vacaAdd(DateVo dv) {
		return sqlSession.insert("com.ezen.cterm.mapper.vacaMapper.vacaAdd", dv);
	}
	
	// 토·일 연차 삭제 weekend
	public int weekend(int vacaNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.vacaMapper.weekend", vacaNO);
	}
	
	// 연차 결재자 등록 checkAdd
	public int checkAdd(AuthVacaVo avv) {
		return sqlSession.insert("com.ezen.cterm.mapper.vacaMapper.checkAdd", avv);
	}
	
	// 연차 결재자 삭제 <철회 시> checkDelete
	public int checkDelete(int vacaNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.vacaMapper.checkDelete", vacaNO);
	}
	
	// 연차 신청일자 조회
	public List<DateVo> daylist(int vacaNO) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.daylist", vacaNO);
	}
	
	// 연차 철회
	public int vacaBack(int vacaNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.vacaMapper.vacaBack", vacaNO);
	}
	
	// 연차 결재자 및 승인 여부 조회
	public List<AuthVacaVo> checklist(int vacaNO) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.checklist", vacaNO);
	}
	
	// 연차 결재자 인원 수 조회
	public int checkCount(int vacaNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.checkCount", vacaNO);
	}
	
	// (대기 제외) 결재자 인원 수 조회 
	public int checkOkCount(int vacaNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.checkOkCount", vacaNO);
	}
		
	// <결재자> 연차 승인 state:1
	public int approve(AuthVacaVo avv) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.approve", avv);
	}
	public int stateTwo(int vacaNO) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.stateTwo", vacaNO);
	}
	public int stateOne(int vacaNO) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.stateOne", vacaNO);
	}
	
	// <결재자> 연차 반려 state:9
	public int reject(AuthVacaVo avv) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.reject", avv);
	}
	public int stateNine(int vacaNO) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.stateNine", vacaNO);
	}
	
	// <결재PAGE> 연차 신청 리스트 조회 acceptList
	public List<Map<String, Object>> acceptList(Map<String, Object> map) {
		return sqlSession.selectList("com.ezen.cterm.mapper.vacaMapper.acceptList", map);
	}
	
	// (연차 신청) 결재권자 0: 결재 대기 → 1: 결재 승인 changeOne
	public int changeOne(Map<String, Object> map) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.changeOne", map);
	}
	public int changeEight(Map<String, Object> map) {
		return sqlSession.update("com.ezen.cterm.mapper.vacaMapper.changeEight", map);
	}
	
	// 로그인유저 승인 상태 selectCheck
	public AuthVacaVo selectCheck(AuthVacaVo avv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.selectCheck", avv);
	}
	
	// <loginUser> 연차 신청 글 수 조회
	public int paging(Map<String, Object> map) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.paging", map);
	}
	public int avpaging(Map<String, Object> map) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.avpaging", map);
	}
	public int mpaging(Map<String, Object> map) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.mpaging", map);
	}
	
	
	// 미결재 기안 수 조회
	public int countVaca(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.vacaMapper.countVaca", loginVo);
	}
	
}