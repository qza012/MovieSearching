package com.mvc.member.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.file.service.FileService;
import com.mvc.follow.dto.FollowDTO;
import com.mvc.member.dao.MemberDAO;
import com.mvc.member.dto.MemberDTO;
import com.mvc.question.dto.QuestionDTO;
import com.mvc.review.dto.ReviewDTO;

public class MemberService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;

	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void updateMemberForm() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String id = req.getParameter("id");
			System.out.println(id+"님의 회원정보");
			
			MemberDAO dao = new MemberDAO();
			MemberDTO dto  = new MemberDTO();
			dto = dao.updateForm(id);
			ArrayList<QuestionDTO> list = new ArrayList<QuestionDTO>();
			list = dao.bringQ();
			
			
			String msg = "현재 이용이 불가합니다.";
			String page="./main.jsp";
			
			if(dto != null) {
				System.out.println("데이터 보내주기");
				req.setAttribute("mDto", dto);
				req.setAttribute("qList", list);
				msg = "";
				page="./updateMember.jsp";
			}
			dao.resClose();
			
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");			
		}
	}

	public void update() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			FileService file = new FileService(req);
			MemberDTO dto = file.regist();
			System.out.println(dto.getOriFileName()+"=>"+dto.getNewFileName());
		
			MemberDAO dao = new MemberDAO();
			int success = dao.updateMember(dto);
			
			if(dto.getOriFileName() != null) {
				String id = dto.getId();
				String delFileName = dao.getFileName(id);
				int change = dao.savePhoto(delFileName,dto);
				System.out.println("교체한 파일 갯수 : "+change);
				if(delFileName != null) {
					file.delete(delFileName);
				}
			}
			if(success > 0) {
				req.setAttribute("photoPath", dto.getNewFileName());
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/updateMF?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}

	public void withdraw() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String id = (String) req.getSession().getAttribute("myLoginId");
			String pw = req.getParameter("userPw");
			System.out.println(id+" / "+pw);
			
			MemberDAO dao = new MemberDAO();
			boolean success = dao.withdraw(id, pw);
			
			String msg = "비밀번호를 다시 확인해주세요!";
			String page="/myPage/withdraw.jsp";
			
			if(success) {
				msg="탈퇴되었습니다.";
				page="./main.jsp";
				req.getSession().removeAttribute("myLoginId");
			}
			dao.resClose();
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}
	
	public void follow() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			String myId = (String) req.getSession().getAttribute("myLoginId");
			String targetId = req.getParameter("targetId");
			System.out.println(myId+"님이, "+targetId+"님을 팔로우");
			
			MemberDAO dao = new MemberDAO();
			boolean success = dao.follow(myId,targetId);
			
			if(success) {
				System.out.println("팔로우 신청!");
			}
			dao.resClose();
			
			RequestDispatcher dis = req.getRequestDispatcher("/myPage/followingList?id="+loginId);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}
	public void idChk() throws IOException {
		String id = req.getParameter("id");
		boolean success = false;
		System.out.println("id : " + id);
		MemberDAO dao = new MemberDAO();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			success = dao.idChk(id);
			System.out.println("아이디 사용여부 : " + success);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
			map.put("use", success);
			Gson gson = new Gson();
			String json = gson.toJson(map);
			System.out.println(json);
			resp.getWriter().print(json);
		}

	}

	public void join() throws IOException {
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String question_idx = req.getParameter("question_idx");
		String pw_answer = req.getParameter("pw_answer");
		String gender = req.getParameter("gender");
		String genre = req.getParameter("genre");
		String email = req.getParameter("email_id") + "@" + req.getParameter("email_sel");

		System.out.println(id + "/" + pw + "/" + name + "/" + age + "/" + question_idx + "/" + pw_answer + "/" + gender
				+ "/" + genre + "/" + email);

		MemberDAO dao = new MemberDAO();
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPw(pw);
		dto.setName(name);
		dto.setAge(Integer.parseInt(age));
		dto.setPw_answer(pw_answer);
		dto.setGender(gender);
		dto.setGenre(genre);
		dto.setEmail(email);
		dto.setQuestion_idx(Integer.parseInt(question_idx));

		int success = 0;
		try {
			success = dao.join(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("use", success);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().print(json);
	}

	public void questionList() throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		ArrayList<QuestionDTO> QuestionList = null;
		try {
			QuestionList = dao.getQuestionlist();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		if (QuestionList != null) {
			req.setAttribute("Qlist", QuestionList);
			RequestDispatcher rd = req.getRequestDispatcher("joinForm.jsp");
			rd.forward(req, resp);
		}
	}

	public void login() throws IOException {
		MemberDAO dao = new MemberDAO();
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		System.out.println(id + "/" + pw);

		boolean result = false;
		
		try {
			result = dao.login(id, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		if(result) {
			req.getSession().setAttribute("id", id);
		}
		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("use", result);
		Gson gson = new Gson();
		String json = gson.toJson(map);
		System.out.println(json);
		resp.getWriter().print(json);
	}

	//회원 목록 불러오기+입력한 키워드가 포함된 아이디 검색기능
	public void getMemberList() throws ServletException, IOException {
//		String loginId = (String) req.getSession().getAttribute("loginId");
//		if (loginId != null) {
		MemberDAO dao = new MemberDAO();
			try {
				String keyWord = req.getParameter("keyWord");
				System.out.println("검색 요청한 키워드:" + keyWord);
				ArrayList<MemberDTO> list = dao.getMemberList();
				ArrayList<ReviewDTO> top_list = dao.top();
				ArrayList<MemberDTO> keyWord_list = dao.search(keyWord);
				if (keyWord== null) {
					req.setAttribute("member_list", list);
				} else {
					req.setAttribute("member_list", keyWord_list);
				}
				req.setAttribute("top_list", top_list);
				RequestDispatcher dis = req.getRequestDispatcher("/member/member.jsp");
				dis.forward(req, resp);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}
//		} else {
//			resp.sendRedirect("index.jsp");
//		}
	}

	public void loginForMyPage() throws ServletException, IOException {
		String id = req.getParameter("userId");
		String pw = req.getParameter("userPw");
		System.out.println(id+" / "+pw);
		
		MemberDAO dao = new MemberDAO();
		boolean success = false;
		try {
			success = dao. loginForMyPage(id,pw);
			if(success) {
				req.getSession().setAttribute("myLoginId", id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}
		RequestDispatcher dis = req.getRequestDispatcher("./main.jsp");
		dis.forward(req, resp);
	}

	public void followingList() throws IOException, ServletException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			MemberDAO dao = new MemberDAO();
			ArrayList<FollowDTO> list = dao.followingList(loginId);
			
			String page="./main.jsp";
			if(list!=null) {
				page="/myPage/followingList.jsp";
				req.setAttribute("fList", list);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}

	public void followerList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("myLoginId");
		if(loginId != null) {
			MemberDAO dao = new MemberDAO();
			ArrayList<FollowDTO> list = dao.followerList(loginId);
			
			String page="./main.jsp";
			if(list!=null) {
				page="/myPage/followerList.jsp";
				req.setAttribute("fList", list);
			}
			dao.resClose();
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./main.jsp");
		}
	}

}