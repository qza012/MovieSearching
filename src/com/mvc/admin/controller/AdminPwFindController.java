package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.service.AdminPwFindService;

@WebServlet({"/admin/pwQuestionList", "/admin/updatePwQuestion"})
public class AdminPwFindController extends HttpServlet {

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
		
		AdminPwFindService service = new AdminPwFindService(req, resp);
		
		switch(sub) {
		case "/admin/pwQuestionList" :
			System.out.println("/admin/PwQuestionList 요청");
			service.pwQuestionList();
			break;
			
		case "/admin/updatePwQuestion" :
			System.out.println("/admin/updatePwQuestion 요청");
			service.updatePwQuestion();
			break;
		}
	}

}
