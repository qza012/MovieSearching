package com.mvc.movie.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.movie.service.MovieService;
import com.mvc.review.service.ReviewService;

@WebServlet({ "/movie/home", "/movie/movielist", "/movie/moviedetail", 
	"/likeMovie","/myPage/iLikeMovie"})
public class MovieController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		System.out.println("요청 확인");

		String sub = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("sub : " + sub);

		MovieService service = new MovieService(req, resp);

		switch (sub) {
		case "/movie/home":
			System.out.println("홈으로");
			service.main();
			break;

		case "/movie/movielist":
			System.out.println("영화 리스트 불러오기");
			service.list();
			break;

		case "/movie/moviedetail":
			System.out.println("영화 상세보기 요청");
			service.detail();
			break;

		case "/likeMovie":
			System.out.println("회원이 좋아요한 영화 요청");
			service.likeMovie();
			break;
			
		case "/myPage/iLikeMovie":
			System.out.println("임시 url");
			resp.sendRedirect("./likeMovie.jsp");
			break;
		}
	}

}
