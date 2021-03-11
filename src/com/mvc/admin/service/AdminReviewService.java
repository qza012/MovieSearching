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

import com.mvc.admin.dao.AdminDAO;
import com.mvc.movie.dto.MovieDTO;
import com.mvc.review.dto.ReviewDTO;

public class AdminReviewService{

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	
	public AdminReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void list() throws ServletException, IOException {
		String page = "admin/review.jsp";
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
			
			Map<ArrayList<ReviewDTO>, ArrayList<MovieDTO>> map = new HashMap<ArrayList<ReviewDTO>, ArrayList<MovieDTO>>();
			map.put(reviewList, movieList);

			if(reviewList.size() != 0) {
				req.setAttribute("map", map);				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		
		String finalPage = (String)req.getAttribute("finalPage");
		if(finalPage != null) {
			page = finalPage;
		}
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);	
		
	}
}
