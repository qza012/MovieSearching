package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.service.AdminReviewService;

@WebServlet({"/admin/reviewList", "/admin/toggleRevieDelType" })
public class AdminReviewController extends HttpServlet {

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
		
		AdminReviewService service = new AdminReviewService(req, resp);
		
		switch(sub) {
		case "/admin/reviewList" :
			System.out.println("/admin/reviewList 요청");
			service.reviewList();
			break;
			
		case "/admin/toggleRevieDelType" :
			System.out.println("/admin/toggleRevieDelType 요청");
			service.toggleDelType();
			break;
		}
	}

	
}
