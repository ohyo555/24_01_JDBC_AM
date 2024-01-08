package com.KoreaIT.java.JDBCAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBCUpdateTest {
	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		Scanner sc = new Scanner(System.in);
		System.out.print("수정할 게시글 : ");
		int id = sc.nextInt();
		System.out.print("수정할 제목 : ");
		String title = sc.next();
		System.out.print("수정할 내용 : ");
		String body = sc.next();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/JDBC_AM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

			conn = DriverManager.getConnection(url, "root", "");
			System.out.println("연결 성공!");

			String sql = "UPDATE article ";
			sql += "SET updateDate = NOW()";
			if (title.length() > 0) {
				sql += " ,title = ?, ";
			}
			if (body.length() > 0) {
				sql += "body = ? ";
			}
			sql += "WHERE id = ?";

			System.out.println(sql);

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, title);
			pstmt.setString(2, body);
			pstmt.setInt(3, id);

			int res = pstmt.executeUpdate();
			if (res > 0) {
				System.out.print("수정 성공");
			} else {
				System.out.print("해당 번호의 게시글이 존재하지 않습니다.");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("에러 : " + e);
		} finally {
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sc.close();
	}
}