package com.mvc.review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.review.service.ReviewService;

@WebServlet({"/reviewList","/reviewDetail","/reviewWrite"})
public class ReviewController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req,resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String sub = uri.substring(ctx.length());
		
		System.out.println(sub);
		
		ReviewService service = new ReviewService(req, resp);
		
		switch(sub) {
		case "/reviewList":
			System.out.println("리뷰 리스트 요청");
			service.list();
			break;
		
		case "/reviewDetail":
			System.out.println("리뷰 상세 요청");
			service.detail();
			break;
			
		case "/reviewWrite":
			System.out.println("리뷰 작성");
			service.write();
			break;
		
		}
	}
	
}
