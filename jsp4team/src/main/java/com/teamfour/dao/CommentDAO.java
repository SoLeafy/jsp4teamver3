package com.teamfour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poseidon.util.Util;
import com.teamfour.dto.CommentDTO;

public class CommentDAO extends AbstractDAO {

	public int commentWrite(CommentDTO dto) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "INSERT INTO comment (comment, isbn, mno, ip, mid, mname) "
				+ "VALUES (?, ?, (SELECT mno FROM member WHERE mid=?), ?, ?, ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getComment());
			pstmt.setString(2, dto.getIsbn());
			pstmt.setString(3, dto.getMid());
			pstmt.setString(4, dto.getIp());
			pstmt.setString(5, dto.getMid());
			pstmt.setString(6, dto.getMname());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		return result;
	}

	public int commentDelete(CommentDTO dto) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE comment SET del=0 "
				+ "WHERE cno=? AND mno=(SELECT mno FROM member WHERE mid=?)";
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCno());
			pstmt.setString(2, dto.getMid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}		
		return result;
	}

	public int commentUpdate(CommentDTO dto) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE comment SET comment=? "
				+ "WHERE cno=? AND mno=(SELECT mno FROM member WHERE mid=?)";
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getComment());
			pstmt.setInt(2, dto.getCno());
			pstmt.setString(3, dto.getMid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}		
		return result;
	}

	public List<CommentDTO> commentList(String isbn) {
		List<CommentDTO> list = new ArrayList<CommentDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT cno, comment, cdate, mname, ip, mid FROM comment WHERE del=1 and isbn=?";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				CommentDTO dto = new CommentDTO();
				dto.setCno(rs.getInt("cno"));
				dto.setComment(rs.getString("comment"));
				dto.setCdate(rs.getString("cdate"));
				dto.setMname(rs.getString("mname"));
				dto.setIp(Util.ipMasking(rs.getString("ip")));
				dto.setMid(rs.getString("mid"));
				list.add(dto);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return list;
	}

	public int setStarPoint(CommentDTO dto) {
		int result = 0;
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO star (mno, isbn, starpoint) VALUES ((SELECT mno FROM member WHERE mid=?), ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getMid());
			pstmt.setString(2, dto.getIsbn());
			pstmt.setInt(3, dto.getStarpoint());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		
		return result;
	}

	public int checkStarPoint(String mid, String isbn) {
		int result = 0;
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) AS count FROM star WHERE mno=(SELECT mno FROM member WHERE mid=? AND isbn=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mid);
			pstmt.setString(2, isbn);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return result;
	}

	public String avgStarPoint(String isbn) {
		String result = null;
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT ROUND(AVG(starpoint), 1) AS average FROM star WHERE isbn=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString("average");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return result;
	}
}
