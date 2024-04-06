package com.ezen.cterm.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ezen.cterm.dao.DocuDao;
import com.ezen.cterm.service.DocuService;
import com.ezen.cterm.service.MemberService;
import com.ezen.cterm.vo.AuthDocuVo;
import com.ezen.cterm.vo.AuthOverVo;
import com.ezen.cterm.vo.AuthVacaVo;
import com.ezen.cterm.vo.DocuAttachVo;
import com.ezen.cterm.vo.DocuVo;
import com.ezen.cterm.vo.MemberSearchVo;
import com.ezen.cterm.vo.MemberVo;
import com.ezen.cterm.vo.NoticeAttachVo;
import com.ezen.cterm.vo.SearchVo;

@RequestMapping(value = "/docu")
@Controller
public class DocuController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	@Autowired private DocuService docuService;
	@Autowired private MemberService memberService;
	
	
	// 목록/내용 조회 ================================================================================
	// 내 목록 조회 =======================================
	@RequestMapping(value="/list_mine.do", method=RequestMethod.GET)
	public String list_mine( DocuVo dv, Model model, HttpSession session, MemberSearchVo msv ) throws IOException {
		MemberVo loginVo = (MemberVo)session.getAttribute("loginUser");
		
		// 로그인 안했거나 관리자면 기안조회로 이동
		if (loginVo == null) {
			return "redirect:/";
		} else if (loginVo.getDuty() == 0) {
			return "redirect:/docu/list.do";
		}
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("id", loginVo.getId());
		// 일자별 검색
		tmp.put("startday", msv.getStartday());
		tmp.put("endday", msv.getEndday());
		// 페이징
		tmp.put("perPage", msv.getPerPage());
		tmp.put("limitOffset", msv.getLimitOffset());
		
		List<Map<String, Object>> list = docuService.list_mine(tmp);
		
		model.addAttribute("list", list);
//		System.out.println("list_mine: " + list);
		
		// 전체 게시글 수
		int total = docuService.count_mine(tmp);
		
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
		
		return "docu/list_mine";
	}
	
	// 전체 목록 조회 =======================================
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public String list( MemberSearchVo msv, Model model, HttpSession session ) throws IOException {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		List<Map<String, Object>> list = docuService.list(msv);
//		System.out.println("list: " + list);
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
		int total = docuService.count(msv);
		
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
		
		return "docu/list";
	}
	
	// 내 기안 내용 조회 ======================================
	@RequestMapping(value="/view_mine.do", method=RequestMethod.GET)
	public String view_mine( int docuNO, AuthDocuVo adv, Model model, HttpSession session, HttpServletResponse response, HttpServletRequest request ) throws IOException {
		Map<String, Object> view = docuService.view(docuNO);
		model.addAttribute("view", view);
		
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		// 로그인 유저와 불일치시 접근 불가
		if (loginUser == null) {
			return "redirect:/";
		} else if( loginUser.getId() != Integer.parseInt(String.valueOf(view.get("id"))) )
		{
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('권한이 없습니다');");
			out.println("history.go(-1); </script>"); 
			out.close();
			return "redirect:" + referer;
		}
		
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
		
		return "docu/view_mine";
	}
		
	// 기안 내용 조회 ======================================
	@RequestMapping(value="/view.do", method=RequestMethod.GET)
	public String view( int docuNO, AuthDocuVo adv, Model model, HttpSession session ) {
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
		
		// 결재권자<DocuVacaVo>의 리스트 임시 생성
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
		
		return "docu/view";
	}
	
	
	
	// 등록/철회 =======================================================================================
	// 기안 작성 =============================================
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write (MemberSearchVo msv, Model model, HttpSession session ) throws IOException {
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
		
		return "docu/write";
	}
	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String write( DocuVo dv, AuthDocuVo adv, DocuAttachVo dav, HttpServletRequest req, HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest mreq ) throws IOException {
		// 결재자 추가
		String[] carry = req.getParameterValues("checkIds");
		
		docuService.write(dv);
//		PrintWriter out;
//		String referer = request.getHeader("Referer");
		if (carry != null)
		{
			for( String ids : carry )
			{
				adv.setDocuNO(dv.getDocuNO());
				adv.setId(Integer.parseInt(ids));
				docuService.checkAdd(adv);
			}
		}
//		else {
//			out = response.getWriter();
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/html; charset=utf-8");
//			out.println("<script> alert('결재자를 선택하세요');");
//			out.println("history.go(-1); </script>"); 
//			out.close();
//			return "redirect:" + referer;
//		}
		
		MemberVo writer = memberService.writer(dv.getId());
		
		ArrayList<HashMap<String, Object>> allList = new ArrayList<HashMap<String, Object>>();
		List<AuthDocuVo> authList = docuService.checklist(dv.getDocuNO());
		for ( int i = 0; i < authList.size(); i++ )
		{	
			// 결재권자
			AuthDocuVo auth = (AuthDocuVo) authList.get(i);
			System.out.println("---------------");
			System.out.println("authList.size(): "+authList.size());
			// 결재권자 사원정보
			MemberVo info = memberService.writer(auth.getId());
			
			// 임시 저장 맵
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			// 결재자 정보 map에 넣음
			// Map Auth id, duty / Writer DocuNO, id, duty
			if((writer.getDuty() - 1) == info.getDuty())
			{
				map.put("AId", auth.getId());
				map.put("ADuty", info.getDuty());
				map.put("docuNO", auth.getDocuNO());
				map.put("Wid", writer.getId());
				map.put("Wduty", writer.getDuty());
			}
			allList.add(map);
		}
		System.out.println("allList.size(): "+allList.size());		
		for( HashMap<String, Object> item : allList )
		{
			docuService.changeOne(item);
		}
		
		
		// 파일 첨부
		List<MultipartFile> files = mreq.getFiles("file");
		// 업로드 경로 설정
		String path = request.getSession().getServletContext().getRealPath("/resources/docuUpload");

		for( MultipartFile file : files )
		{
			if( !file.getOriginalFilename().isEmpty() )
			{	
				System.out.println(path);
				String fileName = file.getOriginalFilename();
				String newFileName = UUID.randomUUID().toString();

				String orgName = path + "\\" + fileName;
				String newName = path + "\\" + newFileName;

				file.transferTo(new File( newName ));
				dav.setNewName(newFileName);
				dav.setOrgName(fileName);
				dav.setDocuNO(dv.getDocuNO());
				docuService.fileAdd(dav);
			}
		}
		
		
		return "redirect:view.do?docuNO=" + dv.getDocuNO();
	}
	
	
	// 기안 철회 =============================================
		@RequestMapping(value = "/back.do", method = RequestMethod.GET)
		public String vacaBack(int docuNO, HttpServletRequest request, HttpServletResponse response, HttpSession session ) throws IOException {
			MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
			if (loginUser == null) {
				return "redirect:/";
			}
			
			docuService.docuBack(docuNO);
			docuService.checkDelete(docuNO);
			
//			PrintWriter out = response.getWriter();
//			response.setCharacterEncoding("utf-8");
//			response.setContentType("text/html; charset=utf-8");
//			out.println("<script> alert('철회가 완료되었습니다');");
//			out.println("window.location.href='" + request.getContextPath() + "/docu/view.do?docuNO=" + docuNO + "'; </script>"); 
//			out.close();
			
//			String referer = request.getHeader("Referer");
			return "redirect:view.do?docuNO=" + docuNO;
		}
	
}
