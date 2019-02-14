package kr.co.sist.cinema.admin.vo;

/**
 * @author owner
 * ���� ���� - ��ȭ �� ���� ���̺�
 * ��ȭ �ڵ�, ��ȭ ����, �ο� ��, �� ����
 */
public class SCACalculationMovieSalesVO {
	private String movie_code, movie_title;
	private int personnel, total_price;
	
	public SCACalculationMovieSalesVO(String movie_code, String movie_title, int personnel, int total_price) {
		this.movie_code = movie_code;
		this.movie_title = movie_title;
		this.personnel = personnel;
		this.total_price = total_price;
	} // SCACalculationMovieSalesVO

	public String getMovie_code() {
		return movie_code;
	}

	public String getMovie_title() {
		return movie_title;
	}

	public int getPersonnel() {
		return personnel;
	}

	public int getTotal_price() {
		return total_price;
	}
	
} // class
