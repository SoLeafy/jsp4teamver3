package com.teamfour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;
import javax.print.attribute.HashAttributeSet;

import com.teamfour.db.DBConnection;
import com.teamfour.dto.BookDTO;
import com.teamfour.dto.MemberDTO;
import com.teamfour.dto.testBookDTO;

public class BookDAO extends AbstractDAO {
	private List<BookDTO> listofBooks = new ArrayList<BookDTO>();
	
	private static BookDAO instance = new BookDAO();
	public static BookDAO getInstance() {
		return instance;
	}
	
	public void insertInfo(ArrayList newitemList) {
		Connection con = db.getConnection();
		try {
			
			// 3. sql 작성 & pstmt 객체 생성
			String sql = "insert into testbook(isbn, title, author, publisher, priceStandard) values(?,?,?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			for(int i=0;i<newitemList.size();i++) {
				
				testBookDTO dto =  (testBookDTO) newitemList.get(i);
				
				pstmt.setInt(1,dto.getBookId());
				pstmt.setString(2, dto.getTitle());
				pstmt.setString(3, dto.getAuthor());
				pstmt.setString(4, dto.getPublisher());
				pstmt.setInt(5, dto.getPriceStandard());
									// 4. sql 실행
				pstmt.executeUpdate();
			
			}
			
			System.out.println("DB 저장 성공!");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(null, null, con);
		}
	}
	
	public BookDTO detail(String isbn) {
		BookDTO dto = new BookDTO();

		Connection con = DBConnection.getInstance().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT bookcover, booktitle, author, publisher, publishdate, isbn, "
				+ "bookdetail, bookindex, profile, bookprice FROM book WHERE isbn=?";

		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setBookcover(rs.getString("bookcover"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setPublishdate(rs.getString("publishdate"));
				dto.setBookdetail(rs.getString("bookdetail"));
				dto.setBookindex(rs.getString("bookindex"));
				dto.setProfile(rs.getString("profile"));
				dto.setBookprice(rs.getInt("bookprice"));
				dto.setIsbn(isbn);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}

		return dto;
	}
	
	

	public List<BookDTO> domesticList(int page) {
		List<BookDTO> list = new ArrayList<BookDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT isbn, booktitle, bookprice, author, publisher, publishdate, bookcover FROM domesticview LIMIT ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (page-1) * 10);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setIsbn(rs.getString("isbn"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setBookprice(rs.getInt("bookprice"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				//dto.setStock(rs.getInt("stock"));
				dto.setPublishdate(rs.getString("publishdate"));
				dto.setBookcover(rs.getString("bookcover"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		
		return list;
	}
	
	public List<BookDTO> foreignList(int page) {
		List<BookDTO> list = new ArrayList<BookDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT isbn, booktitle, bookprice, author, publisher, publishdate, bookcover FROM foreignview LIMIT ?, 10";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (page-1) * 10);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setIsbn(rs.getString("isbn"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setBookprice(rs.getInt("bookprice"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				//dto.setStock(rs.getInt("stock"));
				dto.setPublishdate(rs.getString("publishdate"));
				dto.setBookcover(rs.getString("bookcover"));
				list.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		
		return list;
	}
	
	public int totalBooks(String viewname) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM " + viewname;
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			//pstmt.setString(1, viewname);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return result;
	}
	
	public int searchTotal(String searchItem) {
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT count(*) FROM book WHERE booktitle LIKE CONCAT('%', ?, '%') OR author LIKE CONCAT('%', ?, '%')";
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchItem);
			pstmt.setString(2, searchItem);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return result;
	}
	
	public List<BookDTO> newBookList(int page) {
		 List<BookDTO> list = new ArrayList<BookDTO>();
		 Connection con = db.getConnection();
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 String sql = "SELECT isbn, booktitle, bookprice, author, publisher, publishdate, bookcover FROM book ORDER BY publishdate DESC LIMIT ?, 10";
		 
		 try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (page-1) * 10);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setIsbn(rs.getString("isbn"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setBookprice(rs.getInt("bookprice"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setPublishdate(rs.getString("publishdate"));
				dto.setBookcover(rs.getString("bookcover"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		 return list;
	}

	public List<BookDTO> searchList(String searchItem, int page) {
		List<BookDTO> list = new ArrayList<BookDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT isbn, booktitle, bookprice, author, publisher, publishdate, bookcover FROM book "
				+ "WHERE booktitle LIKE CONCAT('%', ? '%') OR author LIKE CONCAT('%', ? '%') LIMIT ?, 10";
		
		try {
			// 오류나서...
			if(page < 1) {
				page = 0;
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, searchItem);
			pstmt.setString(2, searchItem);
			pstmt.setInt(3, (page-1) * 10);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setIsbn(rs.getString("isbn"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setBookprice(rs.getInt("bookprice"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setPublishdate(rs.getString("publishdate"));
				dto.setBookcover(rs.getString("bookcover"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		
		return list;
	}

	public List<BookDTO> newBookIndex() {
		List<BookDTO> list = new ArrayList<BookDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT isbn, bookcover, booktitle, author FROM book order by publishdate desc LIMIT 0, 6;";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setIsbn(rs.getString("isbn"));
				dto.setBookcover(rs.getString("bookcover"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setAuthor(rs.getString("author"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return list;
	}

	public List<BookDTO> newDomestic() {
		List<BookDTO> list = new ArrayList<BookDTO>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT isbn, bookcover, booktitle, author FROM domesticview LIMIT 0, 6;";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setIsbn(rs.getString("isbn"));
				dto.setBookcover(rs.getString("bookcover"));
				dto.setBooktitle(rs.getString("booktitle"));
				dto.setAuthor(rs.getString("author"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return list;
	}

	public List<Map<String, Object>> newForeign() {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT isbn, booktitle, bookcover, author FROM foreignview LIMIT 0, 6";
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("isbn", rs.getString("isbn"));
				map.put("booktitle", rs.getString("booktitle"));
				map.put("bookcover", rs.getString("bookcover"));
				map.put("author", rs.getString("author"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, con);
		}
		return list;
	}

	public int bookInfo(BookDTO dto) {
		int result = 0;
		
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO book (isbn, booktitle, bookprice, author, publisher, publishdate, category, bookcover, bookdetail, bookindex, profile) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getIsbn());
			pstmt.setString(2, dto.getBooktitle());
			pstmt.setInt(3, dto.getBookprice());
			pstmt.setString(4, dto.getAuthor());
			pstmt.setString(5, dto.getPublisher());
			pstmt.setString(6, dto.getPublishdate());
			pstmt.setString(7, dto.getCategory());
			pstmt.setString(8, dto.getBookcover());
			pstmt.setString(9, dto.getBookdetail());
			pstmt.setString(10, dto.getBookindex());
			pstmt.setString(11, dto.getProfile());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		return result;
	}
}
