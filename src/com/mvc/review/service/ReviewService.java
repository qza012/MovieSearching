package com.mvc.review.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.comment.dto.CommentDTO;
import com.mvc.report.dto.ReportDTO;
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
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			String subject = req.getParameter("subject");
			String id = req.getParameter("id");
			String movieCode = req.getParameter("movieCode");
			String movieName = req.getParameter("movieName");
			int score = Integer.parseInt(req.getParameter("score"));
			String content = req.getParameter("content");
			
			System.out.println(subject + " / " + id + " / " + movieCode + " / " + movieName + " / " + score + " / " + content);
			
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
			
			map.put("msg", msg);
			map.put("success",success);
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*"); 
			
			PrintWriter out = resp.getWriter();
			out.println(json);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void list() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			String pageParam = req.getParameter("page");
			
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
	
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void detail() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			int reviewIdx = Integer.parseInt(req.getParameter("Idx"));
			System.out.println(reviewIdx);
			
			ReviewDTO dto = new ReviewDTO();
			ArrayList<CommentDTO> list = new ArrayList<CommentDTO>();
			ReviewDAO dao = new ReviewDAO();
			
			dto = dao.detail(reviewIdx);
			
			//현재 로그인한 회원이 이 리뷰에 좋아요 눌렀는지 가져오기
			int reviewLike = 0;
			if(dao.reviewLikeCheck(reviewIdx, myLoginId)) {
				reviewLike = 1;
			}
			req.setAttribute("reviewLike", reviewLike);
	
			//댓글 가져오기
			list = dao.commentList(reviewIdx);
			
			//댓글 갯수 가져오기
			int cntComment = dao.cntComment(reviewIdx);
			dao.resClose();
			
			if(dto!=null) {
				req.setAttribute("review", dto);
				if(list!=null) {
					req.setAttribute("comment", list);
				}
			}
			req.setAttribute("br", "<br/>");
			req.setAttribute("cn", "\n");
			req.setAttribute("cntComment", cntComment);
			req.getRequestDispatcher("review/reviewDetail.jsp").forward(req, resp);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}
	
	public void memReviewList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {	
			String id = req.getParameter("id");
			req.getSession().setAttribute("target_id", id);
			System.out.println("상세보기 할 id:"+id);
			String pageParam = req.getParameter("page");
			System.out.println("page:"+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}	
			ReviewDAO dao = new ReviewDAO();
			try {
				HashMap<String, Object> map = dao.memReviewList(id,group);
					req.setAttribute("maxPage", map.get("maxPage"));
					req.setAttribute("review_list", map.get("list"));
					req.setAttribute("currPage", group);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				dao.resClose();
			}
			RequestDispatcher dis = req.getRequestDispatcher("review.jsp");
			dis.forward(req, resp);
	} else {
		resp.sendRedirect("../movie/home");
	}
}

	public void updateFrom() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
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
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void update() throws IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
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
			
			map.put("msg", msg);
			map.put("success",success);
			
			Gson gson = new Gson(); 
			String json = gson.toJson(map); 
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*"); 
			
			PrintWriter out = resp.getWriter();
			out.println(json);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void del() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
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
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}
	
	public void myReviewList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			
			ReviewDAO dao = new ReviewDAO();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map = dao.myReviewList(loginId,group);
			
			String page="/MovieSearching/movie/home";
			
			if(map!=null) {
				page="/myPage/reviewList.jsp";
				req.setAttribute("list", map.get("list"));
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("currPage", group);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}	
	}

	public void iLikeReview() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String pageParam = req.getParameter("page");
			System.out.println("page : "+pageParam);
			int group = 1;
			if(pageParam != null) {
				group = Integer.parseInt(pageParam);
			}
			
			ReviewDAO dao = new ReviewDAO();
			HashMap<String, Object> map = dao.myLikeReview(loginId,group);
			String page="/MovieSearching/movie/home";
			
			if(map != null) {
				page="/myPage/likeReview.jsp";
				req.setAttribute("list", map.get("list"));
				req.setAttribute("maxPage", map.get("maxPage"));
				req.setAttribute("currPage", group);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("/MovieSearching/movie/home");
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
			resp.sendRedirect("/MovieSearching/movie/home");
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
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void commentWrite() throws IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			String content = req.getParameter("content");
			String id = req.getParameter("id");
			int review_idx = Integer.parseInt(req.getParameter("review_idx"));
			
			System.out.println(review_idx + " / " + id + " / " + content);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			String msg ="댓글작성에 실패했습니다.";
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
			
			map.put("msg", msg);
			map.put("success",success);
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*"); 
			
			PrintWriter out = resp.getWriter();
			out.println(json);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void commentUpdateForm() throws IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
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
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*"); 
			
			PrintWriter out = resp.getWriter();
			out.println(json);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void commentUpdate() throws IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
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
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*"); 
			
			PrintWriter out = resp.getWriter();
			out.println(json);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void commentDel() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
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
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void reviewLike() throws IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			int review_idx = Integer.parseInt(req.getParameter("review_idx"));
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			int reviewLikeState = 0;
			int success = 0;
			
			ReviewDAO dao = new ReviewDAO();
			if(dao.reviewLikeCheck(review_idx, myLoginId)) {
				reviewLikeState = 1;
			}
			if(reviewLikeState == 0) {
				if(dao.reviewLikeUp(review_idx, myLoginId)) {
					success = 1;
				}
			}else if(reviewLikeState == 1){
				if(dao.reviewLikeDown(review_idx, myLoginId)) {
					success = 1;
				}
			}
			
			//이 리뷰의 총 좋아요 갯수 가져오기
			int reviewCntLike = dao.getReviewCntLike(review_idx);
			dao.resClose();
			
			map.put("success",success);
			map.put("reviewLikeState", reviewLikeState);
			map.put("reviewCntLike",reviewCntLike);
			
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			
			resp.setContentType("text/html; charset=UTF-8");
			resp.setHeader("Access-Control-Allow", "*"); 
			
			PrintWriter out = resp.getWriter();
			out.println(json);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}
	
	public void reportForm() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		int idx = Integer.parseInt(req.getParameter("idx"));
		int type_idx = Integer.parseInt(req.getParameter("type_idx"));
		
		System.out.println(myLoginId + " / " + idx + " / " + type_idx);
		
		//이미 신고 했는지 확인
		String msg = "";
		ReviewDAO dao = new ReviewDAO();
		if(dao.reportCheck(myLoginId, idx, type_idx) == false) {
			dao.resClose();
		}else {
			if(type_idx == 2001) {
				msg = "이미 신고한 리뷰입니다.";
			}else {
				msg = "이미 신고한 댓글입니다.";
			}
		}
		req.setAttribute("msg", msg);
		req.setAttribute("idx", idx);
		req.setAttribute("type_idx", type_idx);
		req.getRequestDispatcher("./review/reviewReport.jsp").forward(req, resp);
	}
	
	public void report() throws IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		int type_idx = Integer.parseInt(req.getParameter("type_idx"));
		int report_idx = Integer.parseInt(req.getParameter("report_idx"));
		String content = req.getParameter("content");
		
		System.out.println(myLoginId + " / " + type_idx + " / " + report_idx + " / " + content);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int success = 0;
		String msg = "신고에 실패했습니다.";
		
		ReportDTO dto = new ReportDTO();
		dto.setReport_id(myLoginId);
		dto.setType_idx(type_idx);
		dto.setReport_idx(report_idx);
		dto.setContent(content);
		
		ReviewDAO dao = new ReviewDAO();
		if(dao.report(dto)) {
			success = 1;
			msg="신고되었습니다.";
		}
		dao.resClose();
		
		map.put("success",success);
		map.put("msg", msg);
		
		Gson gson = new Gson(); 
		String json = gson.toJson(map); 
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*"); 
		
		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	public void reviewMovieSearch() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		String pageParam = req.getParameter("page");
		String subName = req.getParameter("subName");
		
		int group = 1;
		if(pageParam !=null) {
			group = Integer.parseInt(pageParam);
		}
		
		System.out.println(pageParam + subName);
		
		ReviewDAO dao = new ReviewDAO();
		HashMap<String, Object> map = dao.reviewMovieSearch(subName, group);
		dao.resClose();
		
		String page="review/movieSearch.jsp";
		
		req.setAttribute("subName", subName);
		req.setAttribute("maxPage", map.get("maxPage"));
		req.setAttribute("movie", map.get("list"));
		req.setAttribute("currPage", group);
		
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
	}

	public void reviewMovieChoice() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		String movieCode = req.getParameter("movieCode");
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		int success = 0;
		int haveReview = 0;
		
		ReviewDAO dao = new ReviewDAO();
		haveReview = dao.reviewMovieCheck(movieCode, myLoginId);
		String movieName = dao.reviewMovieChoice(movieCode);
		
		dao.resClose();
		
		if(movieName != null) {
			success = 1;
		}
		System.out.println(movieCode + movieName);
		
		map.put("haveReview", haveReview);
		map.put("movieCode",movieCode);
		map.put("movieName", movieName);
		map.put("success", success);
		
		Gson gson = new Gson(); 
		String json = gson.toJson(map); 
		System.out.println(json);
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.setHeader("Access-Control-Allow", "*"); 
		
		PrintWriter out = resp.getWriter();
		out.println(json);	
	}

	public void reviewSearchList() throws ServletException, IOException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			String pageParam = req.getParameter("page");
			String search = req.getParameter("search");
			String keyword = req.getParameter("keyword");
			
			if(search == null) {
				search = "movieName";
			}
			if(keyword == null) {
				keyword = "";
			}
			System.out.println(search + keyword);
			
			int group = 1;
			if(pageParam !=null) {
				group = Integer.parseInt(pageParam);
			}
			
			ReviewDAO dao = new ReviewDAO();
			HashMap<String, Object> map = dao.reviewSearchList(group,search,keyword);
			dao.resClose();
			
			String page="review/reviewSearchList.jsp";
			req.setAttribute("maxPage", map.get("maxPage"));
			req.setAttribute("review", map.get("list"));
			req.setAttribute("currPage", group);
			req.setAttribute("search", search);
			req.setAttribute("keyword", keyword);
			
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}

	public void movieReviewWriteForm() throws IOException, ServletException {
		String myLoginId = (String)req.getSession().getAttribute("myLoginId");
		if(myLoginId != null) {
			String movieName = req.getParameter("movieName");
			String movieCode = req.getParameter("movieCode");
			System.out.println(movieName + movieCode);
			
			String page="";
			String msg="";
			if(movieName != null || movieCode != null) {
				page="./review/reviewWrite.jsp";
			}else {
				page="moviedetail?movieCode="+movieCode;
				msg="리뷰작성 페이지 가져오기에 실패했습니다.";
			}
			req.setAttribute("movieName", movieName);
			req.setAttribute("movieCode", movieCode);
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}else {
			resp.sendRedirect("/MovieSearching/movie/home");
		}
	}
}
