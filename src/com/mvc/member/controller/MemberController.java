package com.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.member.service.MemberService;

@WebServlet({"/idChk","/login","/logout", "/join","/Qlist", "/updateMF"
			,"/update", "/withdraw","/member/member"})
public class MemberController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		//한글 깨짐
		req.setCharacterEncoding("UTF-8");
				
		String sub = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("호출한 태그 : " + sub);
		
		MemberService service = new MemberService(req, resp);
		
		
		switch(sub) {
			case "/idChk":
				System.out.println("중복체크 요청");
				service.idChk();
				break;
				
			case "/join":
				System.out.println("회원가입 요청");
				service.join();
				break;
				
			case "/login":
				System.out.println("로그인 요청");
				service.login();
				break;
				
			case "/logout":
				System.out.println("로그아웃 요청");
				req.getSession().removeAttribute("id");//session에서 값 삭제 
				resp.sendRedirect("index.jsp");
				break;
				
			case "/Qlist":
				System.out.println("질문지 요청");
				service.questionList();
				break;
				
			case "/updateMF":
				System.out.println("회원정보 수정 폼으로");
				service.updateMemberForm();
				break;
				
			case "/update":
				System.out.println("프로필 사진 저장 요청");
				service.updateMember();
				break;
				
			case "/withdraw":
				System.out.println("회원 탈퇴 요청");
				//service.withdraw();
				break;
				
			case "/member/member":
				System.out.println("회원이 작성한 리뷰,top7  요청");
				service.getMemberList();
				break;
				
		}
		
	}
	
	


}