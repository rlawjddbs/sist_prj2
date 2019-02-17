package kr.co.sist.sc.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.sc.admin.vo.SCABookListVO;
import kr.co.sist.sc.admin.vo.SCABookMovieListVO;
import kr.co.sist.sc.admin.vo.SCABookOnScreenVO;

public class SCABookManageDAO {
	private static SCABookManageDAO scabm_dao;
	
	private SCABookManageDAO() {
		
	} // SCABookManageDAO
	
	public static SCABookManageDAO getInstance() {
		if (scabm_dao == null) {
			scabm_dao = new SCABookManageDAO();
		} // end if
		
		return scabm_dao;
	} // getInstance
	
	/**
	 * ��ȭ�� ��ȸ
	 * @param today
	 * @return
	 * @throws SQLException
	 */
	public List<SCABookMovieListVO> selectMovieList() throws SQLException {
		List<SCABookMovieListVO> list = new ArrayList<SCABookMovieListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = SCAConnect.getInstance().getConn();
			
			// ���� �� ���� ��ȭ ��ȸ
			// "and screen_date = to_char(sysdate, 'YYYY-MM-DD') " + 
			String selectQuery = 
					"select distinct m.movie_code, m.movie_title " + 
					"from movie m, on_screen os " + 
					"where (m.movie_code = os.movie_code) " + 
					"and screen_date = '2019-01-29' " + 
					"order by m.movie_code";
			
			pstmt = con.prepareStatement(selectQuery);
			
			rs = pstmt.executeQuery();
			
			SCABookMovieListVO scabml_vo = null;
			
			while (rs.next()) {
				scabml_vo = new SCABookMovieListVO(rs.getString("movie_code"), rs.getString("movie_title"));
				
				list.add(scabml_vo);
			} // end while
		} finally {
			if (rs != null) { rs.close(); } // end if
			if (pstmt != null) { pstmt.close(); } // end if
			if (con != null) { con.close(); } // end if
		} // end finally
		
		return list;
	} // selectMovieList
	
	/**
	 * 1) ���� ���� �� ������ ��ü �� ��ȭ ������ ��ȸ
	 * 2) ��ȭ������ ��ȸ �� ������ �ش� �� ��ȭ ������ ��ȸ
	 * @param movie_code
	 * @return
	 * @throws SQLException
	 */
	public List<SCABookOnScreenVO> selectBookOnScreen(String movie_code) throws SQLException {
		List<SCABookOnScreenVO>	list = new ArrayList<SCABookOnScreenVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = SCAConnect.getInstance().getConn();
			
			// ���� �� ���� ��ȭ ��ȸ
			// "and screen_date = to_char(sysdate, 'YYYY-MM-DD') " + 
			StringBuilder selectQuery = new StringBuilder();
			
			selectQuery
			.append(" select m.movie_code, m.movie_title, os.screen_num, t.screen_name, os.start_time, os.end_time, t.seat_count " )
			.append(" from movie m, on_screen os, theater t ")
			.append(" where (os.movie_code = m.movie_code) ")
			.append("   and (os.screen_name = t.screen_name) ")
			.append("   and os.screen_date = '2019-01-29' ");
			
//			selectQuery
//			.append(" select m.movie_code, m.movie_title, os.screen_num, t.screen_name, ")
//			.append(" os.start_time, os.end_time, (t.seat_count - b.personnel) as seat_remain, t.seat_count	")
//			.append(" from movie m, on_screen os, theater t, book b ")
//			.append(" where (os.movie_code = m.movie_code) ")
//			.append("   and (os.screen_name = t.screen_name) ")
//			.append("   and (b.screen_num = os.screen_num) ")
//			.append("   and os.screen_date = '2019-01-29' ");
			
			if (!movie_code.equals("")) {
				// movie_code�� empty�� �ƴ϶��, movie_code�� �ش� ��ȭ�� ��ȸ
				selectQuery
				.append("   and os.movie_code = ? ");
			} // end if
			
			selectQuery
			.append(" order by os.start_time ");
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			if (!movie_code.equals("")) {
				pstmt.setString(1, movie_code);
			} // end if 
			
			rs = pstmt.executeQuery();
			
			SCABookOnScreenVO scabos_vo = null;
			
			while (rs.next()) {
				scabos_vo = new SCABookOnScreenVO(
						rs.getString("movie_code"), rs.getString("movie_title"), 
						rs.getString("screen_num"), rs.getString("screen_name"), 
						rs.getString("start_time"), rs.getString("end_time"), 
						1, rs.getInt("seat_count")); 
//						rs.getInt("seat_remain"), rs.getInt("seat_count"));
				
				list.add(scabos_vo);
			} // end while
		} finally {
			if (rs != null) { rs.close(); } // end if
			if (pstmt != null) { pstmt.close(); } // end if
			if (con != null) { con.close(); } // end if
		} // end finally
		
		return list;
	} // selectBookOnScreen
	
	/**
	 * ���� �¼� ��ȸ
	 * �� ��ȣ�� ã�´�.
	 * @param screen_num
	 * @return
	 * @throws SQLException
	 */
	public List<Integer> selectBookSeat(String screen_num) throws SQLException {
		List<Integer> list = new ArrayList<Integer>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = SCAConnect.getInstance().getConn();
			
			String selectQuery = 
					"  " + 
					"  " + 
					"  " + 
					"  ";
			
			
		} finally {
			if (rs != null) { rs.close(); } // end if
			if (pstmt != null) { pstmt.close(); } // end if
			if (con != null) { con.close(); } // end if
		} // end finally
		
		return list;
	} //selectBookSeat
	
	/**
	 * �ش� ���� ��ȣ�� �����ϴ� �¼� ���̺��� �¼� ��ȣ�� �����;� �Ѵ�.
	 * ���� ��ȣ�� S�� �����ϴ���, P�� �����ϴ��� ���� ������ �ۼ�?
	 * 
	 * @param movie_code
	 * @return
	 * @throws SQLException
	 */
	public List<SCABookListVO> selectBookList(String movie_code) throws SQLException {
		List<SCABookListVO> list = new ArrayList<SCABookListVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = SCAConnect.getInstance().getConn();
			
			StringBuilder selectQuery = new StringBuilder(); 
			
			selectQuery
			.append(" select m.member_id, b.book_number, b.personnel, ss.seat_num, b.payment_date ")
			.append(" from member m, book b, standard_seat ss ")
			.append(" where (b.member_id = m.member_id) ")
			.append("   and (ss.book_number = b.book_number) ")
			.append(" union ")
			.append(" select m.member_id, b.book_number, b.personnel, ps.seat_num, b.payment_date ")
			.append(" from member m, book b, premium_seat ps ")
			.append(" where (b.member_id = m.member_id) ")
			.append(" and (ps.book_number = b.book_number) ");
			
			pstmt = con.prepareStatement(selectQuery.toString());
			
			rs = pstmt.executeQuery();
			
			SCABookListVO scabl_vo = null;
			
			while (rs.next()) {
				scabl_vo = new SCABookListVO(
						rs.getString("member_id"), rs.getString("book_number"), rs.getString("payment_date"), 
						rs.getInt("personnel"), rs.getInt("seat_num"));
				
				list.add(scabl_vo);
			} // end while
		} finally {
			if (rs != null) { rs.close(); } // end if
			if (pstmt != null) { pstmt.close(); } // end if
			if (con != null) { con.close(); } // end if
		} // end finally
		
		return list;
	} // selectBookList
	
} // class
