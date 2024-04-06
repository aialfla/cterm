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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.cterm.service.*;
import com.ezen.cterm.vo.*;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	@Autowired private MemberService memberService;
	@Autowired private MsgService msgService;
	@Autowired private WorkService workService;
	@Autowired private DocuService docuService;
	@Autowired private VacaService vacaService;
	@Autowired private OverService overService;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder; 
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
//		logger.info("Welcome home! The client locale is {}.", locale);
		
//		Date date = new Date();
//		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
//		
//		String formattedDate = dateFormat.format(date);
//		
//		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	// 로그인
	@RequestMapping(value= "/login.do", method = RequestMethod.POST)
	public String login(MemberVo mv, HttpSession session, HttpServletResponse response, Model model) throws IOException {
		MemberVo loginVo = memberService.login(mv);
		System.out.println("MemberController :: login");
		System.out.println("mv.getId(): " + mv.getId());
		System.out.println("mv.getPw(): " + mv.getPw());
		
		int countNotRead = msgService.countNotRead(loginVo);
		int countDocu = docuService.countDocu(loginVo);
		int countVaca = vacaService.countVaca(loginVo);
		int countOver = overService.countOver(loginVo);
//		int sumAuth = (countDocu + countVaca + countOver);
		WorkVo wv = workService.checkTime(loginVo);
		Map<String, Object> mapw = workService.weeklyWork(loginVo);
		String weeklyWork = "00:00:00";
		if (mapw != null) {
			weeklyWork = String.valueOf(mapw.get("weeklyWork"));
		}
		if(loginVo != null && bCryptPasswordEncoder.matches(mv.getPw(), loginVo.getPw()))
		{
			session.setAttribute("wv", wv);
			session.setAttribute("loginUser", loginVo);
			session.setAttribute("countNotRead", countNotRead);
			session.setAttribute("countDocu", countDocu);
			session.setAttribute("countVaca", countVaca);
			session.setAttribute("countOver", countOver);
			session.setAttribute("weeklyWork", weeklyWork);
			return "redirect:main.do";
		}
		
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		out.println("<script> alert('허용하지 않은 접근입니다');");
//		out.println("<script> Swal.fire('로그인 후 이용해 주세요','비밀번호 분실 시 관리자에게 문의바랍니다.');");
		out.println("history.go(-1); </script>"); 
		out.close();
		return "redirect:/";
	}
	
	// 메인페이지
	@RequestMapping(value="/main.do", method=RequestMethod.GET)
	public String main(Model model, String day, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "redirect:/";
		}
		
		Map<String, Object> today = vacaService.today();
		model.addAttribute("today", today.get("today"));
		
		// <total> 총 연차 및 사용 연차
		MemberVo login = memberService.writer(loginUser.getId());
		model.addAttribute("login", login);
		
		List<Map<String, Object>> use = vacaService.allCount(loginUser.getId());
		int result = 0;
		
		for (Map<String, Object> item : use)
		{
			result += Integer.parseInt(String.valueOf(item.get("useDay")));
		}		
		model.addAttribute("use", result);
		
		// <우측> 캘린더 연차자 정보 출력
		model.addAttribute("day", day);
		System.out.println("───────────");
		System.out.println(day);
		if(day != null)
		{
			String[] dayArr = day.split("-");
			System.out.println("───────────");
			System.out.println(dayArr[1]);			
			System.out.println(dayArr[2]);		
			model.addAttribute("dayMonth", dayArr[1]);
			model.addAttribute("dayDay", dayArr[2]);
		}
		
//		model.addAttribute("list", vacaService.todayVaca(day));
		List<Map<String, Object>> tmp = vacaService.todayVaca(day);
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (Map<String, Object> item : tmp)
		{
			MemberVo writer = memberService.writer((int)item.get("id"));
			
			// 저장할 MAP +id, name, dept, duty, why, start
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.get("id"));
			map.put("name", writer.getName());
			map.put("dept", writer.getDept());
			map.put("deptName", writer.getDeptName());
			map.put("duty", writer.getDuty());
			map.put("dutyName", writer.getDutyName());
			map.put("why", item.get("why"));
			map.put("start", item.get("start"));
			list.add(map);
		}
		model.addAttribute("list", list);
		
		return "main";
	}
	
	
	// 캘린더 데이터 전송
	@RequestMapping(value="/calendar.do", method=RequestMethod.GET)
	@ResponseBody
	public List<CalendarVo> data(Model model, HttpServletRequest request) {
		return vacaService.event();
	}
	

	// 로그아웃
	@RequestMapping(value= "/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.setAttribute("loginUser", null);
		// 세션 리셋
		session.invalidate();
		return "redirect:/";
	}

}
