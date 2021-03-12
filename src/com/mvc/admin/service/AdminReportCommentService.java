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
import com.mvc.comment.dto.CommentDTO;
import com.mvc.report.dto.ReportDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdminReportCommentService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminReportCommentService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void reportCommentList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정.
		String finalPage = "reportComment.jsp";
		req.setAttribute("finalPage", finalPage);
		
		LinkedList<ReportDTO> reportList = null;
		ArrayList<CommentDTO> commentList = null;
		
		AdminDAO reportDao = new AdminDAO();
		try {
			ArrayList<ReportDTO> filteredReportList = null;
			
			reportList = reportDao.getReportList();
			commentList = new ArrayList<CommentDTO>();

			if(reportList != null) {
				// 타입 번호가 2002인 것만 추출
				filteredReportList = filter(reportList);
				
				// 신고 리스트에서 글 번호를 가져온 후, 신고당한 회원의 댓글을 추출.
				for(ReportDTO reportDto : filteredReportList) {
					CommentDTO commentDto = reportDao.getComment(reportDto.getReport_idx());
					commentList.add(commentDto);
				}
				
				req.setAttribute("reportList", filteredReportList);
				req.setAttribute("commentList", commentList);
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
		// 댓글 신고 번호 2002
		ArrayList<ReportDTO> result = new ArrayList<ReportDTO>();
		for(ReportDTO dto : list) {
			if(dto.getType_idx() == 2002) {
				result.add(dto);
			}
		}
		
		return result;
	}
	
}
