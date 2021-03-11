package com.mvc.movie.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.movie.service.MovieService;

@WebServlet({ "/home", "/list", "/detail","/likeMovie" })
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
		req.setCharacterEncoding("UTF-8"); // 한글깨짐 방지
		System.out.println("요청 확인");
		// 요청 URL 추출
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String subAddr = uri.substring(ctx.length());
		System.out.println("subAdde : " + subAddr); // 요청 URL 확인

		MovieService service = new MovieService(req, resp);

		switch (subAddr) {
		case "/home":
			System.out.println("TOP10 불러오기");
			service.top();
			break;

		case "/list":
			System.out.println("리스트 불러오기");
			service.list();
			break;

		case "/detail":
			System.out.println("상세보기 요청");
			service.detail();
			break;
			
		case "/likeMovie":
			System.out.println("회원이 좋아요한 영화 요청");
			service.detail();
			break;
		}
	}

}
