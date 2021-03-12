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
import com.mvc.movie.dto.MovieDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdminReviewService{

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void reviewList() throws ServletException, IOException {
		String nextPage = "list";
		// 최종 도착 페이지 설정
		String finalPage = "review.jsp";
		req.setAttribute("finalPage", finalPage);
		
		ArrayList<ReviewDTO> reviewList = null;
		ArrayList<MovieDTO> movieList = null;
		
		AdminDAO dao = new AdminDAO();
		try {
			reviewList = dao.getReviewList();
			
			if(reviewList != null) {
				movieList = new ArrayList<MovieDTO>();
				
				for(ReviewDTO reviewDto : reviewList) {
					MovieDTO movieDto = dao.getMovie(reviewDto.getMovieCode());
					movieList.add(movieDto);
				}	
			}
			
			req.setAttribute("reviewList", reviewList);
			req.setAttribute("movieList", movieList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(finalPage);
		dis.forward(req, resp);	
	}

	public void toggleDelType() throws ServletException, IOException {
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
	}
}
