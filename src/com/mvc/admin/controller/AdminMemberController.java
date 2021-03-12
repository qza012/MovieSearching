package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.service.AdminMemberService;

@WebServlet({ "/admin/memberDisableList", "/admin/toggleMemberDisable" })
public class AdminMemberController extends HttpServlet {

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

		AdminMemberService service = new AdminMemberService(req, resp);
		
		switch (sub) {
		case "/admin/memberDisableList":
			System.out.println("/admin/memberDisableList 요청");
			service.memberList();
			break;
			
		case "/admin/toggleMemberDisable":
			System.out.println("/admin/ToggleMemberDisable 요청");
			service.toggleDisable();
			break;
		}
	}

}
