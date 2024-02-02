package com.teamfour.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.teamfour.dto.BookDTO;
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
			String sql = "insert into testbook(bookId, title, author, publisher, priceStandard) values(?,?,?,?,?)";
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close(null, null, con);
		}
	}
}