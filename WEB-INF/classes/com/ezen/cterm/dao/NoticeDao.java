package com.ezen.cterm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.cterm.vo.*;
import com.mysql.cj.Session;

@Repository
public class NoticeDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 공지사항 작성
	public int write(NoticeVo nv) {
		return sqlSession.insert("com.ezen.cterm.mapper.noticeMapper.write", nv);
	}
	
	// 공지사항 조회
	public NoticeVo view(int nNO) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.noticeMapper.view", nNO);
	}
	
	// 공지사항 수정
	public int modify(NoticeVo nv) {
		return sqlSession.update("com.ezen.cterm.mapper.noticeMapper.modify", nv);
	}
	
	// 공지사항 삭제
	public int delete(int nNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.noticeMapper.delete", nNO);
	}
	public int fdelete(int nNO) {
		return sqlSession.delete("com.ezen.cterm.mapper.noticeMapper.fdelete", nNO);
	}
	
	// 공지사항 전체 등록 글 갯수
	public int count(MemberSearchVo msv) {
		return sqlSession.selectOne("com.ezen.cterm.mapper.noticeMapper.count", msv);
	}
	
	// 공지사항 목록 조회
	public List<NoticeVo> list(MemberSearchVo msv) {
		return sqlSession.selectList("com.ezen.cterm.mapper.noticeMapper.list", msv);
	}
	

	
	// 파일 등록
	public int fileAdd(NoticeAttachVo nav) {
		return sqlSession.insert("com.ezen.cterm.mapper.noticeMapper.fileAdd", nav);
	}
	
	// 파일 삭제
	public int fileDelete(String newName) {
		return sqlSession.delete("com.ezen.cterm.mapper.noticeMapper.fileDelete", newName);
	}
	
	// 파일 조회
	public List<NoticeAttachVo> Alist(int nNO) {
		return sqlSession.selectList("com.ezen.cterm.mapper.noticeMapper.Alist", nNO);
	}
	
}
