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

public class AdminReportReviewService{

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminReportReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void reportReviewList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정.
		String finalPage = "reportReview.jsp";
		req.setAttribute("finalPage", finalPage);
		
		LinkedList<ReportDTO> reportList = null;
		ArrayList<ReviewDTO> reviewList = null;

		AdminDAO reportDao = new AdminDAO();
		try {
			ArrayList<ReportDTO> filteredReportList = null;
			
			reportList = reportDao.getReportList();
			reviewList = new ArrayList<ReviewDTO>();
			
			if (reportList != null) {
				// 타입 번호가 2001인 것만 추출.
				filteredReportList = filter(reportList);
				
				// 신고 리스트에서 글 번호를 가져온 후, 신고당한 회원의 리뷰를 추출.
				for(ReportDTO reportDto : filteredReportList) {
					ReviewDTO reviewDto = reportDao.getReview(reportDto.getReport_idx());
					reviewList.add(reviewDto);
				}
				
				req.setAttribute("reportList", filteredReportList);
				req.setAttribute("reviewList", reviewList);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reportDao.resClose();
		}

		RequestDispatcher dis = req.getRequestDispatcher(nextPage);
		dis.forward(req, resp);
	}


	public void toggleCompleteType() throws ServletException, IOException {
		String strIdx = req.getParameter("idx");
		int idx = 0;
		if(strIdx != null) {
			idx = Integer.parseInt(strIdx);
		}
		
		ReportDTO reportDto = null;
		AdminDAO reportDao = new AdminDAO();
		try {
			reportDto = reportDao.getReport(idx);
			if(reportDto != null) {
				int result = reportDao.toggleComplete(reportDto);
				reportDto = reportDao.getReport(idx);	// 토글한 데이터도 갱신
			}
			//System.out.println(result);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reportDao.resClose();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
	
		if(reportDao != null) {
			map.put("complete", reportDto.getComplete());
		}
		
		Gson gson =  new Gson();
		String json = gson.toJson(map);
		//System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*");
		resp.getWriter().print(json);
	}
	
	private ArrayList<ReportDTO> filter(LinkedList<ReportDTO> list) {
		// 리뷰 신고 번호 2001
		ArrayList<ReportDTO> result = new ArrayList<ReportDTO>();
		for (ReportDTO dto : list) {
			if (dto.getType_idx() == 2001) {
				result.add(dto);
			}
		}

		return result;
	}
}
