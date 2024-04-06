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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.cterm.service.*;
import com.ezen.cterm.vo.*;

@RequestMapping(value = "/member")
@Controller
public class MemberController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	@Autowired
	private MemberService memberService;
	@Autowired 
	private BCryptPasswordEncoder bCryptPasswordEncoder; 

	
	// 목록/내용 조회 ================================================================================
	// 사원 목록 조회 ========================================
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public String list( MemberSearchVo msv, Model model, HttpSession session ) throws IOException {
//		System.out.println("-----------------------------------------");
//		System.out.println("msv:" + msv.toString());
		
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 로그인유저 정보
		map.put("loginduty", loginUser.getDuty());
		// 검색
		map.put("startday", msv.getStartday());
		map.put("endday", msv.getEndday());
		map.put("dept", msv.getDept());
		map.put("duty", msv.getDuty());
		map.put("name", msv.getName());
		// 페이징
		map.put("perPage", msv.getPerPage());
		map.put("limitOffset", msv.getLimitOffset());

		
		List<Map<String, Object>> list = memberService.list(map);
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
		
		// 전체 게시글 수
		int total = memberService.count(map);
		
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
		
		System.out.println("전체 게시글 수 : " + total);
		System.out.println("선택된페이지번호 : " + msv.getPageNO());
		System.out.println("마지막페이지번호 : " + MaxPage);
		System.out.println("시작페이지번호 : "   + startBlock);
		System.out.println("종료페이지번호 : "   + endBlock);
		
		return "member/list";
	}

	// 사원 정보 조회 ========================================
	@RequestMapping(value="/view.do", method=RequestMethod.GET)
	public String view( int id, Model model, HttpSession session ) throws IOException {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		Map<String, String> map = memberService.view(id);
		model.addAttribute("view", map);
		
		String result = "";
		switch (String.valueOf(map.get("duty"))) {
		case "0" : result = "관리자"; break;
		case "1" : result = "대표"; break;
		case "2" : result = "부장"; break;
		case "3" : result = "팀장"; break;
		default : result = "사원";
		}
		map.put("dutyName", result);
		
		switch (String.valueOf(map.get("dept"))){
		case "1" : result = "기획부"; break;
		case "2" : result = "디자인부"; break;
		case "3" : result = "개발부"; break;
		default : result = "관리부";
		}
		map.put("deptName", result);
		
		switch (String.valueOf(map.get("state"))) {
		case "0" : result = "퇴사"; break;
		case "1" : result = "재직"; break;
		case "2" : result = "휴직"; break;
		default : result = "재직";
		}
		map.put("stateName", result);
		
		if ( map.get("tel") == null || map.get("mail") == null || map.get("addr") == null ){
			result = "-";
		}
		
		return "member/view";
	}
	
	// 내 정보 조회 ========================================
	@RequestMapping(value="/mine.do", method=RequestMethod.GET)
	public String viewMine( Model model, HttpSession session ) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		
		Map<String, Object> map =  memberService.viewMine(loginVo);
		model.addAttribute("map", map);

		String result = "";
		switch (String.valueOf(map.get("duty"))) {
		case "0" : result = "관리자"; break;
		case "1" : result = "대표"; break;
		case "2" : result = "부장"; break;
		case "3" : result = "팀장"; break;
		default : result = "사원";
		}
		map.put("dutyName", result);
		
		
		switch (String.valueOf(map.get("dept"))){
		case "1" : result = "기획부"; break;
		case "2" : result = "디자인부"; break;
		case "3" : result = "개발부"; break;
		default : result = "관리부";
		}
		map.put("deptName", result);
		
		switch (String.valueOf(map.get("state"))) {
		case "0" : result = "퇴사"; break;
		case "1" : result = "재직"; break;
		case "2" : result = "휴직"; break;
		default : result = "재직";
		}
		map.put("stateName", result);
		
		return "member/mine";
	}
	
	
	
	// 등록/수정 =======================================================================================
	// 사원 등록(관리자) ==========================================
	@RequestMapping(value="/join.do", method=RequestMethod.GET)
	public String join() {
		return "member/join";
	}
	@RequestMapping(value="/join.do", method=RequestMethod.POST)
	public String join( MemberVo mv, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		if(loginUser.getId() != 100000)
		{
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('권한이 없습니다');");
			out.println("history.go(-1); </script>"); 
			out.close();
			return "redirect:" + referer;
		}
		System.out.println("유저가 입력한 비밀번호: " + mv.getPw());
		String encodedPW = bCryptPasswordEncoder.encode(mv.getPw());
		// Spring security로 암호화 한 비번 확인
		System.out.println("암호화된 비밀번호: " + encodedPW);
		mv.setPw(encodedPW);
		System.out.println("vo에 잘 들어갔니? : " + mv.getPw());
		memberService.join(mv);

		return "redirect:list.do";
	}
	
	// 사원 정보 수정(관리자) =====================================
	@RequestMapping(value="/modify_admin.do", method=RequestMethod.GET)
	public String modify_admin( int id, Model model ) {
		
		// 정보 조회
		Map<String, String> view = memberService.view(id);
		model.addAttribute("view", view);
		
		return "member/modify_admin";
	}
	@RequestMapping(value="/modify_admin.do", method=RequestMethod.POST)
	public String modify_admin( MemberVo mv, Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		if(loginUser.getId() != 100000)
		{
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('권한이 없습니다');");
			out.println("history.go(-1); </script>"); 
			out.close();
			return "redirect:" + referer;
		}
		System.out.println("유저가 입력한 비밀번호: " + mv.getNpw());
		String encodedPW = bCryptPasswordEncoder.encode(mv.getNpw());
		if(bCryptPasswordEncoder.matches("", encodedPW)) {
			System.out.println(bCryptPasswordEncoder.matches("", encodedPW));
			mv.setNpw("");
		} else {
			mv.setNpw(encodedPW);
		}
		System.out.println(mv.getNpw());
		int result = memberService.modify_admin(mv);
		if( result < 1 )
		{
			// 수정 실패
			System.out.println("수정실패");
			return "redirect:modify_admin.do?id=" + mv.getId();
		}
		return "redirect:list.do";
	}
	
	
	// 내 정보 수정 ===========================================
	@RequestMapping(value="/modify_mine.do", method=RequestMethod.GET)
	public String modify_mine( Model model, HttpSession session ) {

		// 정보 조회
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		if (loginVo == null) {
			return "redirect:/";
		}
		Map<String, Object> view =  memberService.viewMine(loginVo);
		model.addAttribute("view", view);
		
		return "member/modify_mine";
	}
	
	// 글조회 페이지 요청
	@ResponseBody
	@RequestMapping(value = "/pwcheck.do", method = RequestMethod.GET)
	public int pwcheck(HttpSession session, String pw, Model model) {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		String orgpw = loginVo.getPw();
		if(pw == null) return 0;
//		String encodedPW = bCryptPasswordEncoder.encode(pw);
		System.out.println("현재 입력된 비밀번호 : " + pw);
		System.out.println("DB에 저장된 비밀번호 : " +orgpw);
		System.out.println(bCryptPasswordEncoder.matches(pw, orgpw));
		if(bCryptPasswordEncoder.matches(pw, orgpw)) {
			return 1;
		}
		return 0;
	}	
	
	@RequestMapping(value="/modify_mine.do", method=RequestMethod.POST)
	public String modify_mine( MemberVo loginVo, HttpSession session, HttpServletResponse response, HttpServletRequest request ) throws IOException {
		PrintWriter out;
		System.out.println(loginVo.getId());
		System.out.println(loginVo.getPw());
		System.out.println("유저가 입력한 새 비밀번호: " + loginVo.getNpw());
		String encodedPW = bCryptPasswordEncoder.encode(loginVo.getNpw());
		if(bCryptPasswordEncoder.matches("", encodedPW)) {
			System.out.println(bCryptPasswordEncoder.matches("", encodedPW));
			loginVo.setNpw("");
			System.out.println("새 비번: " + loginVo.getNpw());
		} else {
			loginVo.setNpw(encodedPW);
			System.out.println("새 비번: " + loginVo.getNpw());
		}
		int result = memberService.modify_mine(loginVo);
		if( result < 1 )
		{
			// 수정 실패
			System.out.println("수정실패");
			return "redirect:modify_mine.do";
		}
		if (loginVo.getNpw() != "") {
			session.removeAttribute("loginUser");
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('비밀번호가 변경되었습니다. 다시 로그인 해주세요');");
			out.println("window.location.href='" + request.getContextPath() + "/'; </script>"); 
			out.close();
			return "redirect:/";
		}
		return "redirect:mine.do";
	}

}
