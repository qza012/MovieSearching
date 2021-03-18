package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.admin.dao.AdminDAO;
import com.mvc.admin.util.AdminSql;
import com.mvc.admin.util.AdminUtil;
import com.mvc.report.dto.ReportDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdminReportReviewService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;

	public AdminReportReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void reportReviewList() throws ServletException, IOException {
		if (AdminUtil.IsLogin(req)) {
			String nextPage = "list";
			// 최종 도착 페이지 설정.
			String finalPage = "reportReview.jsp";
			req.setAttribute("finalPage", finalPage);

			String standard = req.getParameter("standard");
			String keyWord = req.getParameter("keyWord");
			String strCurPage = req.getParameter("curPage");
			String strRowsPerPage = req.getParameter("rowsPerPage");
			// AdminUtil.log(standard);
			// 값이 request에 존재하면 가져옴. default : curPage 1, rowsPerPage 10
			int curPage = (strCurPage != null) ? Integer.parseInt(strCurPage) : 1;
			int rowsPerPage = (strRowsPerPage != null) ? Integer.parseInt(strRowsPerPage) : 10;

			List<ReportDTO> reportList = null;
			List<ReportDTO> filteredReportList = null;
			List<ReviewDTO> reviewList = null;

			AdminDAO dao = new AdminDAO();
			try {

				if (keyWord == null || keyWord.equals("")) {
					reportList = dao.getReportList(curPage, rowsPerPage);
					// 타입 번호가 2001인 것만 추출.
					filteredReportList = filterReportList(reportList);

					req.setAttribute("maxPage", dao.getRowCount(AdminSql.REPORT_TABLE) / rowsPerPage + 1);
					req.removeAttribute("keyWord");

					reviewList = new ArrayList<ReviewDTO>();
					// 신고 리스트에서 글 번호를 가져온 후, 신고당한 회원의 리뷰를 추출.
					for (ReportDTO reportDto : filteredReportList) {
						ReviewDTO reviewDto = dao.getReview(reportDto.getReport_idx());
						reviewList.add(reviewDto);
					}

				} else {
					reportList = dao.getReportList(curPage, rowsPerPage, standard, keyWord);
					// 타입 번호가 2001인 것만 추출.
					filteredReportList = filterReportList(reportList);

					req.setAttribute("maxPage", dao.getRowCount(AdminSql.REPORT_TABLE, standard, keyWord) / rowsPerPage + 1);
					req.setAttribute("keyWord", keyWord);

				}

				reviewList = new ArrayList<ReviewDTO>();
				// 신고 리스트에서 글 번호를 가져온 후, 신고당한 회원의 리뷰를 추출.
				for (ReportDTO reportDto : filteredReportList) {
					ReviewDTO reviewDto = dao.getReview(reportDto.getReport_idx());
					reviewList.add(reviewDto);
				}

				req.setAttribute("curPage", curPage);
				req.setAttribute("standard", standard);
				req.setAttribute("reportList", filteredReportList);
				req.setAttribute("reviewList", reviewList);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}

			RequestDispatcher dis = req.getRequestDispatcher(nextPage);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void toggleCompleteType() throws ServletException, IOException {
		if (AdminUtil.IsLogin(req)) {
			String strIdx = req.getParameter("idx");
			int idx = 0;
			if (strIdx != null) {
				idx = Integer.parseInt(strIdx);
			}

			ReportDTO reportDto = null;
			AdminDAO reportDao = new AdminDAO();
			try {
				reportDto = reportDao.getReport(idx);
				if (reportDto != null) {
					int result = reportDao.toggleComplete(reportDto);
					reportDto = reportDao.getReport(idx); // 토글한 데이터도 갱신
				}
				// System.out.println(result);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				reportDao.resClose();
			}

			Map<String, Object> map = new HashMap<String, Object>();

			if (reportDao != null) {
				map.put("complete", reportDto.getComplete());
			}

			Gson gson = new Gson();
			String json = gson.toJson(map);
			// System.out.println(json);

			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*");
			resp.getWriter().print(json);
		} else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	private List<ReportDTO> filterReportList(List<ReportDTO> list) {
		return list.stream()
				.filter(dto -> dto.getType_idx() == 2001)
				.collect(Collectors.toList());
	}
}
