package com.ezen.cterm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.NoticeDao;
import com.ezen.cterm.vo.NoticeVo;
import com.ezen.cterm.vo.MemberSearchVo;
import com.ezen.cterm.vo.NoticeAttachVo;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeDao nd;
	
	@Override
	public int write(NoticeVo nv) {
		return nd.write(nv);
	}

	@Override
	public NoticeVo view(int nNO) {
		return nd.view(nNO);
	}

	@Override
	public int modify(NoticeVo nv) {
		return nd.modify(nv);
	}

	@Override
	public int delete(int nNO) {
		return nd.delete(nNO);
	}
	
	@Override
	public int fdelete(int nNO) {
		return nd.fdelete(nNO);
	}

	@Override
	public int count(MemberSearchVo msv) {
		return nd.count(msv);
	}

	@Override
	public List<NoticeVo> list(MemberSearchVo msv) {
		return nd.list(msv);
	}

	@Override
	public int fileAdd(NoticeAttachVo nav) {
		return nd.fileAdd(nav);
	 }
	  
	@Override
	public int fileDelete(String newName) { 
		 return nd.fileDelete(newName);
	}
	  
	@Override public List<NoticeAttachVo> Alist(int nNO) {
		return nd.Alist(nNO);
	}
}
