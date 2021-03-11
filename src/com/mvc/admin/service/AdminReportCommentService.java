package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.dao.AdminDAO;
import com.mvc.report.dto.ReportDTO;

public class AdminReportCommentService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminReportCommentService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void list() throws ServletException, IOException {
		String page = "admin/reportComment.jsp";
		LinkedList<ReportDTO> list = null;
		
		AdminDAO comDao = new AdminDAO();
		
		try {
			list = comDao.getReportList();
			if(list != null) {
				ArrayList<ReportDTO> filteredList = filter(list);
				req.setAttribute("list", filteredList);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			comDao.resClose();
		}
		
		String finalPage = (String)req.getAttribute("finalPage");
		if(finalPage != null) {
			page = finalPage;
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);	
	}
	
	private ArrayList<ReportDTO> filter(LinkedList<ReportDTO> list) {
		// 댓글 신고 번호 2002
		ArrayList<ReportDTO> result = new ArrayList<ReportDTO>();
		for(ReportDTO dto : list) {
			if(dto.getTypeIdx() == 2002) {
				result.add(dto);
			}
		}
		
		return result;
	}
}
