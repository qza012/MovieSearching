package com.mvc.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.member.service.MemberService;

@WebServlet({"/idChk","/join/login","/join/logout", "/join","/questionList", "/myPage/updateMF","/myPage/update", "/myPage/withdraw",
	"/member/member","/member/search","/follow","/myPage/loginForMyPage", "/myPage/follow","/myPage/followerList","/myPage/followingList",
	"/myPage/notFollow","/myPage/deleteFollower","/join/idFind","/pwFind","/pwQuestionList","/join/pwFind","/member/alarmList","/member/alarmDel","/myPage/alarm"})


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
				
			case "/join/login":
				System.out.println("로그인 요청");
				service.login();
				break;
				
			case "/join/logout":
				System.out.println("로그아웃 요청");
				req.getSession().removeAttribute("myLoginId");//session에서 값 삭제 
				resp.sendRedirect("../movie/home");
				break;
				
			case "/questionList":
				System.out.println("질문지 요청");
				service.questionList();
				break;
				
			case "/myPage/loginForMyPage":
				System.out.println("마이페이지 구성을 위한 임시 로그인");
				service.loginForMyPage();
				break;
				
			case "/myPage/logout":
				System.out.println("마이페이지 구성을 위한 임시 로그아웃");
				req.getSession().removeAttribute("myLoginId");
				RequestDispatcher dis = req.getRequestDispatcher("./main.jsp");
				dis.forward(req, resp);
				break;	
				
			case "/myPage/updateMF":
				System.out.println("회원정보 수정 폼으로");
				service.updateMemberForm();
				break;
				
			case "/myPage/update":
				System.out.println("프로필 사진 저장 요청");
				service.update();
				break;
				
			case "/myPage/withdraw":
				System.out.println("회원 탈퇴 요청");
				service.withdraw();
				break;
				
			case "/member/member":
				System.out.println("회원이 작성한 리뷰,top7  요청");
				service.getMemberList();
				break;
				
			case "/member/search":
				System.out.println("검색 요청");
				service.search();
				break;
				
			case "/follow":
				System.out.println("팔로우/팔로우 취소 요청");
				service.follow();
				break;
				
			case "/join/idFind":
				System.out.println("아이디 찾기 요청");
				service.idFind();
				break;
				
			case "/pwFind":
				System.out.println("비밀번호 찾기 요청");
				service.pwFind();
				break;

				
			case "/myPage/followerList":
				System.out.println("나를 팔로우 하는 사람들");
				service.followerList();
				break;
				
			case "/myPage/followingList":
				System.out.println("내가 팔로잉하는 사람들");
				service.followingList();
				break;

			case "/myPage/deleteFollower":
				System.out.println("팔로워 삭제");
				service.deleteFollower();
				break;
				
			case "/pwQuestionList":
				System.out.println("비밀번호 질문지 요청");
				service.pwQuestionList();
				break;
				
			case "/member/fChk":
				System.out.println("팔로우 체크");
				service.followCheck();
				break;
				
			case "/myPage/alarm":
				System.out.println("알람 체크");
				service.alramChk();

			case "/member/alarmList":
				System.out.println("알람보기 요청");
				service.alarmList();
				break;
				
			case "/member/alarmDel":
				System.out.println("알람 삭제 요청");
				service.alarmDel();
				break;
		}
		
	}
	
	


}