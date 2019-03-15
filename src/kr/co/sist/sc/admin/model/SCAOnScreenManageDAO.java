package kr.co.sist.sc.admin.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import kr.co.sist.sc.admin.vo.SCAOnScreenInsertVO;
import kr.co.sist.sc.admin.vo.SCAOnScreenMovieListVO;
import kr.co.sist.sc.admin.vo.SCAOnScreenMovieListVO2;
import kr.co.sist.sc.admin.vo.SCAOnscreenSelectiveVO;

public class SCAOnScreenManageDAO {
	private static SCAOnScreenManageDAO scao_dao;

	private SCAOnScreenManageDAO(){
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static SCAOnScreenManageDAO  getInstance() {
		if (scao_dao == null) {
			scao_dao = new SCAOnScreenManageDAO();
		}
		return scao_dao;
	}
	
	public Connection getconn() throws SQLException {
		String url = "jdbc:oracle:thin:@211.63.89.142:1521:orcl";
		String id = "scadmin";
		String pass = "sc1234";
		Connection con = DriverManager.getConnection(url, id, pass);
		return con;
		
	}
	
	// �� �߰� �κ��� ��ȭ ����� �ҷ����� �κ�
	public List<SCAOnScreenMovieListVO> MovieList() throws SQLException{
		//List<SCAMovieManageVO> list = new ArrayList<SCAMovieManageVO>();
		List<SCAOnScreenMovieListVO> list=new ArrayList<SCAOnScreenMovieListVO>();
		
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con=getconn();
			
			String selectMovie = "SELECT MOVIE_CODE, MOVIE_TITLE, RUNNINGTIME FROM MOVIE";
			
			pstmt = con.prepareStatement(selectMovie);
			rs = pstmt.executeQuery();

			SCAOnScreenMovieListVO sosmvo = null;
			while (rs.next()) {
				sosmvo = new SCAOnScreenMovieListVO(rs.getString("movie_code"),rs.getString("movie_title"),rs.getInt("runningtime"));
				list.add(sosmvo);
				
			}
		}
		finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
			
		}
		return list;
	}
	//������ ��ȭ �� ����� ������
	public List<SCAOnScreenMovieListVO2> showScreenMovieList() throws SQLException {
		List<SCAOnScreenMovieListVO2> list=new ArrayList<SCAOnScreenMovieListVO2>();
		
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		try {
			con=getconn();
			
			String showMovie = "select distinct mov.movie_code as movie_code, mov.movie_title as movie_title from on_screen ons,(select movie_code,movie_title from movie)mov where ons.movie_code=mov.movie_code order by mov.movie_code ";
			
			pstmt = con.prepareStatement(showMovie);
			rs = pstmt.executeQuery();

			SCAOnScreenMovieListVO2 sosmvo2 = null;
			while (rs.next()) {
//				sosmvo2 = new SCAOnScreenMovieListVO2(rs.getString("movie_code"),rs.getString("movie_title"));
				sosmvo2= new SCAOnScreenMovieListVO2(rs.getString("movie_code"),rs.getString("movie_title"));
				list.add(sosmvo2);
				
			}
		}
		finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
			
		}
		return list;
	}
	
	//ó�� �󿵰����� ���Խ� �⺻������ ������ ���̺�
	public List<SCAOnscreenSelectiveVO> selectiveList() throws SQLException{
		 List<SCAOnscreenSelectiveVO> list=new ArrayList<SCAOnscreenSelectiveVO>();
		 Connection con=null;
		 PreparedStatement pstmt =null;
		 ResultSet rs=null;
		 StringBuilder tempquery=new StringBuilder();
		 tempquery.append("select ons.screen_num as screen_num,mov.movie_code as movie_code,mov.movie_img as movie_img,mov.movie_title as movie_title,ons.screen_name as screen_name,ons.start_time as start_time,ons.end_time as end_time,ons.screen_date as screen_date")
		 				.append(" from on_screen ons,(select movie_code,movie_img,movie_title from movie)mov")
		 				.append(" where ons.movie_code=mov.movie_code and check_remove in('N') order by screen_date,start_time");
		 try{//and not in('Y')
			 con=getconn();
			 String onScreenFirst=tempquery.toString();
			 pstmt = con.prepareStatement(onScreenFirst);
			 rs= pstmt.executeQuery();
			 SCAOnscreenSelectiveVO slvo=null;
			 while(rs.next()) {
					slvo = new SCAOnscreenSelectiveVO(rs.getString("screen_num"), rs.getString("movie_code"), rs.getString("movie_img"), 
							rs.getString("movie_title"),rs.getString( "screen_name"),rs.getString( "start_time"), rs.getString("end_time"),rs.getString("screen_date"));
					list.add(slvo);
				}
		 }
		 finally {
			 if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
		 }
		 return list;
	}
	
	//�󿵰������� ������ ��ȭ�� �󿵼��������� �����ִ� �κ�
	public List<SCAOnscreenSelectiveVO> screeningSelectMovieInfo(String code,String date) throws SQLException{
		 List<SCAOnscreenSelectiveVO> selectlist=new ArrayList<SCAOnscreenSelectiveVO>();
		 Connection con=null;
		 PreparedStatement pstmt =null;
		 ResultSet rs=null;
		 StringBuilder tempquery=new StringBuilder();
		 tempquery.append("select ons.screen_num as screen_num,mov.movie_code as movie_code,mov.movie_img as movie_img,mov.movie_title as movie_title,ons.screen_name as screen_name,ons.start_time as start_time,ons.end_time as end_time,ons.screen_date as screen_date")
		 				.append(" from on_screen ons,(select movie_code,movie_img,movie_title from movie where movie_code='")
		 				.append(code).append("' )mov")
	 					.append(" where ons.movie_code=mov.movie_code and screen_date='")
	 					.append(date).append("' ").append("and check_remove in('N') order by screen_date,start_time");
		 try{
			 con=getconn();
			 String onScreenFirst=tempquery.toString();
			 
			 pstmt = con.prepareStatement(onScreenFirst);
			 rs= pstmt.executeQuery();
			 	System.out.println(rs);
			 SCAOnscreenSelectiveVO slvo=null;
			 if(rs != null) {
			 while(rs.next()) {
					slvo = new SCAOnscreenSelectiveVO(rs.getString("screen_num"), rs.getString("movie_code"), rs.getString("movie_img"), 
							rs.getString("movie_title"),rs.getString( "screen_name"),rs.getString( "start_time"), rs.getString("end_time"),rs.getString("screen_date"));
					selectlist.add(slvo);
				}
			 }
			 if(slvo==null) {
				 slvo=new SCAOnscreenSelectiveVO("0", "0" , "0", "0", "0", "0","0", "0");
				 selectlist.add(0, slvo);
			 }
		 }
		 finally {
			 if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
		 }
		 return selectlist;
	}
	//�� ����� �ϴ� �κ�
	public int screenRegister(SCAOnScreenInsertVO sivo) throws SQLException{
//	screen_num	screen_date	start_time	end_time	movie_code	screen_name
		Connection con =null;
		PreparedStatement pstmt =null;
		StringBuilder insertQuery =new StringBuilder();
		insertQuery.append("insert into on_screen (screen_num,screen_date,start_time,end_time,movie_code,screen_name)")
									.append(" values(").append("'").append(sivo.getScreen_num()).append("',")
															.append("'").append(sivo.getScreen_date()).append("',")
															.append("'").append(sivo.getStart_time()).append("',")
															.append("'").append(sivo.getEnd_time()).append("',")
															.append("'").append(sivo.getMovie_code()).append("',")
															.append("'").append(sivo.getScreen_name()).append("')");
		int cnt =0;
		try {
			con=getconn();
			pstmt = con.prepareStatement(insertQuery.toString());
			
			cnt=pstmt.executeUpdate();
			
			
		}finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		return cnt;
	}
	
	/**
	 *���� ��ư�� ������ check_remove �κ��� Y �� �Ͽ� ���� �Ѱɷ� �Ѵ�
	 * @throws SQLException 
	 */
	public int remove(String screenNum) throws SQLException {
		Connection con =null;
		PreparedStatement pstmt =null;
		
	
		try {
			con=getconn();
			String updateQuery ="UPDATE ON_SCREEN SET CHECK_REMOVE='Y' WHERE SCREEN_NUM=?";
			
			pstmt = con.prepareStatement(updateQuery);
			pstmt.setString(1, screenNum);
			int cnt = pstmt.executeUpdate();
			
			return cnt;
			
		}finally {
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
		
		
	}
	 
	
}
