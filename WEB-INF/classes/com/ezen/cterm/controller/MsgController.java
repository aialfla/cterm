package com.ezen.cterm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ezen.cterm.service.*;
import com.ezen.cterm.vo.*;

@RequestMapping(value = "/msg")
@Controller
public class MsgController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	@Autowired
	private MsgService msgService;
	
	@Autowired
	private MemberService memberService;
	
	// 페이지 호출
	
	// 쪽지_수신쪽지목록
	@RequestMapping(value="/list_r.do", method=RequestMethod.GET)
	public String list_r(HttpSession session, Model model, MemberSearchVo msv, String name_s) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		
		
		tmp.put("id", loginVo.getId());
		tmp.put("dept", loginVo.getDept());
		tmp.put("duty", loginVo.getDuty());
		tmp.put("startday", msv.getStartday());
		tmp.put("endday", msv.getEndday());
		tmp.put("searchDept", msv.getDept());
		tmp.put("searchDuty", msv.getDuty());
		tmp.put("searchName", name_s);
		tmp.put("limitOffset", msv.getLimitOffset());
		tmp.put("perPage", msv.getPerPage());
		
		List<Map<String, Object>> list = msgService.list_r(tmp);
		
		for(Map<String, Object> item : list)
		{
//			System.out.println(item.toString());
			String result = "";
			switch (String.valueOf(item.get("duty_s"))) {
			case "0" : result = "관리자"; break;
			case "1" : result = "대표"; break;
			case "2" : result = "부장"; break;
			case "3" : result = "팀장"; break;
			default : result = "사원";
			}
			item.put("dutyName", result);
			
			switch (String.valueOf(item.get("dept_s"))) {
			case "1" : result = "기획부"; break;
			case "2" : result = "디자인부"; break;
			case "3" : result = "개발부"; break;
			default : result = "관리부";
			}
			item.put("deptName", result);
		}
		
		// 페이징
		
		// 전체 게시글 수
		int total = msgService.countReceive(tmp);
		
		// 전체 페이지 갯수 계산
		int MaxPage = total / msv.getPerPage() + 1;
		if( total % msv.getPerPage() == 0 ){ MaxPage--;}
		
		// 시작 블럭 번호 계산
		int startBlock = ( msv.getPageNO() / 10 ) * 10 + 1;	// 10페이지 초과
		if( msv.getPageNO() % 10 == 0 ){ startBlock -= 10;}	// 10페이지 미만
		
		// 끝 블럭 번호 계산	
		int endBlock = startBlock + 10 - 1;
		if( endBlock >= MaxPage ) { endBlock = MaxPage; }
		
		model.addAttribute("pageNO", msv.getPageNO());
		model.addAttribute("MaxPage", MaxPage);
		model.addAttribute("startBlock", startBlock);
		model.addAttribute("endBlock", endBlock);
		
		System.out.println("total : " + total);
		System.out.println("pageNO : " + msv.getPageNO());
		System.out.println("MaxPage : " + MaxPage);
		System.out.println("startBlock : " + startBlock);
		System.out.println("endBlock : " + endBlock);
		
		model.addAttribute("list", list);
		return "msg/list_r";
	}
	
	// 쪽지_발신쪽지목록
	@RequestMapping(value="/list_s.do", method=RequestMethod.GET)
	public String list_s(HttpSession session, Model model, MemberSearchVo msv, String name_r) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		
		tmp.put("id", loginVo.getId());
		tmp.put("dept", loginVo.getDept());
		tmp.put("duty", loginVo.getDuty());
		tmp.put("startday", msv.getStartday());
		tmp.put("endday", msv.getEndday());
		tmp.put("searchDept", msv.getDept());
		tmp.put("searchDuty", msv.getDuty());
		tmp.put("searchName", name_r);
		tmp.put("limitOffset", msv.getLimitOffset());
		tmp.put("perPage", msv.getPerPage());
		
		List<Map<String, Object>> list = msgService.list_s(tmp);
		for(Map<String, Object> item : list)
		{
			String result = "";
			switch (String.valueOf(item.get("duty_r"))) {
			case "0" : result = "관리자"; break;
			case "1" : result = "대표"; break;
			case "2" : result = "부장"; break;
			case "3" : result = "팀장"; break;
			default : result = "사원";
			}
			item.put("dutyName", result);
			
			switch (String.valueOf(item.get("dept_r"))) {
			case "1" : result = "기획부"; break;
			case "2" : result = "디자인부"; break;
			case "3" : result = "개발부"; break;
			default : result = "관리부";
			}
			item.put("deptName", result);
		}
		
		// 전체 게시글 수
		int total = msgService.countSend(tmp);
		// 전체 페이지 갯수 계산
		int MaxPage = total / msv.getPerPage() + 1;
		if( total % msv.getPerPage() == 0 ){ MaxPage--;}
		
		// 시작 블럭 번호 계산
		int startBlock = ( msv.getPageNO() / 10 ) * 10 + 1;	// 10페이지 초과
		if( msv.getPageNO() % 10 == 0 ){ startBlock -= 10;}	// 10페이지 미만
		
		// 끝 블럭 번호 계산	
		int endBlock = startBlock + 10 - 1;
		if( endBlock >= MaxPage ) { endBlock = MaxPage; }
		
		model.addAttribute("pageNO", msv.getPageNO());
		model.addAttribute("MaxPage", MaxPage);
		model.addAttribute("startBlock", startBlock);
		model.addAttribute("endBlock", endBlock);
		
		System.out.println("total : " + total);
		System.out.println("pageNO : " + msv.getPageNO());
		System.out.println("MaxPage : " + MaxPage);
		System.out.println("startBlock : " + startBlock);
		System.out.println("endBlock : " + endBlock);
		
		model.addAttribute("list", list);
		return "msg/list_s";
	}
	
	// 쪽지_쪽지쓰기
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write(HttpSession session, MemberSearchVo msv, Model model) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		msv.setPerPage(-1);
		List<Map<String, Object>> olist = memberService.olist(msv);
		
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		for(Map<String, Object> item : olist)
		{
			list.add(memberService.writer((Integer)item.get("id")));
		}
		model.addAttribute("olists", list);
		return "msg/write";
	}
	
	// 쪽지_답장쓰기
	@RequestMapping(value="/write_re.do", method=RequestMethod.GET)
	public String write_re(HttpSession session, int id, int duty, String name, Model model) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		MemberVo mv = new MemberVo();
		mv.setId(id);
		mv.setDuty(duty);
		mv.setName(name);
		model.addAttribute("mv", mv);
		return "msg/write_re";
	}
	
	// 쪽지_쪽지쓰기
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String write(MsgVo msgv, HttpSession session, HttpServletRequest req) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		
		msgv.setId(loginVo.getId());			// 발신자 정보를 넣기
		System.out.println("발신자 : " + msgv.getId());
		System.out.println("내용 : " + msgv.getNote());
		msgService.write(msgv);					// 발신자 정보와 메세지 내용을 DB에 저장
		
		String[] carry = req.getParameterValues("checkIds");
		if (carry != null)
		{
			for( String ids : carry )
			{
				MsgtoVo msgtv = new MsgtoVo();
				msgtv.setMsgNO(msgv.getMsgNO());
				msgtv.setId(Integer.parseInt(ids));
				msgService.checkAdd(msgtv);
			}	
		}
		return "redirect:list_s.do";
	}
	
	// 쪽지_받은 쪽지읽기
	@RequestMapping(value="/view_r.do", method=RequestMethod.GET)
	public String view_r(int msgNO, HttpSession session, Model model) {
		if (msgNO < 1) {
			return "msg/list_r";
		}
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "member/login";
		}
		MsgtoVo tmp = new MsgtoVo();
		tmp.setId(loginVo.getId());
		tmp.setMsgNO(msgNO);
		MsgtoVo msgtv = msgService.getMsgtoVo(tmp);
		Map<String, Object> msg = msgService.view(msgNO);
		if(!loginVo.getName().equals(msg.get("name"))) { // 수신자가 읽었을때만
			msgService.toggle(msgtv.getMsgtoNO()); 		 // 읽지않음을 읽음으로 변경
		}
		model.addAttribute("msg", msg);
		session.removeAttribute("countNotRead");
		int countNotRead = msgService.countNotRead(loginVo);
		session.setAttribute("countNotRead", countNotRead);
		return "msg/view_r";
	}
	
	// 쪽지_보낸 쪽지읽기
	@RequestMapping(value="/view_s.do", method=RequestMethod.GET)
	public String view_s(int msgNO, int msgtoNO, HttpSession session, Model model) {
		if (msgNO < 1) {
			return "msg/list_s";
		}
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "member/login";
		}
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("msgtoNO", msgtoNO);
		tmp.put("msgNO", msgNO);
		
		Map<String, Object> msg = msgService.view_s(tmp);
		model.addAttribute("msg", msg);
		return "msg/view_s";
	}
	
	// 쪽지_보낼사원목록
	@RequestMapping(value="/sendlist.do", method=RequestMethod.GET)
	public String sendlist() {
		return "msg/sendlist";
	}
	
	// 미수신 쪽지 삭제
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public String delete(int msgtoNO, HttpSession session, Model model, HttpServletResponse response) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		int delete = msgService.delete(msgtoNO);
		if (delete < 1) {
			System.out.println("쪽지 삭제 실패");
		}
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		out.println("<script> location.href='list_s.do'; </script>"); 
		out.close();
		return "msg/list_s";
	}
	
}
