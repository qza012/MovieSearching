package com.mvc.review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.review.service.ReviewService;

@WebServlet({"/reviewList","/reviewDetail","/reviewWrite","/memReviewList","/reviewUpdateForm","/reviewUpdate","/reviewDel"})
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
			System.out.println("리뷰 작성 요청");
			service.write();
			break;
			
		case "/memReviewList":
			System.out.println("회원이 작성한 리뷰 요청");
			service.memReviewList();
			break;
			
		case "/reviewUpdateForm":
			System.out.println("리뷰 수정폼 요청");
			service.updateFrom();
			break;
			
		case "/reviewUpdate":
			System.out.println("리뷰 수정 요청");
			service.update();
			break;
			
		case "/reviewDel":
			System.out.println("리뷰 삭제 요청");
			service.del();
			break;
		}
	}
	
}
