package com.mvc.admin.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.admin.dao.AdminDAO;
import com.mvc.admin.util.AdminSql;
import com.mvc.admin.util.AdminUtil;
import com.mvc.movie.dto.MovieDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdminReviewService{

	private final HttpServletRequest req;
	private final HttpServletResponse resp;
	
	public AdminReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void reviewList() throws ServletException, IOException {
		if (AdminUtil.IsLogin(req)) {
			String nextPage = "review.jsp";
			
			String standard = req.getParameter("standard");
			String keyWord = req.getParameter("keyWord");
			String strCurPage = req.getParameter("curPage");
			String strRowsPerPage = req.getParameter("rowsPerPage");
			
			// 값이 request에 존재하면 가져옴.  default : curPage 1, rowsPerPage 10
			int curPage = (strCurPage != null) ? Integer.parseInt(strCurPage) : 1;
			int rowsPerPage = (strRowsPerPage != null) ? Integer.parseInt(strRowsPerPage) : 10;
			
			List<ReviewDTO> reviewList = null;
			List<MovieDTO> movieList = null;
			
			AdminDAO dao = new AdminDAO();
			try {
				
				if(keyWord == null || keyWord.equals("")) {
					reviewList = dao.getReviewList(curPage, rowsPerPage);
					req.setAttribute("maxPage", dao.getRowCount(AdminSql.REVIEW_TABLE)/rowsPerPage + 1);
					req.removeAttribute("keyWord");
				} else {
					reviewList = dao.getReviewList(curPage, rowsPerPage, standard, keyWord);
					req.setAttribute("maxPage", dao.getRowCount(AdminSql.REVIEW_TABLE, standard, keyWord)/rowsPerPage + 1);
					req.setAttribute("keyWord", keyWord);
				}
				
				movieList = new ArrayList<MovieDTO>();
				// 리뷰 리스트에서 영화 코드를 가져온 후, 영화 세부사항을 추출.
				for(ReviewDTO reviewDto : reviewList) {
					MovieDTO movieDto = dao.getMovie(reviewDto.getMovieCode());
					movieList.add(movieDto);
				}
				
				req.setAttribute("curPage", curPage);
				req.setAttribute("standard", standard);
				req.setAttribute("reviewList", reviewList);
				req.setAttribute("movieList", movieList);
				
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

	public void toggleDelType() throws ServletException, IOException {
		if (AdminUtil.IsLogin(req)) {
			String strIdx = req.getParameter("idx");
			int idx = strIdx != null ? Integer.parseInt(strIdx) : 0;
	
			ReviewDTO reviewDto = null;
			AdminDAO adminDao = null;
			
			try {
	
				adminDao = new AdminDAO();
				reviewDto = adminDao.getReview(idx);
	
				if (reviewDto != null) {
					int result = adminDao.toggleDelType(reviewDto);
					reviewDto = adminDao.getReview(idx);	// 토글한 데이터도 갱신
				}
				// System.out.println(result);
	
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				adminDao.resClose();
			}
	
			Map<String, Object> map = new HashMap<String, Object>();
	
			if (reviewDto != null) {
				map.put("del_type", reviewDto.getDel_type());
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
}
