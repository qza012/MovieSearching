package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.admin.dao.AdminDAO;
import com.mvc.report.dto.ReportDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdimReportReviewService{

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdimReportReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void list() throws ServletException, IOException {
		String page = "admin/reportReview.jsp";
		LinkedList<ReportDTO> list = null;

		AdminDAO comDao = new AdminDAO();

		try {
			list = comDao.getReportList();
			if (list != null) {
				ArrayList<ReportDTO> filteredList = filter(list);
				req.setAttribute("list", filteredList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			comDao.resClose();
		}

		String finalPage = (String) req.getAttribute("finalPage");
		if (finalPage != null) {
			page = finalPage;
		}

		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	private ArrayList<ReportDTO> filter(LinkedList<ReportDTO> list) {
		// 리뷰 신고 번호 2001
		ArrayList<ReportDTO> result = new ArrayList<ReportDTO>();
		for (ReportDTO dto : list) {
			if (dto.getTypeIdx() == 2001) {
				result.add(dto);
			}
		}

		return result;
	}

	public void toggleDelType() throws ServletException, IOException {
		String strIdx = req.getParameter("idx");
		int idx = 0;
		if(strIdx != null) {
			idx = Integer.parseInt(strIdx);
		}
		
		ReviewDTO reviewDto = null;
		AdminDAO reviewDao = new AdminDAO();
		try {
			reviewDto = reviewDao.getReview(idx);
			if(reviewDto != null) {
				int result = reviewDao.toggleDelType(reviewDto);				
			}
			//System.out.println(result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reviewDao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		if(reviewDao != null) {
			map.put("delType", reviewDto.getDel_type());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}
}
