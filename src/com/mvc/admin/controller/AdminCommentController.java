package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.service.AdminCommentService;

@WebServlet({"/admin/commentList", "/admin/toggleCommentDelType"})
public class AdminCommentController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String sub = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("호출한 태그 : " + sub);
		
		AdminCommentService service = new AdminCommentService(req, resp);
		
		switch(sub) {
		case "/admin/commentList" :
			System.out.println("/admin/commentList 요청");
			service.commentList();
			break;
			
		case "/admin/toggleCommentDelType" :
			System.out.println("/admin/toggleCommentDelType 요청");
			service.toggleDelType();
			break;
		}
	}

}
