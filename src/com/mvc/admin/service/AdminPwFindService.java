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
import com.mvc.movie.dto.MovieDTO;
import com.mvc.question.dto.QuestionDTO;

public class AdminPwFindService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminPwFindService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void pwQuestionList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정.
		String finalPage = "pwQuestion.jsp";
		req.setAttribute("finalPage", finalPage);
		
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

		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
		dis.forward(req, resp);	
	}
	
	public void updatePwQuestion() throws ServletException, IOException {
		String idx = req.getParameter("idx");

		QuestionDTO questionDto = null;
		AdminDAO dao = new AdminDAO();
		try {
			questionDto = dao.getQuestion(idx);
			if(questionDto != null) {
				int result = dao.updatePwQuestion(idx, req.getParameter("content"));
				questionDto = dao.getQuestion(idx);	// 바꾼 데이터로 갱신
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();

		if(questionDto != null) {
			map.put("content", questionDto.getContent());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}
	
}
