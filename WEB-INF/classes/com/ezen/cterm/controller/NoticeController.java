package com.ezen.cterm.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ezen.cterm.service.*;
import com.ezen.cterm.vo.*;
import com.mysql.cj.Session;

@RequestMapping(value = "/notice")
@Controller
public class NoticeController {
	
	// !! 기능 구현에 따른 서비스 꼭 추가할 것 !!
	// 서비스 호출
	@Autowired private NoticeService noticeService;
	@Autowired private MemberService memberService;
	
	// 페이지 호출
	// 공지_공지목록
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public String list(MemberSearchVo msv, Model model, HttpSession session) {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		 List<NoticeVo> list = noticeService.list(msv);
		 model.addAttribute("list", list);
		 
		// 전체 페이지 갯수 계산
		int total = noticeService.count(msv);
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
		
		return "notice/list";
	}
	
	// 공지_공지글상세조회
	@RequestMapping(value="/view.do", method=RequestMethod.GET)
	public String view(Integer nNO, Model model, HttpServletResponse response, HttpSession session) throws IOException {
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if (loginUser == null) {
			return "redirect:/";
		}
		
		NoticeVo view = noticeService.view(nNO);
		model.addAttribute("view", view);
		List<NoticeAttachVo> Alist = noticeService.Alist(nNO);
		model.addAttribute("Alist", Alist);
		return "notice/view";
	}
	
	// 공지_공지수정
	@RequestMapping(value="/modify.do", method=RequestMethod.GET)
	public String modify(Integer nNO, Model model, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
		if(loginUser.getDuty() != 0)
		{
			out = response.getWriter();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			out.println("<script> alert('권한이 없습니다');");
			out.println("history.go(-1); </script>"); 
			out.close();
			return "redirect:" + referer;
		}		
		NoticeVo view = noticeService.view(nNO);
		model.addAttribute("view", view);
		List<NoticeAttachVo> Alist = noticeService.Alist(nNO);
		model.addAttribute("Alist", Alist);
		return "notice/modify";
	}
	
	// 공지_공지작성
	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
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
		return "notice/write";
	}
	
	// 기능 구현
	// 공지_공지작성
	@RequestMapping(value = "/write.do", method = RequestMethod.POST)
	public String write(NoticeVo nv, NoticeAttachVo nav, HttpServletResponse response, HttpServletRequest request, HttpSession session, MultipartHttpServletRequest mreq) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
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
		noticeService.write(nv);
		
		// 파일 첨부
		List<MultipartFile> files = mreq.getFiles("file");
		// 업로드 경로 설정
		String path = request.getSession().getServletContext().getRealPath("/resources/noticeUpload");

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
				nav.setNewName(newFileName);
				nav.setOrgName(fileName);
				nav.setnNO(nv.getnNO());
				noticeService.fileAdd(nav);
			}
		}
		return "redirect:view.do?nNO=" + nv.getnNO();
	}
	
	// 공지_공지수정
	@RequestMapping(value="/modify.do", method=RequestMethod.POST)
	public String modify(NoticeVo nv, NoticeAttachVo nav, Model model, HttpServletRequest req, MultipartHttpServletRequest mreq, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
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
		
		String[] arry = req.getParameterValues("fileIds");
		if(arry != null)
		{
			for( String newName : arry )
			{
				noticeService.fileDelete(newName);
			}			
		}
		noticeService.modify(nv);	
		
		// 파일 첨부
		List<MultipartFile> files = mreq.getFiles("file");
		// 업로드 경로 설정
		String path = req.getSession().getServletContext().getRealPath("/resources/noticeUpload");

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
				nav.setNewName(newFileName);
				nav.setOrgName(fileName);
				nav.setnNO(nv.getnNO());
				noticeService.fileAdd(nav);
			}
		}		
		return "redirect:view.do?nNO="+ nv.getnNO();
	}
	
	// 공지_공지삭제
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public String delete (int nNO, HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
		PrintWriter out;
		String referer = request.getHeader("Referer");
		MemberVo loginUser = (MemberVo)session.getAttribute("loginUser");
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
		noticeService.fdelete(nNO);
		noticeService.delete(nNO);
		return "redirect:list.do";
	}

}
