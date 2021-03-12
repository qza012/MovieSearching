package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.service.AdminReportCommentService;

@WebServlet({"/admin/reportCommentList", "/admin/toggleReportCommentComplete"})
public class AdminReportCommentController extends HttpServlet {

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
		
		AdminReportCommentService service = new AdminReportCommentService(req, resp);
		
		switch(sub) {
		case "/admin/reportCommentList" :
			System.out.println("/admin/reportCommentList 요청");
			service.reportCommentList();
			break;
			
		case "/admin/toggleReportCommentComplete" :
			System.out.println("/admin//toggleReportCommentComplete 요청");
			service.toggleCompleteType();
			break;
		}
	}

}
