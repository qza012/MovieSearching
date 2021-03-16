package com.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.admin.service.AdminMovieService;

@WebServlet({"/admin/movieList", "/admin/updateYoutubeUrl", "/admin/updatePosterUrl"})
public class AdminMovieController extends HttpServlet {

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
		String sub = req.getRequestURI().substring(req.getContextPath().length());
		System.out.println("호출한 태그 : " + sub);
		
		AdminMovieService service = new AdminMovieService(req, resp);
		
		switch (sub) {
		case "/admin/movieList" :
			System.out.println("/admin/movieList 요청");
			service.movieList();
			break;
			
		case "/admin/updateYoutubeUrl" :
			System.out.println("/admin/updateYoutubeUrl 요청");
			service.updateYoutubeUrl();
			break;
			
		case "/admin/updatePosterUrl" :
			System.out.println("/admin/updatePosterUrl 요청");
			service.updatePosterUrl();
			break;
		}
	}

	
}
