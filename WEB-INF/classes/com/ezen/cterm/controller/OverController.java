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

@RequestMapping(value = "/work")
@Controller
public class OverController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	@Autowired
	private WorkService workService;
	
	@Autowired
	private OverService overService;
	
	@Autowired
	private MemberService memberService;
	
	// 페이지 호출
	// 근무_초과근무목록
	@RequestMapping(value="/list_over_mine.do", method=RequestMethod.GET)
	public String list_over(Model model, HttpSession session, String startday, String endday) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		WorkVo wv = workService.checkTime(loginVo);
		model.addAttribute("wv", wv);
		Map<String, Object> ww = workService.weeklyWork(loginVo);
		Map<String, Object> wo = overService.weeklyOver(loginVo);
		String weeklyWork = "00:00:00";;
		String weeklyOver = "";
		if (ww != null) {
			weeklyWork = String.valueOf(ww.get("weeklyWork"));
		}
		if (wo != null) {
			weeklyOver = String.valueOf(wo.get("weeklyOver"));
		}
		model.addAttribute("weeklyWork", weeklyWork);
		model.addAttribute("weeklyOver", weeklyOver);
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("id", loginVo.getId());
		tmp.put("startday", startday);
		tmp.put("endday", endday);
		
		List<OverVo> list = overService.list_mine(tmp);
		model.addAttribute("list", list);
		return "work/list_over_mine";
	}	
	// 근무_초과근무작성 페이지 호출
	@RequestMapping(value="/write_over.do", method=RequestMethod.GET)
	public String write_over(MemberSearchVo msv, Model model, HttpSession session) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		msv.setPerPage(-1);
		List<Map<String, Object>> olist = memberService.olist(msv);
		
		ArrayList<MemberVo> list = new ArrayList<MemberVo>();
		for(Map<String, Object> item : olist)
		{
			System.out.println(item.get("duty"));
			System.out.println(item.get("id"));
			
			list.add(memberService.writer((Integer)item.get("id")));
//			MemberVo olists = memberService.writer((Integer)item.get("id"));
		}
		model.addAttribute("olists", list);
		return "work/write_over";
	}	
	
	// 근무_초과근무작성
	@RequestMapping(value = "/write_over.do", method = RequestMethod.POST)
	public String write(HttpSession session, OverVo ov, AuthOverVo aov, HttpServletRequest req, HttpServletResponse response) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		int already = overService.already(ov);
		if (already > 0) {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('이미 승인된 초과근무가 존재하는 날짜입니다');");
			out.println("history.go(-1); </script>"); 
			out.close();
			
			String referer = req.getHeader("Referer");
			return "redirect:" + referer;
		}
		overService.write(ov);
		// 결재권자 등록
		String[] carry = req.getParameterValues("checkIds");
		if (carry != null)
		{
			for( String ids : carry )
			{
				aov.setOverNO(ov.getOverNO());
				aov.setId(Integer.parseInt(ids));
				overService.checkAdd(aov);				
			}	
		}
		
		MemberVo writer = memberService.writer(ov.getId());
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		List<AuthOverVo> authList = overService.checklist(ov.getOverNO());
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthOverVo auth = (AuthOverVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// Map Auth id, duty / Writer vacaNO, id, duty
			if((writer.getDuty() - 1) == info.getDuty())
			{
				map.put("AId", auth.getId());
				map.put("ADuty", info.getDuty());
				map.put("overNO", auth.getOverNO());
				map.put("Wid", writer.getId());
				map.put("Wduty", writer.getDuty());
			}
			allList.add(map);
		}

		for( HashMap<String, Object> item : allList )
		{
			overService.changeOne(item);
		}
		return "redirect:view.do?overNO=" + ov.getOverNO();
	}
	
	// 근무_초과근무전체목록
	@RequestMapping(value="/list_over.do", method=RequestMethod.GET)
	public String list_over_admin(Model model, HttpSession session, MemberSearchVo msv) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		if (loginVo.getDuty() > 0) {
			return "redirect:/work/list_over_mine.do";
		}
		WorkVo wv = workService.checkTime(loginVo);
		model.addAttribute("wv", wv);
		
		Map<String, Object> ww = workService.weeklyWork(loginVo);
		Map<String, Object> wo = overService.weeklyOver(loginVo);
		String weeklyWork = "00:00:00";;
		String weeklyOver = "";
		if (ww != null) {
			weeklyWork = String.valueOf(ww.get("weeklyWork"));
		}
		if (wo != null) {
			weeklyOver = String.valueOf(wo.get("weeklyOver"));
		}
		model.addAttribute("weeklyWork", weeklyWork);
		model.addAttribute("weeklyOver", weeklyOver);
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("id", loginVo.getId());
		tmp.put("dept", loginVo.getDept());
		tmp.put("duty", loginVo.getDuty());
		tmp.put("startday", msv.getStartday());
		tmp.put("endday", msv.getEndday());
		tmp.put("searchDept", msv.getDept());
		tmp.put("searchDuty", msv.getDuty());
		tmp.put("searchName", msv.getName());
		tmp.put("limitOffset", msv.getLimitOffset());
		tmp.put("perPage", msv.getPerPage());
		
		List<Map<String,Object>> list = overService.list(tmp);
		
		// 페이징
		tmp.put("perPage", msv.getPerPage());
		tmp.put("limitOffset", msv.getLimitOffset());
		
		// 전체 게시글 수
		int total = overService.count(tmp);
		
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
		
		for(Map<String, Object> item : list)
		{
			String result = "";
			switch (String.valueOf(item.get("duty"))) {
			case "0" : result = "관리자"; break;
			case "1" : result = "대표"; break;
			case "2" : result = "부장"; break;
			case "3" : result = "팀장"; break;
			default : result = "사원";
			}
			item.put("dutyName", result);
			
			switch (String.valueOf(item.get("dept"))) {
			case "1" : result = "기획부"; break;
			case "2" : result = "디자인부"; break;
			case "3" : result = "개발부"; break;
			default : result = "관리부";
			}
			item.put("deptName", result);
		}
		model.addAttribute("list", list);
		
		return "work/list_over";
	}	
	// 근무_결재자사원목록
	@RequestMapping(value="/sendlist.do", method=RequestMethod.GET)
	public String sendlist() {
		return "work/sendlist";
	}	
	
	// 근무_초과근무상세조회
	@RequestMapping(value="/view.do", method=RequestMethod.GET)
	public String view(HttpSession session, int overNO, Model model) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		Map<String, Object> ov = overService.view(overNO);
		String result = "";
		switch (String.valueOf(ov.get("duty"))) {
		case "0" : result = "관리자"; break;
		case "1" : result = "대표"; break;
		case "2" : result = "부장"; break;
		case "3" : result = "팀장"; break;
		default : result = "사원";
		}
		ov.put("dutyName", result);
		
		switch (String.valueOf(ov.get("dept"))) {
		case "1" : result = "기획부"; break;
		case "2" : result = "디자인부"; break;
		case "3" : result = "개발부"; break;
		default : result = "관리부";
		}
		ov.put("deptName", result);
		model.addAttribute("ov", ov);
		
//			int id = (Integer) view.get("id");
//			MemberVo writer = memberService.writer(id);
//			model.addAttribute("writer", writer);
		
		int checkCount = overService.checkCount(overNO);
		model.addAttribute("checkCount", checkCount);
		
		int checkOkCount = overService.checkOkCount(overNO);
		model.addAttribute("checkOkCount", checkOkCount);
		
		// Map에 MemberVo + name을 넣어 리스트로 전달
		ArrayList<HashMap<String, Object>> checklist = new ArrayList<HashMap<String, Object>>();
		
		// 결제권자<AuthVacaVo>의 리스트 임시 생성
		List<AuthOverVo> tmp = overService.checklist(overNO);
		
		for( int i = 0; i < tmp.size(); i++ )
		{
			AuthOverVo item = (AuthOverVo) tmp.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			MemberVo checks = memberService.writer(item.getId());
			
			// Map에 이름, 직급 추가
			map.put("state", item.getState());
			map.put("name", checks.getName());
			map.put("duty", checks.getDutyName());
			checklist.add(map);
		}
		model.addAttribute("checklist", checklist);
		
		return "work/view";
	}
	
	// 초과근무 철회
	@RequestMapping(value = "/back.do", method = RequestMethod.GET)
	public String overBack(int overNO, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		
		overService.overBack(overNO);
		overService.checkDelete(overNO);
		
//		PrintWriter out = response.getWriter();
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8");
//		out.println("<script> alert('철회가 완료되었습니다');");
//		out.println("history.go(-1); </script>"); 
//		out.close();
//		
//		String referer = request.getHeader("Referer");
		return "redirect:view.do?overNO=" + overNO;
	}
	
}
