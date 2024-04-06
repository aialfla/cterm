package com.ezen.cterm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.cterm.dao.MsgDao;
import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.MsgVo;
import com.ezen.cterm.vo.MsgtoVo;

@Service
public class MsgServiceImpl implements MsgService {
	
	@Autowired
	private MsgDao msgd;

	@Override
	public int countReceive(Map<String, Object> tmp) {
		return msgd.countReceive(tmp);
	}

	@Override
	public Map<String, Object> view_s(Map<String, Object> tmp) {
		return msgd.view_s(tmp);
	}

	@Override
	public int countSend(Map<String, Object> tmp) {
		return msgd.countSend(tmp);
	}

	@Override
	public Map<String, Object> view(int msgNO) {
		return msgd.view(msgNO);
	}

	@Override
	public int toggle(int msgNO) {
		return msgd.toggle(msgNO);
	}
	
	@Override
	public int write(MsgVo msgv) {
		return msgd.write(msgv);
	}
	
	@Override
	public int setReciever(MsgtoVo msgtv) {
		return msgd.setReciever(msgtv);
	}

	@Override
	public String msgState(MsgtoVo msgtv) {
		return msgd.msgState(msgtv);
	}

	@Override
	public int countNotRead(MemberVo loginVo) {
		return msgd.countNotRead(loginVo);
	}

	@Override
	public List<Map<String, Object>> list_r(Map<String, Object> tmp) {
		return msgd.list_r(tmp);
	}
	
	@Override
	public List<Map<String, Object>> list_s(Map<String, Object> tmp) {
		return msgd.list_s(tmp);
	}
	
	@Override
	public int checkAdd(MsgtoVo msgtv) {
		return msgd.checkAdd(msgtv);
	}
	
	@Override
	public MsgtoVo getMsgtoVo(MsgtoVo msgtv) {
		return msgd.getMsgtoVo(msgtv);
	}
	
	@Override
	public int delete(int msgtoNO) {
		return msgd.delete(msgtoNO);
	}
}
