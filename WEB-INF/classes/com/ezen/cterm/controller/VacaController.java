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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.cterm.service.*;
import com.ezen.cterm.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@RequestMapping(value = "/vaca")
@Controller
public class VacaController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	 @Autowired
	 private VacaService vacaService;
	 @Autowired
	 private MemberService memberService;

	// 페이지 호출
	// 연차_내연차목록
	@RequestMapping(value="/list_mine.do", method=RequestMethod.GET)
	public String list_mine(Model model, HttpSession session, MemberSearchVo msv) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		
		if (loginUser == null) {
			return "redirect:/";
		} else if (loginUser.getDuty() == 0) {
			return "redirect:/vaca/list.do";
		}

		MemberVo login = memberService.writer(loginUser.getId());
		model.addAttribute("login", login);

		// 연차 작성 리스트
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", loginUser.getId());
		// 검색
		map.put("startday", msv.getStartday());
		map.put("endday", msv.getEndday());
		
		// 페이징
		map.put("perPage", msv.getPerPage());
		map.put("limitOffset", msv.getLimitOffset());
		
		// 전체 페이지 갯수 계산
		int total = vacaService.paging(map);
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

		List<Map<String, Object>> list = vacaService.list(map);
		model.addAttribute("list", list);
		
		// <total> 사용 연차
		List<Map<String, Object>> use = vacaService.allCount(loginUser.getId());
		int result = 0;
		
		for (Map<String, Object> item : use)
		{
			result += Integer.parseInt(String.valueOf(item.get("useDay")));
		}		
		model.addAttribute("use", result);
		
		// <total> 최근 연차 신청
		model.addAttribute("latest", vacaService.checkLatestVaca(loginUser.getId()));
		
		// <total> dday
		model.addAttribute("dday", vacaService.dday());
		
		return "vaca/list_mine";
	}
	
	// 연차_연차작성
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write(MemberSearchVo msv, Model model, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
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
		return "vaca/write";
	}	
	
	// 연차_연차상세조회
	@RequestMapping(value="/view.do", method=RequestMethod.GET)
	public String view(int vacaNO, Model model, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		Map<String, Object> view = vacaService.view(vacaNO);
		model.addAttribute("view", view);
		
		int id = (Integer) view.get("id");
		MemberVo writer = memberService.writer(id);
		model.addAttribute("writer", writer);
		
		int checkCount = vacaService.checkCount(vacaNO);
		model.addAttribute("checkCount", checkCount);
		
		int checkOkCount = vacaService.checkOkCount(vacaNO);
		model.addAttribute("checkOkCount", checkOkCount);
		
		// Map에 MemberVo + name을 넣어 리스트로 전달
		ArrayList<HashMap<String, Object>> checklist = new ArrayList<HashMap<String, Object>>();
		
		// 결제권자<AuthVacaVo>의 리스트 임시 생성
		List<AuthVacaVo> tmp = vacaService.checklist(vacaNO);
		
		for( int i = 0; i < tmp.size(); i++ )
		{
			AuthVacaVo item = (AuthVacaVo) tmp.get(i);
			MemberVo checks = memberService.writer(item.getId());
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			// Map에 이름, 직급 추가
			map.put("state", item.getState());
			map.put("name", checks.getName());
			map.put("duty", checks.getDutyName());
			checklist.add(map);
		}
		model.addAttribute("checklist", checklist);
		
		return "vaca/view";
	}
	
	// 연차_연차전체목록
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public String list(String hid, MemberSearchVo msv, Model model, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		MemberVo login = memberService.writer(loginUser.getId());
		model.addAttribute("login", login);
		
		List<Map<String, Object>> list = memberService.olist(msv);
		ArrayList<HashMap<String, Object>> vacalist = new ArrayList<HashMap<String, Object>>();
		
		// 리스트
		for (Map<String, Object> item : list)
		{
			int id = Integer.parseInt(String.valueOf(item.get("id")));
			MemberVo writer = memberService.writer(id);
			List<Map<String, Object>> uses = vacaService.allCount(id);
			
			int use = 0;
			for (Map<String, Object> tmp : uses)
			{
				use += Integer.parseInt(String.valueOf(tmp.get("useDay")));
			}
			
			// 저장할 MAP
			HashMap<String, Object> map = new HashMap<String, Object>();
			if( loginUser.getDuty() < writer.getDuty() )
			{
				map.put("id", writer.getId());
				map.put("join", writer.getjoindate());
				map.put("name", writer.getName());
				map.put("dept", writer.getDept());
				map.put("deptName", writer.getDeptName());
				map.put("duty", writer.getDuty());
				map.put("dutyName", writer.getDutyName());
				map.put("vaca", writer.getVaca());
				map.put("usevaca", use);
				map.put("ingvaca", (writer.getVaca()-use));			
				vacalist.add(map);				
			}
		}
		model.addAttribute("vacalist", vacalist);
		System.out.println("───────────");
		System.out.println("vacalist.size(): " + vacalist.size());
		System.out.println("list.size(): " + list.size());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", loginUser.getId());
		map.put("loginduty", loginUser.getDuty());
		map.put("logindept", loginUser.getDept());
		
		// 검색
		map.put("startday", msv.getStartday());
		map.put("endday", msv.getEndday());
		map.put("dept", msv.getDept());
		map.put("duty", msv.getDuty());
		map.put("name", msv.getName());
		// 페이징
		map.put("perPage", msv.getPerPage());
		map.put("limitOffset", msv.getLimitOffset());
		
		// 전체 페이지 갯수 계산
		int total = vacaService.mpaging(map);
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
		
		
		// <total> 로그인 유저 사용 연차
		List<Map<String, Object>> use = vacaService.allCount(loginUser.getId());
		int result = 0;
		for (Map<String, Object> item : use)
		{
			result += Integer.parseInt(String.valueOf(item.get("useDay")));
		}
		model.addAttribute("use", result);
		
		// <total> 최근 연차 신청
		model.addAttribute("latest", vacaService.checkLatestVaca(loginUser.getId()));
		
		// <total> dday
		model.addAttribute("dday", vacaService.dday());
		
		return "vaca/list";
	}
	
	// 캘린더 데이터 전송
	@RequestMapping(value="/history.do", method=RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> data(@RequestParam("id") int id, Model model, HttpServletRequest request) {
		System.out.println("───────────");
		System.out.println(id);
	
		return vacaService.history(id);
	}

	// 연차_결재자사원목록
	@RequestMapping(value="/sendlist.do", method=RequestMethod.GET)
	public String sendlist() {
		return "vaca/sendlist";
	}
	
	// 기능 구현
	// 연차 부여
	@RequestMapping(value="/addVacation.do", method = RequestMethod.GET)
	public String addVacation(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
				
		vacaService.addVacation();
		String referer = request.getHeader("Referer");
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		out.println("<script> alert('총 연차 갱신 완료');");
		out.println("history.go(-1); </script>"); 
		out.close();
		
		return "redirect:" + referer;
	}
	
	// 연차 신청
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	public String write(VacaVo vv, DateVo dv, AuthVacaVo avv, HttpServletRequest req, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		// 연차 신청일 목록
		String[] arry = req.getParameterValues("dayIds");
		// 연차 결재자 목록
		String[] carry = req.getParameterValues("checkIds");
		
		PrintWriter out;
		String referer = request.getHeader("Referer");
		if( arry == null || carry == null )
		{
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('결재자 또는 연차 일정을 입력하세요');");
			out.println("history.go(-1); </script>"); 
			out.close();
			return "redirect:" + referer;
		}
		vacaService.write(vv);

		// 연차 시작일~종료일 등록
		if( arry != null )
		{
			for( String days : arry )
			{
				dv.setVacaNO(vv.getVacaNO());
				dv.setDate(days);
				vacaService.vacaAdd(dv);
			}			
		}
		
		// 등록된 연차 中 주말 삭제
		vacaService.weekend(vv.getVacaNO());
		
		// 결재권자 등록
		if ( carry != null )
		{
			for( String ids : carry )
			{
				avv.setVacaNO(vv.getVacaNO());
				avv.setId(Integer.parseInt(ids));
				vacaService.checkAdd(avv);				
			}	
		}
		
		MemberVo writer = memberService.writer(vv.getId());
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		List<AuthVacaVo> authList = vacaService.checklist(vv.getVacaNO());
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthVacaVo auth = (AuthVacaVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// Map Auth id, duty / Writer vacaNO, id, duty
			if((writer.getDuty() - 1) == info.getDuty())
			{
				map.put("AId", auth.getId());
				map.put("ADuty", info.getDuty());
				map.put("vacaNO", auth.getVacaNO());
				map.put("Wid", writer.getId());
				map.put("Wduty", writer.getDuty());
			}
			allList.add(map);
		}

		for( HashMap<String, Object> item : allList )
		{
			vacaService.changeOne(item);
		}
		
		return "redirect:view.do?vacaNO=" + vv.getVacaNO();
	}
	
	// 연차 철회
	@RequestMapping(value = "/back.do", method = RequestMethod.GET)
	public String vacaBack(int vacaNO, HttpSession session) throws IOException {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		vacaService.vacaBack(vacaNO);
		vacaService.checkDelete(vacaNO);

		return "redirect:view.do?vacaNO=" + vacaNO;
	}
}
