package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.dao.AdminDAO;
import com.mvc.question.dto.QuestionDTO;

public class AdminPwFindService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminPwFindService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void list() throws ServletException, IOException {
		String page = "admin/pwQuestion.jsp";
		ArrayList<QuestionDTO> list = null;
		
		AdminDAO memDao = new AdminDAO();
		
		try {
			list = memDao.getQuestionList();
			if(list != null) {
				req.setAttribute("list", list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			memDao.resClose();
		}
		
		String finalPage = (String)req.getAttribute("finalPage");
		if(finalPage != null) {
			page = finalPage;
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);	
	}
	
}
