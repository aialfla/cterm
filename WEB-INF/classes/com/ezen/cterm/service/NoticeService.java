package com.ezen.cterm.service;

import java.util.List;

import com.ezen.cterm.vo.*;

public interface NoticeService {
	
	
	// 공지사항 작성
	int write(NoticeVo nv);
	
	// 공지사항 조회
	NoticeVo view(int nNO);
	
	// 공지사항 수정
	int modify(NoticeVo nv);
	
	// 공지사항 삭제
	int delete(int nNO);
	int fdelete(int nNO);
	
	// 공지사항 전체 등록 글 갯수
	int count(MemberSearchVo msv);
	
	// 공지사항 목록 조회
	List<NoticeVo> list(MemberSearchVo msv);
	
	// 파일 등록
	int fileAdd(NoticeAttachVo nav);
	
	// 파일 삭제
	int fileDelete(String newName);
	
	// 파일 조회
	List<NoticeAttachVo> Alist(int nNO);
}
