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
		String loginId = (String) req.getAttribute("loginId");
		if (loginId != null) {
			String id = req.getParameter("id");
			System.out.println(id + "님의 회원정보");

			String msg = "현재 이용이 불가합니다.";
			String page = "./";

			MemberDAO dao = new MemberDAO();
			MemberDTO dto = new MemberDTO();
			try {
				dto = dao.updateForm(id);

				QuestionDTO qDto = new QuestionDTO();
				if (dto != null) {
					System.out.println("데이터 보내주기");
					req.setAttribute("mDto", dto);
					req.setAttribute("qDto", qDto);
					msg = "";
					page = "updateMember.jsp";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}

			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./");
		}
	}

	public void updateMember() throws ServletException, IOException {
		String loginId = (String) req.getAttribute("loginId");
		if (loginId != null) {
			FileService file = new FileService(req);
			MemberDTO dto = file.regist();
			System.out.println(dto.getOriFileName() + "=>" + dto.getNewFileName());

			MemberDAO dao = new MemberDAO();
			int success;
			try {
				success = dao.updateMember(dto);
				if (dto.getOriFileName() != null) {
					int idx = dto.getIdx();
					String delFileName = dao.getFileName(String.valueOf(idx));
					int change = dao.savePhoto(delFileName, dto);
					System.out.println("교체한 파일 갯수 : " + change);
					if (delFileName != null) {
						file.delete(delFileName);
					}
				}
				if (success > 0) {
					req.setAttribute("photoPath", dto.getNewFileName());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}

			RequestDispatcher dis = req.getRequestDispatcher("/updateMF?id=" + req.getAttribute("loginId"));
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("./");
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

	public boolean login() {
		MemberDAO dao = new MemberDAO();
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		System.out.println(id + "/" + pw);

		boolean result = false;
		;
		try {
			result = dao.login(id, pw);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
		}

		return result;
	}

	public void memberList() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		if (loginId != null) {
			MemberDAO dao = new MemberDAO();
			ArrayList<MemberDTO> member_list;
			try {
				member_list = dao.getMemberList();
				ArrayList<ReviewDTO> top_list = dao.top();
				if (member_list == null) {
					req.setAttribute("member_list", req.getParameter("keyWord_list"));
				} else {
					req.setAttribute("member_list", member_list);
					req.setAttribute("top_list", top_list);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dao.resClose();
			}

			RequestDispatcher dis = req.getRequestDispatcher("member.jsp");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}

	public void search() throws ServletException, IOException {
		String loginId = (String) req.getSession().getAttribute("loginId");
		if (loginId != null) {// search?search=id&keyWord=0310
			String keyWord = req.getParameter("keyWord");
			String search = req.getParameter("search");
			System.out.println("검색조건:" + search + "검색 요청한 키워드:" + keyWord);

			MemberDAO dao = new MemberDAO();
			ArrayList<MemberDTO> keyWord_list;
			try {
				keyWord_list = dao.search(keyWord);

				if (keyWord_list != null) {
					req.setAttribute("keyWord_list", keyWord_list);
					switch (search) {

					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher dis = req.getRequestDispatcher("/member");
			dis.forward(req, resp);
		} else {
			resp.sendRedirect("index.jsp");
		}
	}
}