package com.ezen.cterm.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.mysql.cj.Session;

@RequestMapping(value = "/auth")
@Controller
public class AuthController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	@Autowired
	private MemberService memberService;
	@Autowired
	private DocuService docuService;
	@Autowired
	private VacaService vacaService;
	@Autowired
	private OverService overService;
	
	// 페이지 호출
	// 결재_초과근무 결재 목록 <동현>
	@RequestMapping(value="/list_over.do", method=RequestMethod.GET)
	public String list_over(MemberSearchVo msv, HttpSession session, Model model) {
		
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
		tmp.put("searchName", msv.getName());
		tmp.put("state", msv.getState());
		tmp.put("astate", msv.getAstate());
		tmp.put("limitOffset", msv.getLimitOffset());
		tmp.put("perPage", msv.getPerPage());
		
		List<Map<String, Object>> list = overService.acceptList(tmp);
		
		int total = overService.count_accept(tmp);
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
		
		// 최종 저장할 리스트 <view>
		ArrayList<HashMap<String, Object>> overlist = new ArrayList<HashMap<String, Object>>();
		for (Map<String, Object> item : list)
		{
			MemberVo writer = memberService.writer((int)(item.get("id"))); 
			
			// 저장할 MAP
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// map.put overNO, why, wdate, state / name, deptName, dutyName
			map.put("overNO", item.get("overNO"));
			map.put("note", item.get("note"));
			map.put("wdate", item.get("wdate"));
			map.put("date", item.get("date"));
			map.put("state", item.get("state"));
			map.put("astate", item.get("astate"));
			map.put("name", writer.getName());
			map.put("duty", writer.getDutyName());
			map.put("dept", writer.getDeptName());
			overlist.add(map);
		}
		model.addAttribute("overlist", overlist);
		
		return "auth/list_over";
	}
	
	// 결재_초과근무 내용 조회 <동현>
	@RequestMapping(value="/view_over.do", method=RequestMethod.GET)
	public String view_over(int overNO, AuthOverVo aov, Model model,  HttpSession session) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		Map<String, Object> view = overService.view(overNO);
		model.addAttribute("view", view);
		
		int id = (Integer) view.get("id");
		MemberVo writer = memberService.writer(id);
		model.addAttribute("writer", writer);
		
		int checkCount = overService.checkCount(overNO);
		model.addAttribute("checkCount", checkCount);
		
		int checkOkCount = overService.checkOkCount(overNO);
		model.addAttribute("checkOkCount", checkOkCount);
		
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		aov.setId(loginUser.getId());
		aov.setOverNO(overNO);
		AuthOverVo login = overService.selectCheck(aov);
		model.addAttribute("login", login);
		
		// Map에 MemberVo + name을 넣어 리스트로 전달
		ArrayList<HashMap<String, Object>> checklist = new ArrayList<HashMap<String, Object>>();
		
		// 결제권자<AuthOverVo>의 리스트 임시 생성
		List<AuthOverVo> tmp = overService.checklist(overNO);
		
		for( int i = 0; i < tmp.size(); i++ )
		{
			AuthOverVo item = (AuthOverVo) tmp.get(i);
			MemberVo checks = memberService.writer(item.getId());
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			// Map에 이름, 직급 추가
			map.put("state", item.getState());
			map.put("name", checks.getName());
			map.put("duty", checks.getDutyName());
			checklist.add(map);
		}
		model.addAttribute("checklist", checklist);
		return "auth/view_over";
	}
		
	// 결재_초과근무 승인 approve <동현>
	@RequestMapping(value="/approve_over.do", method=RequestMethod.GET)
	public String approve(int overNO, AuthOverVo aov, HttpServletRequest request, HttpSession session) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		aov.setId(loginVo.getId());
		aov.setOverNO(overNO);
		overService.approve(aov);
		overService.stateOne(overNO);
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> testList = new ArrayList<HashMap<String, Object>>();
		List<AuthOverVo> authList = overService.checklist(overNO);
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthOverVo auth = (AuthOverVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			HashMap<String, Object> testmap = new HashMap<String, Object>();
			
			
			// 같은 직급 존재 여부 확인
			if ((loginVo.getDuty() == info.getDuty()) && (auth.getState() <= 1))
			{
				testmap.put("AId", auth.getId());
				testList.add(testmap);
			} 
			
			// 상위 직급 존재 여부 확인 및 정보 입력
			if(testList.size() == 0 && (loginVo.getDuty() - 1) == info.getDuty())
			{
				map.put("AId", auth.getId());
				map.put("overNO", overNO);
				allList.add(map);
			}
		}

		if(testList.size() == 0 && allList.size() != 0 )
		{
			for( HashMap<String, Object> item : allList )
			{
				// 결재 대기
				overService.changeOne(item);
			}
		}
		
		if(testList.size() == 0 && allList.size() == 0 ) {
			// 결재 승인완료
			overService.stateTwo(overNO);
		}
		
		session.removeAttribute("countOver");
		int countOver = overService.countOver(loginVo);
		session.setAttribute("countOver", countOver);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}	
	
	// 결재_초과근무 거절 reject <동현>
	@RequestMapping(value="/reject_over.do", method=RequestMethod.GET)
	public String reject(int overNO, AuthOverVo aov, HttpServletRequest request, HttpSession session) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		aov.setId(loginVo.getId());
		aov.setOverNO(overNO);
		overService.reject(aov);
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		List<AuthOverVo> authList = overService.checklist(overNO);
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthOverVo auth = (AuthOverVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// Map Auth id, duty / Writer overNO, id, duty
			if(loginVo.getDuty() >= info.getDuty() && info.getState() <= 1 && loginVo.getId() != info.getId())
			{
				map.put("AId", auth.getId());
				map.put("overNO", overNO);
				allList.add(map);
			}
		}
		for( HashMap<String, Object> item : allList )
		{
			overService.changeEight(item);
		}
		overService.stateNine(overNO);
		
		session.removeAttribute("countOver");
		int countOver = overService.countOver(loginVo);
		session.setAttribute("countOver", countOver);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	
	// 결재_연차 결재 목록 <지원>
	@RequestMapping(value="/list_vaca.do", method=RequestMethod.GET)
	public String list_vaca(HttpSession session, Model model, MemberSearchVo msv) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", loginUser.getId());
		map.put("loginduty", loginUser.getDuty());
		
		// 검색
		map.put("startday", msv.getStartday());
		map.put("endday", msv.getEndday());
		map.put("dept", msv.getDept());
		map.put("duty", msv.getDuty());
		map.put("name", msv.getName());
		map.put("state", msv.getState());
		map.put("astate", msv.getAstate());
		// 페이징
		map.put("perPage", msv.getPerPage());
		map.put("limitOffset", msv.getLimitOffset());
		
		// 전체 페이지 갯수 계산
		int total = vacaService.avpaging(map);
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
		
		List<Map<String, Object>> list = vacaService.acceptList(map);
		
		// 최종 저장 리스트 <view>
		ArrayList<HashMap<String, Object>> vacalist = new ArrayList<HashMap<String, Object>>();
		for (Map<String, Object> item : list)
		{
			MemberVo writer = memberService.writer((int)(item.get("id"))); 
			HashMap<String, Object> tmp = new HashMap<String, Object>();
			
			tmp.put("vacaNO", item.get("vacaNO"));
			tmp.put("why", item.get("why"));
			tmp.put("wdate", item.get("wdate"));
			tmp.put("state", item.get("state"));
			tmp.put("astate", item.get("astate"));
			tmp.put("name", writer.getName());
			tmp.put("duty", writer.getDutyName());
			tmp.put("dept", writer.getDeptName());
			vacalist.add(tmp);
		}
		model.addAttribute("vacalist", vacalist);
		
		return "auth/list_vaca";
	}
	
	// 결재_연차 내용 조회 <지원>
	@RequestMapping(value="/view_vaca.do", method=RequestMethod.GET)
	public String view_vaca(int vacaNO, AuthVacaVo avv, Model model,  HttpSession session) {
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
		
		avv.setId(loginUser.getId());
		avv.setVacaNO(vacaNO);
		AuthVacaVo login = vacaService.selectCheck(avv);
		model.addAttribute("login", login);
		
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
		return "auth/view_vaca";
	}
	
	// 결재_연차 승인 approve <지원>
	@RequestMapping(value="/approve.do", method=RequestMethod.GET)
	public String approve(int vacaNO, AuthVacaVo avv, HttpServletRequest request, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		avv.setId(loginUser.getId());
		avv.setVacaNO(vacaNO);
		vacaService.approve(avv);
		vacaService.stateOne(vacaNO);
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> testList = new ArrayList<HashMap<String, Object>>();
		List<AuthVacaVo> authList = vacaService.checklist(vacaNO);
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthVacaVo auth = (AuthVacaVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			HashMap<String, Object> testmap = new HashMap<String, Object>();
			
			
			// 같은 직급 존재 여부 확인
			if ((loginUser.getDuty() == info.getDuty()) && (auth.getState() <= 1))
			{
				testmap.put("AId", auth.getId());
				testList.add(testmap);
				System.out.println("───────────");
				System.out.println("testList.size(): " + testList.size());
			} 
			
			// 상위 직급 존재 여부 확인 및 정보 입력
			if(testList.size() == 0 && (loginUser.getDuty() - 1) == info.getDuty())
			{
				map.put("AId", auth.getId());
				map.put("vacaNO", vacaNO);
				allList.add(map);
				System.out.println("───────────");
				System.out.println("allList.size(): " + allList.size());
			}	
		}

		System.out.println("───────────");
		System.out.println("allList.size(): " + allList.size());
		System.out.println("testList.size(): " + testList.size());
		System.out.println("───────────");

		if(testList.size() == 0 && allList.size() != 0 )
		{
			for( HashMap<String, Object> item : allList )
			{
				// 결재 대기
				vacaService.changeOne(item);
			}
		}
		
		if(testList.size() == 0 && allList.size() == 0 ) {
			// 결재 승인완료
			vacaService.stateTwo(vacaNO);
		}

		session.removeAttribute("countVaca");
		int countVaca = vacaService.countVaca(loginUser);
		session.setAttribute("countVaca", countVaca);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	// 결재_연차 거절 reject <지원>
	@RequestMapping(value="/reject.do", method=RequestMethod.GET)
	public String reject(int vacaNO, AuthVacaVo avv, HttpServletRequest request, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		avv.setId(loginUser.getId());
		avv.setVacaNO(vacaNO);
		vacaService.reject(avv);
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		List<AuthVacaVo> authList = vacaService.checklist(vacaNO);
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthVacaVo auth = (AuthVacaVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// Map Auth id, duty / Writer vacaNO, id, duty
			if(loginUser.getDuty() >= info.getDuty() && info.getState() <= 1 && loginUser.getId() != info.getId())
			{
				map.put("AId", auth.getId());
				map.put("vacaNO", vacaNO);
				allList.add(map);
			}
		}
		for( HashMap<String, Object> item : allList )
		{
			vacaService.changeEight(item);
		}
		vacaService.stateNine(vacaNO);
		
		session.removeAttribute("countVaca");
		int countVaca = vacaService.countVaca(loginUser);
		session.setAttribute("countVaca", countVaca);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	
	
	
	// 결재_기안 결재 목록 <진주>
	@RequestMapping(value="/list_docu.do", method=RequestMethod.GET)
	public String list_docu(HttpSession session, Model model, MemberSearchVo msv) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 로그인유저 정보
		map.put("id", loginUser.getId());
		map.put("loginduty", loginUser.getDuty());
		// 검색
		map.put("startday", msv.getStartday());
		map.put("endday", msv.getEndday());
		map.put("dept", msv.getDept());
		map.put("duty", msv.getDuty());
		map.put("name", msv.getName());
		map.put("state", msv.getState());
		map.put("astate", msv.getAstate());
		// 페이징
		map.put("perPage", msv.getPerPage());
		map.put("limitOffset", msv.getLimitOffset());
		
		// 전체 페이지 갯수 계산
		int total = docuService.count_authList(map);
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
		
		List<Map<String, Object>> list = docuService.authList(map);
		
		// 최종 저장 리스트 <view>
		ArrayList<HashMap<String, Object>> doculist = new ArrayList<HashMap<String, Object>>();
		for (Map<String, Object> item : list)
		{
			MemberVo writer = memberService.writer((int)(item.get("id"))); 
			HashMap<String, Object> tmp = new HashMap<String, Object>();
			
			tmp.put("docuNO", item.get("docuNO"));
			tmp.put("title", item.get("title"));
			tmp.put("wdate", item.get("wdate"));
			tmp.put("state", item.get("state"));
			tmp.put("astate", item.get("astate"));
			tmp.put("name", writer.getName());
			tmp.put("duty", writer.getDutyName());
			tmp.put("dept", writer.getDeptName());
			doculist.add(tmp);
		}
		model.addAttribute("doculist", doculist);
		
		return "auth/list_docu";
	}

	// 결재_기안 내용 조회 <진주>
	@RequestMapping(value="/view_docu.do", method=RequestMethod.GET)
	public String view( int docuNO, AuthDocuVo adv, Model model, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		Map<String, Object> view = docuService.view(docuNO);
		model.addAttribute("view", view);
		
		// 부서, 직급 이름 변환
		String result = "";
		switch (String.valueOf(view.get("duty"))) {
		case "0" : result = "관리자"; break;
		case "1" : result = "대표"; break;
		case "2" : result = "부장"; break;
		case "3" : result = "팀장"; break;
		default : result = "사원";
		}
		view.put("dutyName", result);
		
		switch (String.valueOf(view.get("dept"))) {
		case "1" : result = "기획부"; break;
		case "2" : result = "디자인부"; break;
		case "3" : result = "개발부"; break;
		default : result = "관리부";
		}
		view.put("deptName", result);
		
		System.out.println("view: " + view);
		
		// 첨부파일 리스트 불러오기
		List<DocuAttachVo> Alist = docuService.Alist(docuNO);
		model.addAttribute("Alist", Alist);
		
		// 결재 현황 불러오기
		int id = (Integer) view.get("id");
		MemberVo writer = memberService.writer(id);
		model.addAttribute("writer", writer);
		
		int checkCount = docuService.checkCount(docuNO);
		model.addAttribute("checkCount", checkCount);
		
		int checkOkCount = docuService.checkOkCount(docuNO);
		model.addAttribute("checkOkCount", checkOkCount);
		
		adv.setId(loginUser.getId());
		adv.setDocuNO(docuNO);
		AuthDocuVo login = docuService.selectCheck(adv);
		model.addAttribute("login", login);
		
		// Map에 MemberVo + name을 넣어 리스트로 전달
		ArrayList<HashMap<String, Object>> checklist = new ArrayList<HashMap<String, Object>>();
		
		// 결제권자<DocuVacaVo>의 리스트 임시 생성
		List<AuthDocuVo> tmp = docuService.checklist(docuNO);
		
		System.out.println("checklist: " + checklist);
		
		for( int i = 0; i < tmp.size(); i++ )
		{
			AuthDocuVo item = (AuthDocuVo) tmp.get(i);
			HashMap<String, Object> map = new HashMap<String, Object>();
			MemberVo checks = memberService.writer(item.getId());
			
			// Map에 이름, 직급 추가
			map.put("state", item.getState());
			map.put("name", checks.getName());
			map.put("duty", checks.getDutyName());
			checklist.add(map);
		}
		model.addAttribute("checklist", checklist);
		
		return "auth/view_docu";
	}
	
	
	// 결재_기안 승인 approve <진주>
	@RequestMapping(value="/approve_docu.do", method=RequestMethod.GET)
	public String approve(int docuNO, AuthDocuVo adv, HttpServletRequest request, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		adv.setId(loginUser.getId());
		adv.setDocuNO(docuNO);
		docuService.approve(adv);
		docuService.stateOne(docuNO);
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> testList = new ArrayList<HashMap<String, Object>>();
		List<AuthDocuVo> authList = docuService.checklist(docuNO);
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthDocuVo auth = (AuthDocuVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			HashMap<String, Object> testmap = new HashMap<String, Object>();
			
			
			// 같은 직급 존재 여부 확인
			if ((loginUser.getDuty() == info.getDuty()) && (auth.getState() <= 1))
			{
				testmap.put("AId", auth.getId());
				testList.add(testmap);
				System.out.println("───────────");
				System.out.println("testList.size(): " + testList.size());
			} 
			
			// 상위 직급 존재 여부 확인 및 정보 입력
			if(testList.size() == 0 && (loginUser.getDuty() - 1) == info.getDuty())
			{
				map.put("AId", auth.getId());
				map.put("docuNO", docuNO);
				allList.add(map);
				System.out.println("───────────");
				System.out.println("allList.size(): " + allList.size());
			}	
		}

		System.out.println("───────────");
		System.out.println("allList.size(): " + allList.size());
		System.out.println("testList.size(): " + testList.size());
		System.out.println("───────────");

		if(testList.size() == 0 && allList.size() != 0 )
		{
			for( HashMap<String, Object> item : allList )
			{
				// 결재 대기
				docuService.changeOne(item);
			}
		}
		
		if(testList.size() == 0 && allList.size() == 0 ) {
			// 결재 승인완료
			docuService.stateTwo(docuNO);
		}
		
		session.removeAttribute("countDocu");
		int countDocu = docuService.countDocu(loginUser);
		session.setAttribute("countDocu", countDocu);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	
	// 결재_기안 거절 reject <진주>
	@RequestMapping(value="/reject_docu.do", method=RequestMethod.GET)
	public String reject(int docuNO, AuthDocuVo adv, HttpServletRequest request, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		adv.setId(loginUser.getId());
		adv.setDocuNO(docuNO);
		docuService.reject(adv);
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		List<AuthDocuVo> authList = docuService.checklist(docuNO);
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthDocuVo auth = (AuthDocuVo) authList.get(i);
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// Map Auth id, duty / Writer docuNO, id, duty
			if(loginUser.getDuty() >= info.getDuty() && info.getState() <= 1 && loginUser.getId() != info.getId())
			{
				map.put("AId", auth.getId());
				map.put("docuNO", docuNO);
				allList.add(map);
			}
		}
		for( HashMap<String, Object> item : allList )
		{
			docuService.changeEight(item);
		}
		docuService.stateNine(docuNO);
		
		session.removeAttribute("countDocu");
		int countDocu = docuService.countDocu(loginUser);
		session.setAttribute("countDocu", countDocu);
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

}
