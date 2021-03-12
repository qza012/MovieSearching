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
import com.mvc.comment.dto.CommentDTO;

public class AdminCommentService{

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminCommentService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	
	public void commentList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정.
		String finalPage = "comment.jsp";
		req.setAttribute("finalPage", finalPage);
		
		ArrayList<CommentDTO> list = null;
		
		AdminDAO dao = new AdminDAO();
		try {
			list = dao.getCommentList();
			if(list != null) {
				req.setAttribute("list", list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
		dis.forward(req, resp);	
	}
	
	public void  toggleDelType() throws ServletException, IOException {
		String strIdx = req.getParameter("idx");
		int idx = 0;
		if(strIdx != null) {
			idx = Integer.parseInt(strIdx);
		}
		
		CommentDTO comDto = null;
		AdminDAO comDao = new AdminDAO();
		try {
			comDto = comDao.getComment(idx);
			if(comDto != null) {
				int result = comDao.toggleDelType(comDto);
				comDto = comDao.getComment(idx);	// 토글한 데이터로 갱신
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			comDao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(comDto != null) {
			map.put("del_type", comDto.getDel_type());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}
}
