package com.mvc.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.member.dto.MemberDTO;
import com.mvc.question.dto.QuestionDTO;
import com.mvc.review.dto.ReviewDTO;

public class MemberDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void resClose() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public MemberDTO updateForm(String id) {
		MemberDTO dto = null;
		String sql = "SELECT id,pw,name,age,gender,email,genre,pw_answer,question_idx "
				+ "FROM member3 WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("데이터 담기");
				dto = new MemberDTO();
				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setAge(rs.getInt("age"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				dto.setGenre(rs.getString("genre"));
				dto.setPw_answer(rs.getString("pw_answer"));
				dto.setQuestion_idx(rs.getInt("question_idx"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}


	public ArrayList<QuestionDTO> bringQ() {
		String sql = "SELECT *FROM question3";
		ArrayList<QuestionDTO> Qlist = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			Qlist = new ArrayList<QuestionDTO>();
			while(rs.next()) {
				QuestionDTO dto = new QuestionDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setContent(rs.getString("content"));
				Qlist.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return Qlist;
	}
	
	public int savePhoto(String delFileName, MemberDTO dto) { //프로필 사진 저장
		int success = 0;
		String sql="";
		try {
			if(delFileName != null) {
				sql = "UPDATE photo3 SET oriFileName=?, newFileName=? WHERE id=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getOriFileName());
				ps.setString(2, dto.getNewFileName());
				ps.setString(3, dto.getId());
			} else {
				sql = "INSERT INTO photo3(idx,oriFileName,newFileName,id) "
						+ "VALUES(photo3_seq.NEXTVAL,?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getOriFileName());
				ps.setString(2, dto.getNewFileName());
				ps.setString(3, dto.getId());
			} 
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	

	public int updateMember(MemberDTO mDto) { //회원정보 수정사항 저장
		int success = 0;
		String sql = "UPDATE member3 SET pw=?, name=?, age=?, gender=?, email=?, genre=?,pw_answer=? WHERE id=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, mDto.getPw());
			ps.setString(2, mDto.getName());
			ps.setInt(3, mDto.getAge());
			ps.setString(4, mDto.getGender());
			ps.setString(5, mDto.getEmail());
			ps.setString(6, mDto.getGenre());
			ps.setString(7, mDto.getPw_answer());
			/*질문 변경 불가
			ps.setInt(8, mDto.getQuestion_idx());
			*/
			ps.setString(8, mDto.getId());
			success = ps.executeUpdate();
			if(success > 0) {
				System.out.println("회원 정보 업데이트 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public String getFileName(String id) { //파일 변경(파일 기보유 여부 확인)
		String delFileName = null;
		String sql = "SELECT newFileName FROM photo3 WHERE id =?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) { 
				delFileName = rs.getString("newFileName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("삭제파일 : "+delFileName);
		return delFileName;
	}

	public boolean withdraw(String id, String pw) {// 회원 탈퇴
		boolean success = false;
		String sql = "SELECT id FROM member3 WHERE id=? AND pw=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if(rs.next()) {
				sql = "UPDATE member3 SET withdraw=?, disable=? WHERE id = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, "Y");
				ps.setString(2, "Y");
				ps.setString(3, rs.getString("id"));
				System.out.println(rs.getString("id"));
				int data = ps.executeUpdate();
				if(data>0) {
					success = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("성공여부 : "+success);
		return success;
	}
	
	public boolean follow(String myId, String targetId) {
		boolean success = false;
		String sql="INSERT INTO follow3(idx,id,target_id) VALUES(follow3_seq.NEXTVAL,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, myId);
			ps.setString(2, targetId);
			int count = ps.executeUpdate();
			if(count>0) {
				System.out.println(myId+"가 팔로우 신청 ->"+targetId);
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean idChk(String id) throws SQLException {
		boolean success = false;
		String sql = "SELECT id FROM member3 WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		success = rs.next();

		return !success;
	}

	public boolean login(String id, String pw) throws SQLException {
		boolean success = false;
		String sql = "SELECT id FROM member3 WHERE id=? AND pw=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, pw);
		rs = ps.executeQuery();
		success = rs.next();

		return success;
	}

	public ArrayList<QuestionDTO> getQuestionlist() throws SQLException {
		String sql = "SELECT *FROM question3";
		ArrayList<QuestionDTO> Qlist = null;

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		Qlist = new ArrayList<QuestionDTO>();
		while (rs.next()) {
			QuestionDTO dto = new QuestionDTO();
			dto.setIdx(rs.getInt("idx"));
			dto.setContent(rs.getString("content"));
			Qlist.add(dto);
		}

		return Qlist;

	}

	public ArrayList<MemberDTO> getMemberList() throws SQLException {
		String sql = "SELECT id, name, age, gender, email, genre, withdraw, disable FROM member3";
		// 아이디 | 이름 | 나이 | 성별 | 이메일 | 장르 | 탈퇴여부 | 활성화 여부
		ArrayList<MemberDTO> list = new ArrayList<MemberDTO>();

		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();

		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));

			list.add(dto);
		}

		rs.close();
		ps.close();
		return list;
	}

	public MemberDTO getMember(String id) throws SQLException {
		String sql = "SELECT id, pw, name, age, gender, email, genre, pw_answer, withdraw, disable, type, question_idx "
				+ "FROM member3 WHERE id=?";

		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();

		MemberDTO dto = null;
		if (rs.next()) {
			dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setPw(rs.getString("pw"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setEmail(rs.getString("email"));
			dto.setGenre(rs.getString("genre"));
			dto.setPw_answer(rs.getString("pw_answer"));
			dto.setWithdraw(rs.getString("withdraw"));
			dto.setDisable(rs.getString("disable"));
			dto.setType(rs.getString("type"));
			dto.setQuestion_idx(rs.getInt("question_Idx"));
		}

		rs.close();
		ps.close();
		return dto;
	}

	public int join(MemberDTO dto) throws SQLException {
		String sql = "INSERT INTO member3(id,pw,name,age,pw_answer,gender,genre,email,question_idx)"
				+ "VALUES (?,?,?,?,?,?,?,?,?)";
		int result = 0;

		ps = conn.prepareStatement(sql);
		ps.setString(1, dto.getId());
		ps.setString(2, dto.getPw());
		ps.setString(3, dto.getName());
		ps.setInt(4, dto.getAge());
		ps.setString(5, dto.getPw_answer());
		ps.setString(6, dto.getGender());
		ps.setString(7, dto.getGenre());
		ps.setString(8, dto.getEmail());
		ps.setInt(9, dto.getQuestion_idx());
		result = ps.executeUpdate();

		return result;

	}

	/***
	 * 
	 * @return 제목,평점을 review3 에서 score기준 내림차순으로 7개
	 * @throws SQLException
	 */
	public ArrayList<ReviewDTO> top() throws SQLException {
		String scoreSQL = "SELECT subject,score FROM (SELECT subject,score FROM review3 ORDER BY score DESC) WHERE ROWNUM <= 7";
		ArrayList<ReviewDTO> top_list = new ArrayList<ReviewDTO>();
		ps = conn.prepareStatement(scoreSQL);
		rs = ps.executeQuery();
		while (rs.next()) {
			ReviewDTO dto = new ReviewDTO();
			dto.setSubject(rs.getString("subject"));
			dto.setScore(rs.getInt("score"));
			top_list.add(dto);
		}
		return top_list;
	}

	/***
	 * 
	 * @param keyWord
	 * @return 입력받은 id가 포함된 memberDTO들.
	 * @throws SQLException
	 */
	public ArrayList<MemberDTO> search(String keyWord) throws SQLException {
		String sql = "SELECT id,name,age,gender,genre FROM member3 WHERE id LIKE ?";
		ArrayList<MemberDTO> keyWord_list = new ArrayList<MemberDTO>();
		ps = conn.prepareStatement(sql);
		ps.setString(1, "%" + keyWord + "%");
		rs = ps.executeQuery();
		while (rs.next()) {
			MemberDTO dto = new MemberDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setAge(rs.getInt("age"));
			dto.setGender(rs.getString("gender"));
			dto.setGenre(rs.getString("genre"));
			keyWord_list.add(dto);
		}
		return keyWord_list;
	}
	
}
