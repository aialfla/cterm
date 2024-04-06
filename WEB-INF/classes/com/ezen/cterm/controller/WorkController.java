package com.ezen.cterm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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

import com.ezen.cterm.vo.*;
import com.ezen.cterm.service.*;

@RequestMapping(value = "/work")
@Controller
public class WorkController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	 @Autowired
	 private WorkService workService;
	 @Autowired
	 private OverService overService;
	
	// 페이지 호출
	// 근무_내근무목록
	@RequestMapping(value="/list_mine.do", method=RequestMethod.GET)
	public String list_mine(Model model, HttpSession session, String startday, String endday) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		} else if (loginVo.getDuty() == 0) {
			return "redirect:/work/list_work.do";
		}
		
		WorkVo wv = workService.checkTime(loginVo);
		model.addAttribute("wv", wv);
		
		Map<String, Object> ww = workService.weeklyWork(loginVo);
		Map<String, Object> wo = overService.weeklyOver(loginVo);
		String weeklyWork = "00:00:00";
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
		
		List<WorkVo> list = workService.list_mine(tmp);
		model.addAttribute("list", list);
		
		return "work/list_mine";
	}
	
	// 근무_근무전체목록
	@RequestMapping(value="/list_work.do", method=RequestMethod.GET)
	public String list_work(Model model, HttpSession session, MemberSearchVo msv) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		if (loginVo.getDuty() == 4) {
			return "redirect:/work/list_mine.do";
		}
		
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
		
		List<Map<String, String>> list = workService.list(tmp);
		
		int total = workService.count(tmp);
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
		
		for(Map<String, String> item : list)
		{
			String result = "";
			switch (item.get("duty")) {
			case "0" : result = "관리자"; break;
			case "1" : result = "대표"; break;
			case "2" : result = "부장"; break;
			case "3" : result = "팀장"; break;
			default : result = "사원";
			}
			item.put("dutyName", result);
			
			switch (item.get("dept")) {
			case "1" : result = "기획부"; break;
			case "2" : result = "디자인부"; break;
			case "3" : result = "개발부"; break;
			default : result = "관리부";
			}
			item.put("deptName", result);
		}
		model.addAttribute("list", list);
		return "work/list_work";
	}
	// 근무_출근시간 등록
	@RequestMapping(value="/add_start.do", method=RequestMethod.GET)
	public String add_start(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		
		String referer = request.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
		if (loginVo == null) {
			return "redirect:/";
		}
		if (workService.checkStart(loginVo) < 1) { 
			workService.addStart(loginVo);
			WorkVo wv = workService.checkTime(loginVo);
			session.removeAttribute("wv");
			session.setAttribute("wv", wv);
			return "redirect:"+ referer; // 이전 페이지로 리다이렉트
		}
		return "redirect:"+ referer;
	}
	
	// 근무_퇴근시간 등록
	@RequestMapping(value="/add_end.do", method=RequestMethod.GET)
	public String add_end(HttpSession session, HttpServletRequest request) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		String referer = request.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
		if (loginVo == null) {
			return "redirect:/";
		}
		if (workService.checkStart(loginVo) > 0) { 
			workService.addEnd(loginVo);
			WorkVo wv = workService.checkTime(loginVo);
			session.removeAttribute("weeklyWork");
			session.removeAttribute("wv");
			Map<String, Object> map = workService.weeklyWork(loginVo);
			String weeklyWork = String.valueOf(map.get("weeklyWork"));
			session.setAttribute("weeklyWork", weeklyWork);
			session.setAttribute("wv", wv);
			return "redirect:"+ referer; // 이전 페이지로 리다이렉트
		}
		
		return "redirect:"+ referer;
	}
	
	@RequestMapping(value="/set_end_admin.do", method=RequestMethod.GET)
	public String setEndtimeByAdmin(int id, String date, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		String referer = request.getHeader("Referer"); // 헤더에서 이전 페이지를 읽는다.
		if (loginVo == null) {
			return "redirect:/";
		}
		if (loginVo.getDuty() != 0) {
			PrintWriter out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('권한이 없습니다');");
			out.println("</script>"); 
			out.close();
			return "redirect:/"; 
		}
		WorkVo wv = new WorkVo();
		wv.setId(id);
		wv.setDate(date);
		workService.setEndtimeByAdmin(wv);
		
		return "redirect:"+ referer;
	}
	// 기능 구현
	
}
