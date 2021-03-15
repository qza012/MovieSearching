package com.mvc.review.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
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
		String pageParam = req.getParameter("page");
		
		//1페이지 그룹 -> 1~10번
		int group = 1;
		if(pageParam !=null) {
			group = Integer.parseInt(pageParam);
		}
		
		ReviewDAO dao = new ReviewDAO();
		HashMap<String, Object> map = dao.list(group);
		dao.resClose();
		
		String page="review/reviewList.jsp";
		
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("review", map.get("list"));
		req.setAttribute("currPage", group);
		
		//특정 페이지로 보내기
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void detail() throws ServletException, IOException {
		int reviewIdx = Integer.parseInt(req.getParameter("Idx"));
		System.out.println(reviewIdx);
		
		//테스트용
		req.getSession().setAttribute("loginId", "relike");
		String loginId = (String)req.getSession().getAttribute("loginId");
		
		ReviewDTO dto = new ReviewDTO();
		ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
		ReviewDAO dao = new ReviewDAO();
		
		//리뷰 상세 가져오기
		dto = dao.detail(reviewIdx);
		
		//현재 로그인한 회원이 이 리뷰에 좋아요 눌렀는지 가져오기
		int reviewLike = 0;
		if(dao.reviewLikeCheck(reviewIdx, loginId)) {
			reviewLike = 1;	
		}
		req.setAttribute("reviewLike", reviewLike);

		//댓글 가져오기
		list = dao.commentList(reviewIdx);
		dao.resClose();
		
		if(dto!=null) {
			req.setAttribute("review", dto);
			if(list!=null) {
				req.setAttribute("comment", list);
			}
		}
		req.getRequestDispatcher("review/reviewDetail.jsp").forward(req, resp);
	}
	
	public void memReviewList() throws ServletException, IOException {
//		String loginId = (String) req.getSession().getAttribute("loginId");
//		if(loginId!=null) {		
			String id = req.getParameter("id");
			System.out.println("상세보기 할 id:"+id);
			ReviewDAO dao = new ReviewDAO();
			try {
				ArrayList<ReviewDTO> review_list = dao.memReviewList(id);
				if(review_list!=null) {
					req.setAttribute("review_list", review_list);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher dis = req.getRequestDispatcher("member/review.jsp");
			dis.forward(req, resp);
//		}else {
//			resp.sendRedirect("index.jsp");
//		}
		
	}

	public void updateFrom() throws ServletException, IOException {
		int reviewIdx = Integer.parseInt(req.getParameter("Idx"));
		System.out.println(reviewIdx);
		
		ReviewDTO dto = new ReviewDTO();
		ReviewDAO dao = new ReviewDAO();
		
		dto = dao.updateForm(reviewIdx);
		dao.resClose();
		
		if(dto != null) {
			req.setAttribute("review", dto);
		}
		req.getRequestDispatcher("review/reviewUpdate.jsp").forward(req, resp);
	}

	public void update() throws IOException {
		int reviewIdx = Integer.parseInt(req.getParameter("idx"));
		String subject = req.getParameter("subject");
		String id = req.getParameter("id");
		String movieCode = req.getParameter("movieCode");
		String movieName = req.getParameter("movieName");
		int score = Integer.parseInt(req.getParameter("score"));
		String content = req.getParameter("content");
		
		System.out.println(reviewIdx + " / " + subject + " / " + id + " / " + movieCode + " / " + movieName + " / " + score + " / " + content);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String msg ="리뷰수정에 실패했습니다.";
		int success = 0;
		
		ReviewDTO dto = new ReviewDTO();
		ReviewDAO dao = new ReviewDAO();
		dto.setIdx(reviewIdx);
		dto.setSubject(subject);
		dto.setId(id);
		dto.setMovieCode(movieCode);
		dto.setScore(score);
		dto.setContent(content);
		
		if(dao.update(dto)) {
			msg="리뷰수정에 성공했습니다.";
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

	public void del() throws ServletException, IOException {
		int reviewIdx = Integer.parseInt(req.getParameter("Idx"));
		System.out.println(reviewIdx);
		
		String msg="삭제에 실패했습니다.";
		String page="/reviewDetail?Idx="+reviewIdx;
		
		ReviewDAO dao = new ReviewDAO();
		if(dao.del(reviewIdx)) {
			msg="삭제가 완료되었습니다.";
			page="/reviewList";
		}
		dao.resClose();
		
		req.setAttribute("msg", msg);
		req.getRequestDispatcher(page).forward(req, resp);
	}
	
	public void myReviewList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			ReviewDAO dao = new ReviewDAO();
			ArrayList<ReviewDTO> list = dao.myReviewList(loginId);
			
			String page="./main.jsp";
			
			if(list!=null) {
				page="./reviewList.jsp";
			}
			dao.resClose();
			req.setAttribute("list", list);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}	
	}

	public void iLikeReview() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			ReviewDAO dao = new ReviewDAO();
			ArrayList<ReviewDTO> list = dao.myLikeReview(loginId);
			
			String page="./main.jsp";
			
			if(list!=null) {
				page="./likeReview.jsp";
			}
			dao.resClose();
			req.setAttribute("list", list);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}

	public void iDonotLike() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String id = (String) req.getSession().getAttribute("myLoginId");
			String idx = req.getParameter("idx");
			System.out.println(id+"님이, "+idx+"번 리뷰 좋아요 취소.");
			
			ReviewDAO dao = new ReviewDAO();
			boolean success = dao.iDonotLike(id,idx);
			
			if(success) {
				System.out.println("좋아요 취소!");
			}
			dao.resClose();
			
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/iLikeReview?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}

	public void deleteMyReview() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String id = (String) req.getSession().getAttribute("myLoginId");
			String idx = req.getParameter("idx");
			System.out.println(id+"님이, "+idx+"번 리뷰 삭제.");
			
			ReviewDAO dao = new ReviewDAO();
			boolean success = dao.deleteReview(id,idx);
			
			if(success) {
				System.out.println("리뷰 삭제!");
			}
			dao.resClose();
			
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/myReviewList?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}

	public void commentWrite() throws IOException {
		String content = req.getParameter("content");
		String id = req.getParameter("id");
		int review_idx = Integer.parseInt(req.getParameter("review_idx"));
		
		System.out.println(review_idx + " / " + id + " / " + content);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String msg ="댓글성에 실패했습니다.";
		int success = 0;
		
		CommentDTO dto = new CommentDTO();
		ReviewDAO dao = new ReviewDAO();
		dto.setContent(content);
		dto.setId(id);
		dto.setReview_idx(review_idx);
		
		if(dao.commentWrite(dto)) {
			msg="댓글작성에 성공했습니다.";
			success = 1;
		}
		dao.resClose();
		
		//map에 msg와 success 담기
		map.put("msg", msg);
		map.put("success",success);
		
		Gson gson = new Gson(); //gson 라이브러리 추가 후 객체화
		String json = gson.toJson(map); //map을 json형태로 변환
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*"); 
		
		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	public void commentUpdateForm() throws IOException {
		int comment_idx = Integer.parseInt(req.getParameter("comment_idx"));
		System.out.println(comment_idx);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int success = 0;
		String content = null;
		
		ReviewDAO dao = new ReviewDAO();
		content = dao.commentUpdateForm(comment_idx);
		if(content != null) {
			success = 1;
		}
		dao.resClose();
		
		map.put("success",success);
		map.put("content", content);
		
		Gson gson = new Gson(); //gson 라이브러리 추가 후 객체화
		String json = gson.toJson(map); //map을 json형태로 변환
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*"); 
		
		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	public void commentUpdate() throws IOException {
		int comment_idx = Integer.parseInt(req.getParameter("comment_idx"));
		String content = req.getParameter("content");
		System.out.println(comment_idx + " / " + content);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int success = 0;
		String msg = "댓글 수정에 실패했습니다.";
		
		ReviewDAO dao = new ReviewDAO();
		if(dao.commentUpdate(comment_idx, content)) {
			success = 1;
			msg = "댓글 수정에 성공했습니다.";
		}
		dao.resClose();
	
		map.put("success",success);
		map.put("msg", msg);
		
		Gson gson = new Gson(); //gson 라이브러리 추가 후 객체화
		String json = gson.toJson(map); //map을 json형태로 변환
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*"); 
		
		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	public void commentDel() throws ServletException, IOException {
		int comment_idx = Integer.parseInt(req.getParameter("idx"));
		int review_idx = Integer.parseInt(req.getParameter("review_idx"));
		System.out.println(comment_idx + " / " + review_idx);
		
		String msg = "댓글 삭제에 실패했습니다.";
		String page= "/reviewDetail?Idx="+review_idx;
		
		ReviewDAO dao = new ReviewDAO();
		if(dao.commentDel(comment_idx)) {
			msg="댓글을 삭제했습니다.";
		}
		dao.resClose();
		
		req.setAttribute("msg", msg);
		req.getRequestDispatcher(page).forward(req, resp);
		
	}

	public void reviewLike() throws IOException {
		//테스트용
		String loginId = (String)req.getSession().getAttribute("loginId");
		int review_idx = Integer.parseInt(req.getParameter("review_idx"));
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int reviewLikeState = 0;
		int success = 0;
		
		ReviewDAO dao = new ReviewDAO();
		if(dao.reviewLikeCheck(review_idx, loginId)) {
			reviewLikeState = 1;
		}
		if(reviewLikeState == 0) {
			if(dao.reviewLikeUp(review_idx, loginId)) {
				success = 1;
			}
		}else if(reviewLikeState == 1){
			if(dao.reviewLikeDown(review_idx, loginId)) {
				success = 1;
			}
		}
		dao.resClose();
		
		map.put("success",success);
		
		Gson gson = new Gson(); //gson 라이브러리 추가 후 객체화
		String json = gson.toJson(map); //map을 json형태로 변환
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*"); 
		
		PrintWriter out = resp.getWriter();
		out.println(json);
	}
}
