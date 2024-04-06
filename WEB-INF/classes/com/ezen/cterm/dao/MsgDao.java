package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.AuthOverVo;
import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.MsgVo;
import com.ezen.cterm.vo.MsgtoVo;

@Repository
public class MsgDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 받은 쪽지 갯수 세기
	public int countReceive(Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.count_r", tmp);
	}
	
	// 보낸 쪽지 갯수 세기
	public int countSend(Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.count_s", tmp);
	}
	
	// 쪽지 읽기
	public Map<String, Object> view(int msgNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.view", msgNO);
	}
	
	// 쪽지 읽기
	public Map<String, Object> view_s(Map<String, Object> tmp) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.view_s", tmp);
	}
	
	// 쪽지 읽음 상태 전환
	public int toggle(int msgtoNO) {
		return sqlSession.update("com.ezen.cterm.mapper.msgMapper.toggle", msgtoNO);
	}
	
	// 쪽지 쓰기
	public int write(MsgVo msgv) {
		return sqlSession.insert("com.ezen.cterm.mapper.msgMapper.write", msgv);
	}
	
	// 수신자 설정하기
	public int setReciever(MsgtoVo msgtv) {
		return sqlSession.insert("com.ezen.cterm.mapper.msgMapper.setReciever", msgtv);
	}
	
	// 쪽지 상태 조회
	public String msgState(MsgtoVo msgtv) { // 이거 그냥 게터 쓰면 되는거 아닌가
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.state", msgtv);
	}
	
	// 안읽은 쪽지 수 조회
	public int countNotRead(MemberVo loginVo) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.count_nr", loginVo);
	}
	
	// 받은 쪽지 목록 조회
	public List<Map<String, Object>> list_r(Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.msgMapper.list_r", tmp);
	}
	
	// 보낸 쪽지 목록 조회
	public List<Map<String, Object>> list_s(Map<String, Object> tmp) {
		return sqlSession.selectList("com.ezen.cterm.mapper.msgMapper.list_s", tmp);
	}
	
	// 수신자 등록 checkAdd
	public int checkAdd(MsgtoVo msgtv) {
		return sqlSession.insert("com.ezen.cterm.mapper.msgMapper.checkAdd", msgtv);
	}
	
	//
	public MsgtoVo getMsgtoVo(MsgtoVo msgtv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.msgMapper.get_msgtoVO", msgtv);
	}
	
	// 미수신 쪽지 삭제
	public int delete(int msgtoNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.msgMapper.delete",msgtoNO);
	}
}
