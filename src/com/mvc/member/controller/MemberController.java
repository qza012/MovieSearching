package com.mvc.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.member.service.MemberService;

@WebServlet({"/idChk","/login","/logout", "/join","/Qlist", "/myPage/updateMF","/myPage/update","/myPage/withdraw","/member/member",
	"/myPage/loginForMyPage", "/myPage/follow","/myPage/followerList","/myPage/followingList","/myPage/notFollow","/myPage/deleteFollower","/myPage/page"})
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
				
			case "/myPage/loginForMyPage":
				System.out.println("마이페이지 구성을 위한 임시 로그인");
				service.loginForMyPage();
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
				
			case "/myPage/follow":
				System.out.println("팔로우 하기_마이페이지");
				service.follow();
				break;
				
			case "/myPage/followerList":
				System.out.println("나를 팔로우 하는 사람들");
				service.followerList();
				break;
				
			case "/myPage/followingList":
				System.out.println("내가 팔로잉하는 사람들");
				service.followingList();
				break;
				
			case "/myPage/notFollow":
				System.out.println("팔로우 취소");
				service.notFollow();
				break;
				
			case "/myPage/deleteFollower":
				System.out.println("팔로워 삭제");
				service.deleteFollower();
				break;
				
			case "/myPage/page":
				System.out.println("페이징 처리");
				/*service.pagingMyPage();*/
				break;
		}
		
	}
	
	


}