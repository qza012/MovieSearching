package com.mvc.review.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.comment.dto.CommentDTO;
import com.mvc.review.dao.ReviewDAO;
import com.mvc.review.dto.ReviewDTO;

public class ReviewService {
	
	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	
	public ReviewService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void write() throws IOException {
		String subject = req.getParameter("subject");
		String id = req.getParameter("id");
		String movieCode = req.getParameter("movieCode");
		String movieName = req.getParameter("movieName");
		int score = Integer.parseInt(req.getParameter("score"));
		String content = req.getParameter("content");
		
		System.out.println(subject + " / " + id + " / " + movieCode + " / " + movieName + " / " + score + " / " + content);
		
		//비동기 방식은 데이터를 다른 페이지에 전달 할 수 없다.
		//(요청한 페이지에게만 보낼 수 있음 asyncLogin.jsp에서 받았으면 거기에만 보낼 수 있음 result.jsp로 못보냄)
		//response객체를 통해서 답변하므로 다른 페이지로 데이터를 보낼 수 없다. => request사용 못함, map에 데이터 담기
		
		//json과 HashMap이 가장 비슷한 형태
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String msg ="리뷰작성에 실패했습니다.";
		int success = 0;
		
		ReviewDTO dto = new ReviewDTO();
		ReviewDAO dao = new ReviewDAO();
		dto.setSubject(subject);
		dto.setId(id);
		dto.setMovieCode(movieCode);
		dto.setScore(score);
		dto.setContent(content);
		
		if(dao.write(dto)) {
			msg="리뷰작성에 성공했습니다.";
			success = 1;
		}
		dao.resClose();
		//map에 msg와 success 담기
		map.put("msg", msg);
		map.put("success",success);
		
		//맵형태로 전달하면 javascript에서 읽을 수 없어서 map을 json 형태로 변경
		//map -> json으로 바꾸기 작업 (gson사용)
		Gson gson = new Gson(); //gson 라이브러리 추가 후 객체화
		String json = gson.toJson(map); //map을 json형태로 변환
		System.out.println(json);
		
		//이 컨텐츠가 보낼 데이터 타입과 한글깨짐 방지를 위한 인코딩 타입 지정
		resp.setContentType("text/html; charset=UTF-8");
		
		//javascript에서 다른 도메인으로 통신은 기본적으로 안됨(cross domain issue) , 자바스크립트는 보안에 취약해서 안됨
		//그래서 접속하려는 것에 대해 허용해야함
		resp.setHeader("Access-Control-Allow", "*"); 
		
		//resp에서 쓸수있는 객체를 하나 꺼내서 json을 씀
		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	public void list() throws ServletException, IOException {
		ReviewDAO dao = new ReviewDAO();
		
		ArrayList<ReviewDTO> list = dao.list();
		dao.resClose();
		
		String page="reviewList.jsp";
		
		if(list!=null) {
			page="reviewList.jsp";
		}
		
		req.setAttribute("list", list);
		req.getRequestDispatcher(page).forward(req, resp);
		
	}

	public void detail() throws ServletException, IOException {
		int reviewIdx = Integer.parseInt(req.getParameter("Idx"));
		System.out.println(reviewIdx);
		
		ReviewDTO review_dto = new ReviewDTO();
		CommentDTO comment_dto = new CommentDTO();
		ReviewDAO dao = new ReviewDAO();
		
		//리뷰 상세 가져오기
		review_dto = dao.dtail(reviewIdx);
		
		//댓글 가져오기
		comment_dto = dao.commentList(reviewIdx);
		
		dao.resClose();
		if(review_dto!=null) {
			req.setAttribute("review", review_dto);
			if(comment_dto!=null) {
				req.setAttribute("comment", comment_dto);
			}
		}
		
		req.getRequestDispatcher("reviewDetail.jsp").forward(req, resp);
	}

}
