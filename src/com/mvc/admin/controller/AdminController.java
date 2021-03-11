package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet({"/adminList", "/adminMemberDisableList", "/adminToggleMemberDisable", 
	"/adminPwQuestionList", "/adminCommentList", "/adminToggleCommentDelType"
			,"/adminReportCommentList", "/adminReportReviewList", "/adminReviewList"})
public class AdminController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sub = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("호출한 태그 : " + sub);

		switch(sub) {
		case "/list" :
			System.out.println("list 요청");
			//MemberService service = new MemberService(req, resp);
			//service.list();
			break;
			
		case "/memberDisableList" :
			System.out.println("memberDisableList 요청");
			//service = new MemberService(req, resp);
			//((MemberService)service).disableList();
			break;
			
		case "/toggleMemberDisable" :
			System.out.println("toggleMemberDisable 요청");
			//service = new MemberService(req, resp);
			//((MemberService)service).toggleDisable();
			break;
			
		case "/pwQuestionList" :
			System.out.println("pwQuestionList 요청");
			//service = new PwFindService(req, resp);
			//((PwFindService)service).list();
			break;
			
		case "/commentList" :
			System.out.println("commentList 요청");
			//service = new CommentImpl(req, resp);
			//((CommentImpl)service).list();
			break;
			
		case "/toggleCommentDelType" :
			System.out.println("toggleCommentDelType 요청");
			//service = new CommentImpl(req, resp);
			//((CommentImpl)service).toggleDelType();
			break;
			
		case "/reportCommentList" :
			System.out.println("reportCommentList 요청");
			//service = new ReportCommentService(req, resp);
			//((ReportCommentService)service).list();
			break;
			
		case "/reportReviewList" :
			System.out.println("reportReviewList 요청");
			//service = new ReportReviewService(req, resp);
			//((ReportReviewService)service).list();
			break;
			
		case "/reviewList" :
			System.out.println("reviewList 요청");
			//service = new ReviewService(req, resp);
			//((ReviewService)service).list();
			break;
		}
	}

	
}
