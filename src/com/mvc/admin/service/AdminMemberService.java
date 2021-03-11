package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.admin.dao.AdminDAO;
import com.mvc.member.dao.MemberDAO;
import com.mvc.member.dto.MemberDTO;

public class AdminMemberService{
	
	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminMemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void list() throws ServletException, IOException {
		String page = "#";
		ArrayList<MemberDTO> list = null;

		MemberDAO dao = new MemberDAO();
		try {
			list = dao.getMemberList();
			if(list != null) {
				req.setAttribute("list", list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		String finalPage = (String)req.getAttribute("finalPage");
		if(finalPage != null) {
			page = finalPage;
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);	
	}

	public void disableList() throws ServletException, IOException {
		String page = "list";
		
		String finalPage = "admin/memberDisable.jsp";
		req.setAttribute("finalPage", finalPage);
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void toggleDisable() throws ServletException, IOException {
		String id = req.getParameter("id");
		
		MemberDTO memDto = null;
		AdminDAO adminDao = null;
		MemberDAO memDao = null; 
		try {
			
			memDao = new MemberDAO();
			memDto = memDao.getMember(id);
			
			if(memDto != null) {
				adminDao = new AdminDAO();
				int result = adminDao.toggleDisable(memDto);				
			}
			//System.out.println(result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			memDao.resClose();
			adminDao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		if(memDto != null) {
			map.put("disable", memDto.getDisable());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}
}
