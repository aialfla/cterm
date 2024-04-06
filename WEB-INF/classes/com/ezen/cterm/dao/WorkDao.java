package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.AuthVacaVo;
import com.ezen.cterm.vo.MemberSearchVo;
import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.WorkVo;

@Repository
public class WorkDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 총 근무 일 수 조회
	public int countDay() {
		return 0;
	}
	
	// 총 근무 갯수 세기
	public int count(Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.workMapper.count", tmp);
	}
	
	// 내 근무 내용 조회
	public List<WorkVo> list_mine(Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.workMapper.list_mine", tmp);
	}
	
	// 근무 내용 조회
	public List<Map<String,String>> list(Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.workMapper.list", tmp);
	}
	
	// 출근 시간 등록
	public int addStart(MemberVo loginVo) {
		return sqlSession.insert("com.ezen.cterm.mapper.workMapper.addStart", loginVo);
	}
	
	// 출근 시간 체크
	public int checkStart(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.workMapper.checkStart", loginVo);
	}
	
	// 퇴근 시간 등록
	public int addEnd(MemberVo loginVo) {
		return sqlSession.insert("com.ezen.cterm.mapper.workMapper.addEnd", loginVo);
	}
	
	// 퇴근 시간 체크
	public int checkEnd(MemberVo loginVo) {
		return 0;
	}
	
	// 출퇴근 시간 출력
	public WorkVo checkTime(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.workMapper.checkTime", loginVo);
	}
	
	// 1일 총 근무시간 조회
	public String dailyWork(MemberVo loginVo) {
		return null;
	}
	
	// 주단위 총 근무시간 조회
	public Map<String, Object> weeklyWork(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.workMapper.weeklyWork", loginVo);
	}
	
	public int setEndtimeByAdmin(WorkVo wv) {
		return sqlSession.update("com.ezen.cterm.mapper.workMapper.setEndtimeByAdmin",wv);
	}
	// 월 1일부터 현재 날짜를 조회
	
}
