package com.mvc.review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.review.service.ReviewService;

@WebServlet({"/reviewList","/reviewDetail","/reviewWrite","/member/memReviewList","/reviewUpdateForm","/reviewUpdate","/reviewDel",
	"/myPage/myReviewList","/myPage/deleteMyReview","/myPage/iLikeReview","/myPage/iDonotLike","/commentWrite",
	"/commentUpdateForm","/commentUpdate","/commentDel","/reviewLike","/reviewReportForm","/reviewReport",
	"/reviewMovieSearch","/reviewMovieChoice"})
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
			
		case "/member/memReviewList":
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
			
		case "/myPage/myReviewList":
			System.out.println("내가 쓴 리뷰리스트로");
			service.myReviewList();
			break;
			
		case "/myPage/deleteMyReview":
			System.out.println("내가 쓴 리뷰 삭제");
			service.deleteMyReview();
			break;
		
		case "/myPage/iLikeReview":
			System.out.println("좋아요한 리뷰 리스트로");
			service.iLikeReview();
			break;
			
		case "/myPage/iDonotLike":
			System.out.println("좋아요 취소하기");
			service.iDonotLike();
			break;
			
		case "/commentWrite":
			System.out.println("댓글 작성 요청");
			service.commentWrite();
			break;
			
		case "/commentUpdateForm":
			System.out.println("댓글 수정폼 요청");
			service.commentUpdateForm();
			break;
			
		case "/commentUpdate":
			System.out.println("댓글 수정 요청");
			service.commentUpdate();
			break;
			
		case "/commentDel":
			System.out.println("댓글 삭제 요청");
			service.commentDel();
			break;
			
		case "/reviewLike":
			System.out.println("좋아요 상태 변경 요청");
			service.reviewLike();
			break;
			
		case "/reviewReportForm":
			System.out.println("신고 창 요청");
			service.reportForm();
			break;
			
		case "/reviewReport":
			System.out.println("신고 요청");
			service.report();
			break; 
			
		case "/reviewMovieSearch":
			System.out.println("리뷰쓸 영화 검색");
			service.reviewMovieSearch();
			break;
			
		case "/reviewMovieChoice":
			System.out.println("리뷰쓸 영화 선택");
			service.reviewMovieChoice();
			break;
		}
	}
	
}
