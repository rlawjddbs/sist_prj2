package kr.co.sist.sc.admin.vo;

/**
 * ���� ���� - ��ȭ �� ���� ���̺�
 * ��ȭ �ڵ�, ��ȭ ����, �ο� ��, �� ����
 * @author owner
 */
public class SCACalculationMovieSalesVO {
	private String movie_code, movie_title, total_price;
	private int personnel;
	
	public SCACalculationMovieSalesVO(String movie_code, String movie_title, String total_price, int personnel) {
		this.movie_code = movie_code;
		this.movie_title = movie_title;
		this.total_price = total_price;
		this.personnel = personnel;
	} // SCACalculationMovieSalesVO

	public String getMovie_code() {
		return movie_code;
	}

	public String getMovie_title() {
		return movie_title;
	}

	public String getTotal_price() {
		return total_price;
	}

	public int getPersonnel() {
		return personnel;
	}

	@Override
	public String toString() {
		return "SCACalculationMovieSalesVO [movie_code=" + movie_code + ", movie_title=" + movie_title
				+ ", total_price=" + total_price + ", personnel=" + personnel + "]";
	} // toString
	
} // class
